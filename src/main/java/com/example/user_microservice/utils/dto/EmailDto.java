package com.example.user_microservice.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {
    private List<String> tos;
    private String subject;
    private String content;
}
