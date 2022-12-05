package com.example.processmanagement_microservice.model;

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
@Document("Sponsorship")
public class Sponsorship implements Serializable {
    @Id
    private String id;
    private String sponsorId;
    private String subjectId;
    private String cutoffTime;
    private String status;
}


