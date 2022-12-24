package com.example.user_microservice.controller;

import com.example.user_microservice.service.AuthService;
import com.example.user_microservice.service.UserService;
import com.example.user_microservice.model.User;
import com.example.user_microservice.utils.dto.AuthUserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：UserController
 * @date ：2022-12-17 17:50
 * @version : 2.0
 */

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    //good
    @PostMapping("/v2.0/user-microservice/users")
    public void addUser(User user){
        userService.addUser(user);
    }

    //good
    @PutMapping("/v2.0/user-microservice/users")
    public int changeUserInfo(User newUser){
        return userService.changeUserInfo(newUser);
    }

    //good
    @DeleteMapping ("/v2.0/user-microservice/users")
    public void deleteUser(String id){
        userService.deleteUser(id);
    }

    //good
    @GetMapping("/v2.0/user-microservice/users/email")
    public Optional<User> findUserByEmail(String email){
        return userService.findUserByEmail(email);
    }

    //good
    @GetMapping("/v2.0/user-microservice/users/id")
    public Optional<User> findUserById(String id){
        return userService.findUserById(id);
    }

    //good
    @GetMapping("/v2.0/user-microservice/users")
    public List findAllUser(){
        return userService.findAllUser();
    }

    //good
    @PostMapping("/v2.0/user-microservice/picturePath")
    public String addPicPathOnly(String Id, String strogepath){
        return userService.addPicPathOnly(Id,strogepath);
    }

    @PostMapping(value = "/v2.0/user-microservice/register")
    public ResponseEntity<Object> register(@RequestParam String username,@RequestParam String password,
                                           @RequestParam String code,@RequestParam String email) {
        return authService.register(username,password,code,email);
    }

    @PostMapping(value = "/v2.0/user-microservice/registerEmail")
    public ResponseEntity<Object> registerEmail(@RequestParam String email) {
        return  authService.registerEmail(email);
    }
    @PostMapping(value = "/v2.0/user-microservice/recoverEmail")
    public ResponseEntity<Object> recoverEmail(@RequestParam String name) {
        return authService.recoverEmail(name);
    }
    @GetMapping("/v2.0/user-microservice/login")
    public ResponseEntity<Object> login(@RequestParam String name,@RequestParam String password,@RequestParam String role) {
        return authService.login(name,password,role);
    }
    @PostMapping("/v2.0/user-microservice/recover")
    public ResponseEntity<Object> recover(@RequestParam String name,@RequestParam String code,@RequestParam String newpassword) {
        return authService.recover(name,code,newpassword);
    }
}
