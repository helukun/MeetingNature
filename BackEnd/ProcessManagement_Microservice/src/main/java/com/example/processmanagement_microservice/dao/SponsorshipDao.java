package com.example.processmanagement_microservice.dao;

import com.example.processmanagement_microservice.model.Sponsorship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SponsorshipDao extends MongoRepository<Sponsorship, String> {
    public Optional<Sponsorship> findById(String id);
    void delete(Optional<Sponsorship> sponsorship);
}
