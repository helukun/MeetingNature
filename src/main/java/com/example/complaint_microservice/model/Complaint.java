package com.example.complaint_microservice.model;

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

/**
 * @author ：ZXM+LJC
 * @description：Project
 * @date ：2022-12-9 15:23
 * @version : 1.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Document("Complaint")
public class Complaint {
    @Id
    private String id;
    private String content;
    private String status;
    private String sponsorId;
    private String subjectId;
}
