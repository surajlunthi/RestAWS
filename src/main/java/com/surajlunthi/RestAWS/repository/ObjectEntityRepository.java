package com.surajlunthi.RestAWS.repository;


import com.surajlunthi.RestAWS.model.InstanceEntity;
import com.surajlunthi.RestAWS.model.JobStatus;
import com.surajlunthi.RestAWS.model.ObjectEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ObjectEntityRepository extends MongoRepository<ObjectEntity, String> {
    ObjectEntity findByBucket(String bucket);
}