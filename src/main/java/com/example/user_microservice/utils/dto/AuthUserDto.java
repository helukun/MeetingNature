package com.example.user_microservice.utils.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证用户
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto {

    private String username;

    private String password;

    private String code;

    private String email ;

}