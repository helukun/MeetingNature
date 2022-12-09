package com.example.processmanagement_microservice.dao;

import com.example.processmanagement_microservice.model.Order;
import com.example.processmanagement_microservice.model.Sponsorship;
import com.example.processmanagement_microservice.model.FeedBack;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：FeedBackDao
 * @date ：2022-12-9 15:45
 * @version : 1.0
 */

@Repository
public interface FeedBackDao extends MongoRepository<FeedBack,String>{

}
