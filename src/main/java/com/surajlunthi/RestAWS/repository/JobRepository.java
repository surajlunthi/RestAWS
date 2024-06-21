package com.surajlunthi.RestAWS.repository;

import com.surajlunthi.RestAWS.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JobRepository extends MongoRepository<Job,String> {
    Optional<Job> findByJobId(String jobId);
}
