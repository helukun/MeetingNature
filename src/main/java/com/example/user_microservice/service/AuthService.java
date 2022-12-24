package com.example.user_microservice.service;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.example.user_microservice.dao.UserDao;
import com.example.user_microservice.model.User;
import com.example.user_microservice.utils.dto.AuthUserDto;
import com.example.user_microservice.utils.dto.EmailDto;
//import com.example.user_microservice.utils.redis.RedisUtils;
import com.example.user_microservice.utils.jwt.JwtUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AuthService {

    @Autowired
    private UserDao userDao;

    //验证码过期时间
    @Value("${code.expiration}")
    private Long expiration;
    private final UserService userService;

    @Resource
    StringRedisTemplate stringRedisTemplate;

    private final EmailService emailService;


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity register(String username,String password,String code,String email) {
        if (!userService.findByUsername(username).isEmpty()) {
            return new ResponseEntity<>("用户名已存在", HttpStatus.BAD_REQUEST);
        }
        // 通过email获取redis中的code
        String value = stringRedisTemplate.opsForValue().get(email);
        if (value == null || !value.equals(code)) {
            return new ResponseEntity<>("无效验证码", HttpStatus.BAD_REQUEST);
        } else {
            stringRedisTemplate.delete(email);
        }
        // 创建用户
        User sysUser = new User();
        sysUser.setName(username);
        sysUser.setPassword(password);
        sysUser.setRole("1");
        sysUser.setEmail(email);
        userService.addUser(sysUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity registerEmail(String email) {
        Pattern pattern=Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");//\w表示a-z，A-Z，0-9(\\转义符)
        Matcher matcher=pattern.matcher(email);
        boolean b=matcher.matches();
        if (!b) {
            return new ResponseEntity<>("邮箱格式错误", HttpStatus.BAD_REQUEST);
        }
        // 查看注册邮箱是否存在
        if (userService.findUserByEmail(email)!=null) {
            return new ResponseEntity<>("注册邮箱已存在，请重新输入其他邮箱", HttpStatus.BAD_REQUEST);
        }
        // 获取发送邮箱验证码的HTML模板
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email-code.ftl");

        // 从redis缓存中尝试获取验证码
        String code =stringRedisTemplate.opsForValue().get(email);
//        Object code = redisUtils.get(email);
        if (code == null) {
            // 如果在缓存中未获取到验证码，则产生6位随机数，放入缓存中
            code = RandomUtil.randomNumbers(6);
            try {
                stringRedisTemplate.opsForValue().set(email, code, expiration, TimeUnit.SECONDS);
            } catch (Exception e) {
                return new ResponseEntity<>("后台缓存服务异常", HttpStatus.BAD_REQUEST);
            }
        }
        // 发送验证码
        emailService.send(new EmailDto(Collections.singletonList(email),
                "MeetingNature", template.render(Dict.create().set("code", code))));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity login(String name,String password,String role) {
        Optional<User> user = userDao.findByName(name);
        if (user.isEmpty()||!user.get().getRole().equals(role)) {
            return new ResponseEntity<>("该用户不存在", HttpStatus.BAD_REQUEST);
        }
        else if (!user.get().getPassword().equals(password)) {
            return new ResponseEntity<>("密码错误", HttpStatus.BAD_REQUEST);
        }
        else {
            Map<String, Object> info = new HashMap<>();
            info.put("username", user.get().getName());
            info.put("email", user.get().getEmail());
            info.put("role",user.get().getRole());
            String token = JwtUtil.sign(user.get().getId(), info);
            Map<String, Object> result= new HashMap<>(2) {{
                put("token",token);
                put("userID", user.get().getId());
            }};
            return ResponseEntity.ok(result);
        }
    }

    public ResponseEntity recoverEmail(String name) {
        Optional<User> user=userDao.findByName(name);
        if(user.isEmpty()){
            return new ResponseEntity<>("您输入的用户名不存在", HttpStatus.BAD_REQUEST);
        }
        String email=user.get().getEmail();
        // 获取发送邮箱验证码的HTML模板
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email-code2.ftl");

        // 从redis缓存中尝试获取验证码
        String code =stringRedisTemplate.opsForValue().get(name);
//        Object code = redisUtils.get(email);
        if (code == null) {
            // 如果在缓存中未获取到验证码，则产生6位随机数，放入缓存中
            code = RandomUtil.randomNumbers(6);
            try {
                stringRedisTemplate.opsForValue().set(name, code, expiration, TimeUnit.SECONDS);
            } catch (Exception e) {
                return new ResponseEntity<>("后台缓存服务异常", HttpStatus.BAD_REQUEST);
            }
        }
        // 发送验证码
        emailService.send(new EmailDto(Collections.singletonList(email),
                "MeetingNature", template.render(Dict.create().set("code", code))));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> recover(String name,String code, String newpassword) {
        String value = stringRedisTemplate.opsForValue().get(name);
        if (value == null || !value.equals(code) ){
            return new ResponseEntity<>("无效验证码", HttpStatus.BAD_REQUEST);
        }
        else {
            stringRedisTemplate.delete(name);
        }
        Optional<User> user=userDao.findByName(name);
        user.get().setPassword(newpassword);
        userDao.save(user.get());
        return ResponseEntity.ok("新密码修改成功");
    }


}
