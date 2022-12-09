package com.example.user_microservice.controller;

import com.example.user_microservice.service.UserService;
import com.example.user_microservice.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：UserController
 * @date ：2022-12-9 15:48
 * @version : 1.0
 */

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //good
    @PostMapping("/user-microservice/user")
    public void addUser(User user){
        userService.addUser(user);
    }

    //good
    @PutMapping("/user-microservice/user")
    public int changeUserInfo(User newUser){
        return userService.changeUserInfo(newUser);
    }

    //good
    @DeleteMapping ("/user-microservice/user")
    public void deleteUser(String id){
        userService.deleteUser(id);
    }

    //good
    @GetMapping("/user-microservice/user/email")
    public Optional<User> findUserByEmail(String email){
        return userService.findUserByEmail(email);
    }

    //good
    @GetMapping("/user-microservice/user/id")
    public Optional<User> findUserById(String id){
        return userService.findUserById(id);
    }

    //good
    @GetMapping("/user-microservice/user/all")
    public List findAllUser(){
        return userService.findAllUser();
    }
}
