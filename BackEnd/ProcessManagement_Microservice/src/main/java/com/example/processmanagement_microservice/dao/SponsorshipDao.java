package com.example.processmanagement_microservice.dao;

import com.example.processmanagement_microservice.model.Sponsorship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：SponsorshipDao
 * @date ：2022-12-9 15:46
 * @version : 1.0
 */

@Repository
public interface SponsorshipDao extends MongoRepository<Sponsorship, String> {

}
