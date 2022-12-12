package com.example.follow_microservice.model;

import jakarta.ws.rs.core.Context;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Document("Notice")
public class Notice implements Serializable {
    @Id
    private String id;
    @Context
    private String content;
    private String title;//false
    private String subjectId;
    private String createTime;
    private String status;
    private List<String> pathList;
}
