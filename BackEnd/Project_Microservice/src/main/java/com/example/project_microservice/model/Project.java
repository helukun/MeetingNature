package com.example.project_microservice.model;

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
@Document("Project")
public class Project implements Serializable{
    @Id
    private String id;
    private String projectName;
    private String organization;
    private String describe;
    private String[] followerList;
}
