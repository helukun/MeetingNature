package com.example.follow_microservice.model;

import com.ctc.wstx.shaded.msv_core.datatype.xsd.IDType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Document("Follow")
public class Follow implements Serializable {
    @Id
    private String id;
    private String followerId;
    private String subjectId;
}
