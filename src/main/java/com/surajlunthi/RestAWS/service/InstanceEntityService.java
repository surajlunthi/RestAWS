package com.surajlunthi.RestAWS.service;

import com.surajlunthi.RestAWS.model.InstanceEntity;
import com.surajlunthi.RestAWS.repository.InstanceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstanceEntityService {
    @Autowired
    public InstanceEntityRepository instanceEntityRepository;

    public List<String> findAllInstanceIds(){
        return instanceEntityRepository.findAll()
                .stream()
                .map(InstanceEntity::getInstanceId)
                .collect(Collectors.toSet())
                .stream().toList();
    }

}
