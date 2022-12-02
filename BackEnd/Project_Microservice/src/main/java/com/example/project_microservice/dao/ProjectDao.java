package com.example.project_microservice.dao;

import com.example.project_microservice.model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectDao extends MongoRepository<Project, String> {
    public Optional<Project> findById(String id);
}
