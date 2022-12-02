package com.example.user_microservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.user_microservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends MongoRepository<User, String> {
    public Optional<User> findById(String id);
}
