package com.example.user_microservice.service;

import com.example.user_microservice.model.User;
import com.example.user_microservice.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：UserService
 * @date ：2022-12-9 15:49
 * @version : 1.0
 */

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public int isExist(String email,String id){
        User checkUser1=new User();
        checkUser1.setEmail(email);
        Example<User> userExample1=Example.of(checkUser1);
        long count1 = userDao.count(userExample1);

        User checkUser2=new User();
        checkUser2.setId(id);
        Example<User> userExample2=Example.of(checkUser2);
        long count2 = userDao.count(userExample2);

        int result=0;
        if((int)count1==1||(int)count2==1){
            result=1;
        }
        return result;
    }

    public void addUser(User user){
        int exist=this.isExist(user.getEmail(),user.getId());
        if(exist!=1){
            User newUser = new User()
                    .setId(user.getId())
                    .setEmail(user.getEmail())
                    .setName(user.getName())
                    .setPassword(user.getPassword())
                    .setStatus(user.getStatus())
                    .setRole(user.getRole());
            userDao.save(user);
            System.out.println("添加成功！");
        }
        else{
            System.out.println("该邮箱或id已注册，无法再次注册！");
        }
    }

    public List findAllUser(){
        List<User> userList = userDao.findAll();
        return userList;
    }

    public Optional<User> findUserById(String id){
        Optional<User> user=null;

        User checkUser2=new User();
        checkUser2.setId(id);
        Example<User> userExample2=Example.of(checkUser2);
        long count2 = userDao.count(userExample2);

        if((int)count2==1){
            user = userDao.findById(id);
            System.out.println("找到用户！");
        }
        else{
            System.out.println("该用户不存在");
        }
        return user;
    }

    public Optional<User> findUserByEmail(String email){
        Optional<User> user=null;

        User checkUser=new User();
        checkUser.setEmail(email);
        Example<User> userExample=Example.of(checkUser);
        List<User> list=userDao.findAll(userExample);

        if(list.size()==0){
            System.out.println("该用户不存在");
        }
        else{
            String id=list.get(0).getId();
            user = this.findUserById(id);
            System.out.println("找到用户！");
        }
        System.out.println(list.size());
        return user;
    }

    //可能用于登录注册子系统中的密码合法性判断
    public String getUserPassword(String id){
        Optional<User> user = this.findUserById(id);
        return user.get().getPassword();
    }

    public int emailLegal(String email){
        int legal=1;

        User checkUser=new User();
        checkUser.setEmail(email);
        Example<User> userExample=Example.of(checkUser);
        long count = userDao.count(userExample);

        if((int)count!=0){
            legal=0;
        }

        return legal;
    }

    public int nameLegal(String name){
        int legal=1;

        if(name.length()==0||
                name.length()>15||
                name.contains(" ")){
            legal=0;
        }

        return legal;
    }

    public int passwordLegal(String password){
        String REG_NUMBER = ".*\\d+.*";
        String REG_UPPERCASE = ".*[A-Z]+.*";
        String REG_LOWERCASE = ".*[a-z]+.*";

        int legal=1;

        if(password.length()==0||
                password.length()>20||
                password.contains(" ")||
                !password.matches(REG_NUMBER)||
                !password.matches(REG_UPPERCASE)||
                !password.matches(REG_LOWERCASE)
        ){
            legal=0;
        }

        return legal;
    }

    public int changeUserInfo(User newUser){
        User checkUser2=new User();
        checkUser2.setId(newUser.getId());
        Example<User> userExample2=Example.of(checkUser2);
        long count2 = userDao.count(userExample2);

        if((int)count2==0){
            System.out.println("该用户不存在");
            return 0;
        }

        Optional<User> OldUser=this.findUserById(newUser.getId());

        int legal=0;
        if(this.nameLegal(newUser.getName())==1&&
                this.passwordLegal(newUser.getPassword())==1&&
                this.emailLegal(newUser.getEmail())==1){
            legal=1;
        }

        if(legal==1){
            userDao.save(newUser);
            System.out.println("修改成功！");
        }
        else{
            System.out.println("修改失败！");
        }
        return legal;
    }

    public void deleteUser(String id){
        userDao.deleteById(id);
    }
}