package com.example.project_microservice.dao;

import com.example.project_microservice.model.Project;
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
public interface ProjectDao extends MongoRepository<Project, String> {
    public Optional<Project> findById(String id);
}
