package com.surajlunthi.RestAWS.service;

import com.surajlunthi.RestAWS.model.BucketData;
import com.surajlunthi.RestAWS.model.BucketEntity;
import com.surajlunthi.RestAWS.model.JobStatus;
import com.surajlunthi.RestAWS.model.ObjectEntity;
import com.surajlunthi.RestAWS.repository.ObjectEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ObjectEntityService {

    private final ObjectEntityRepository objectEntityRepository;

    @Autowired
    public ObjectEntityService(ObjectEntityRepository objectEntityRepository) {
        this.objectEntityRepository = objectEntityRepository;
    }

    public Integer findBucketCount(String bucket) {
        log.info("Fetching all objects from the bucket");
        ObjectEntity bucketEntityList = objectEntityRepository.findByBucket(bucket);
        return bucketEntityList == null ?0:bucketEntityList.getBucketData().size();
    }

    public List<String> getBucketObjectLike(String bucket,String pattern){
        List<String> matched = new ArrayList<>();
        if(objectEntityRepository.findByBucket(bucket) == null){
            return matched;
        }
        List<BucketData> objectEntities =  objectEntityRepository.findByBucket(bucket).getBucketData();

        Pattern regexPattern = Pattern.compile(pattern);
        for (BucketData objectSummary : objectEntities) {
            String objectKey = objectSummary.getKey();
            if (regexPattern.matcher(objectKey).find()) {
                matched.add(objectKey);
            }
        }
        return matched;
    }


}
