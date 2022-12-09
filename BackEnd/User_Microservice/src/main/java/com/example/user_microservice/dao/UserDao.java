package com.example.user_microservice.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.user_microservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：UserDao
 * @date ：2022-12-9 15:49
 * @version : 1.0
 */

@Repository
public interface UserDao extends MongoRepository<User, String> {
    public Optional<User> findById(String id);
}
