package com.surajlunthi.RestAWS.repository;

import com.surajlunthi.RestAWS.model.ObjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;


public interface ObjectEntityRepository extends MongoRepository<ObjectEntity, String> {
    ObjectEntity findByBucket(String bucket);
    List<ObjectEntity>  findByJobId(String jobId);
}