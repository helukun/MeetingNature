package com.example.user_microservice.service;


import cn.hutool.extra.mail.Mail;
import cn.hutool.extra.mail.MailAccount;
import com.example.user_microservice.utils.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService  {
    @Value("${spring.mail.email}")
    private String email;

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;

    @Autowired
    private JavaMailSender mailSender;

    public void send(EmailDto emailDto) {
        if(email==null || host==null || port==null || username==null|| password==null){
            throw new RuntimeException("邮箱配置异常");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        int size=emailDto.getTos().size();
        message.setTo(emailDto.getTos().toArray(new String[size]));
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getContent());
        mailSender.send(message);

//        MailAccount account=new MailAccount();
//        account.setHost(host);
//        account.setPort(Integer.parseInt(port));
//        //发送人邮箱
//        account.setFrom(email);
//        //发送人名称
//        account.setUser(email);
//        account.setPass(password);
//        account.setAuth(true);
//        //ssl形式发送
//        account.setSslEnable(true);
//        //使用安全连接
//        account.setStarttlsEnable(true);
//
//
//        try{
////            MailUtil.send(account,emailDto.getTos().get(0),emailDto.getSubject(),emailDto.getContent(),true);
//            int size=emailDto.getTos().size();
//            Mail.create(account)
//                    .setTos(emailDto.getTos().toArray(new String[size]))
//                    .setTitle(emailDto.getSubject())
//                    .setContent(emailDto.getContent())
//                    .setHtml(true)
//                    .setUseGlobalSession(false)
//                    .send();
//        }catch (Exception e){
//            throw new RuntimeException(e.getMessage());
//        }

    }
}