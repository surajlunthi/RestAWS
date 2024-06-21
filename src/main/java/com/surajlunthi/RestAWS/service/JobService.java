package com.surajlunthi.RestAWS.service;

import com.surajlunthi.RestAWS.model.*;
import com.surajlunthi.RestAWS.repository.BucketEntityRepository;
import com.surajlunthi.RestAWS.repository.InstanceEntityRepository;
import com.surajlunthi.RestAWS.repository.JobRepository;
import com.surajlunthi.RestAWS.repository.ObjectEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
public class JobService {

    private static final Logger log = LoggerFactory.getLogger(JobService.class);
    private final BucketEntityRepository bucketRepository;
    private final InstanceEntityRepository instanceRepository;
    private final JobRepository jobRepository;
    private final ObjectEntityRepository objectEntityRepository;

    @Autowired
    @Qualifier("asyncExecutor")
    private Executor asyncExecutor;

    public JobService(InstanceEntityRepository instanceRepository, BucketEntityRepository bucketRepository,JobRepository jobRepository,ObjectEntityRepository objectEntityRepository) {
        this.instanceRepository = instanceRepository;
        this.bucketRepository = bucketRepository;
        this.jobRepository = jobRepository;
        this.objectEntityRepository = objectEntityRepository;
    }

    @Async("asyncExecutor")
    @Transactional
    public CompletableFuture<String> saveJob(String jobId, JobStatus jobStatus) {
        if (jobStatus == JobStatus.IN_PROGRESS || jobStatus == JobStatus.FAILED) {
            jobRepository.save(Job.builder().jobId(jobId).status(jobStatus).build());
            log.info("Saved job with jobId {} and status {}", jobId, jobStatus);
        } else {

            List<BucketEntity> bucketEntityList = bucketRepository.findByJobId(jobId);
            List<InstanceEntity> instanceEntityList = instanceRepository.findByJobId(jobId);
            List<ObjectEntity> objectEntity = objectEntityRepository.findByJobId(jobId);

            log.info("Found {} bucket entities for job {}", bucketEntityList.size(), jobId);
            log.info("Found {} instance entities for job {}", instanceEntityList.size(), jobId);
            log.info("Found {} object entities for job {}", objectEntity.size(), jobId);

            Job existingJob = jobRepository.findByJobId(jobId).orElse(new Job());

            existingJob.setJobId(jobId);
            existingJob.setStatus(jobStatus);
            existingJob.setBucketEntity(bucketEntityList);
            existingJob.setInstanceEntity(instanceEntityList);

            jobRepository.save(existingJob);
            log.info("Saved job with jobId {} and updated status {}", jobId, jobStatus);

        }
        return CompletableFuture.completedFuture(jobId);
    }

    public Job findByJobId(String jobId){
        Optional<Job> job = jobRepository.findByJobId(jobId);
        return job.orElse(null);

    }
}
