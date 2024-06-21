package com.surajlunthi.RestAWS.service;

import com.surajlunthi.RestAWS.model.BucketEntity;
import com.surajlunthi.RestAWS.repository.BucketEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BucketEntityService {
    @Autowired
    private BucketEntityRepository bucketEntityRepository;

    public List<String> getBuckets(){
        return bucketEntityRepository
                .findAll()
                .stream()
                .map(BucketEntity::getName)
                .collect(Collectors.toSet()).stream().toList();
    }
}
