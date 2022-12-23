package com.example.complaint_microservice.dao;


import com.example.complaint_microservice.model.Complaint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：ProjectDao
 * @date ：2022-12-9 15:23
 * @version : 1.0
 */

@Repository
public interface ComplaintDao extends MongoRepository<Complaint, String>{

}
