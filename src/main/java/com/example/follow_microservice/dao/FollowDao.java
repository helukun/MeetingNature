package com.example.follow_microservice.dao;

import com.example.follow_microservice.model.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowDao extends MongoRepository<Follow, String>{

}
