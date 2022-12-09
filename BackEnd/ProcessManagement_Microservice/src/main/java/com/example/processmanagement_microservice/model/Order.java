package com.example.processmanagement_microservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author ：ZXM+LJC
 * @description：Order
 * @date ：2022-12-9 15:46
 * @version : 1.0
 */

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
@Document("Order")
public class Order implements Serializable{
    @Id
    private String orderId;
    private String sponsorId;
    private String subjectId;
    private String setupTime;
    private String status;
    private String amount;
    private String SponsorshipPeriod;
}
