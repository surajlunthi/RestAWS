package com.surajlunthi.RestAWS.repository;

import com.surajlunthi.RestAWS.model.BucketEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BucketEntityRepository extends MongoRepository<BucketEntity,String> {
    List<BucketEntity> findByJobId(String jobId);
}
