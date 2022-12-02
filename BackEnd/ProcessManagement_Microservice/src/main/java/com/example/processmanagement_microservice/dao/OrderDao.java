package com.example.processmanagement_microservice.dao;

import com.example.processmanagement_microservice.model.Order;
import com.example.processmanagement_microservice.model.Sponsorship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderDao extends MongoRepository<Order, String>{

}
