package com.surajlunthi.RestAWS.repository;

import com.surajlunthi.RestAWS.model.InstanceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InstanceEntityRepository  extends MongoRepository<InstanceEntity,String> {
    List<InstanceEntity>  findByJobId(String jobId);
}
