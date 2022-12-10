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
 * @date ：2022-12-10 14:06
 * @version : 1.
 */

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //good
    @PostMapping("/v1.1/user-microservice/users")
    public void addUser(User user){
        userService.addUser(user);
    }

    //good
    @PutMapping("/v1.1/user-microservice/users")
    public int changeUserInfo(User newUser){
        return userService.changeUserInfo(newUser);
    }

    //good
    @DeleteMapping ("/v1.1/user-microservice/users")
    public void deleteUser(String id){
        userService.deleteUser(id);
    }

    //good
    @GetMapping("/v1.1/user-microservice/users/email")
    public Optional<User> findUserByEmail(String email){
        return userService.findUserByEmail(email);
    }

    //good
    @GetMapping("/v1.1/user-microservice/users/id")
    public Optional<User> findUserById(String id){
        return userService.findUserById(id);
    }

    //good
    @GetMapping("/v1.1/user-microservice/users")
    public List findAllUser(){
        return userService.findAllUser();
    }
}
