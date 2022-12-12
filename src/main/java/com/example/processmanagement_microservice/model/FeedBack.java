package com.example.processmanagement_microservice.model;

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

/**
 * @author ：ZXM+LJC
 * @description：FeedBack
 * @date ：2022-12-9 15:46
 * @version : 1.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Document("FeedBack")
public class FeedBack implements Serializable {
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
