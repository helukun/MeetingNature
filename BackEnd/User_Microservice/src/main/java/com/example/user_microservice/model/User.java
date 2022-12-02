package com.example.user_microservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Document("User")

public class User implements Serializable{
    @Id
    private String id;
    private String email;
    private String name;
    private String password;
    private String status;
    private String role;
}
