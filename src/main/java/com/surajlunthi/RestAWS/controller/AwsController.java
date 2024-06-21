package com.surajlunthi.RestAWS.controller;

import com.surajlunthi.RestAWS.model.Job;
import com.surajlunthi.RestAWS.model.JobStatus;
import com.surajlunthi.RestAWS.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
public class AwsController {
    private final AwsService awsService;
    private final JobService jobService;
    private final InstanceEntityService instanceEntityService;
    private final BucketEntityService bucketEntityService;
    @Autowired
    private  ObjectEntityService objectEntityService;
    @Autowired
    public AwsController(AwsService awsService,JobService jobService,InstanceEntityService instanceEntityService,BucketEntityService bucketEntityService) {
        this.jobService=jobService;
        this.awsService = awsService;
        this.instanceEntityService = instanceEntityService;
        this.bucketEntityService = bucketEntityService;
    }

    @PostMapping("/services")
    public String discoverServices(@RequestBody List<String> services) {
        UUID jobId = UUID.randomUUID();
        jobService.saveJob(jobId.toString(),JobStatus.IN_PROGRESS);
        log.info("Discover services for jobID : {} and services : {}", jobId, services);

        CompletableFuture<String> ec2Task = awsService.discoverEC2Instances(jobId.toString())
                .thenApplyAsync(result -> {
                    log.info("EC2 discovery completed for job {}", jobId);
                    return result;
                });

        CompletableFuture<String> s3Task = awsService.discoverS3Buckets(jobId.toString())
                .thenApplyAsync(result -> {
                    log.info("S3 discovery completed for job {}", jobId);
                    return result;
                });

        CompletableFuture.allOf(ec2Task, s3Task)
                .thenRunAsync(() -> {
                    log.info("All tasks completed for job {}", jobId);
                    CompletableFuture<String> saveJobFuture = jobService.saveJob(jobId.toString(), JobStatus.SUCCESS);
                })
                .exceptionally(ex -> {
                    log.error("Failed to complete tasks for job {}: {}", jobId, ex.getMessage());
                    return null;
                });

        return jobId.toString();
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobStatus> getJobResult(@PathVariable String jobId) {
        Job job = jobService.findByJobId(jobId);
        if (job != null) {
            return ResponseEntity.ok(job.getStatus());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/service")
    public ResponseEntity<List<String>> getDiscoveryResult(String service){
        List<String> result;
        if(service.equals("EC2")){
            result = instanceEntityService.findAllInstanceIds();
        }else {
           result= bucketEntityService.getBuckets();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("bucket/{bucketName}")
    public String getS3BucketObjects(@PathVariable String bucketName) {
        UUID jobId = UUID.randomUUID();

        log.info("getS3BucketObjects services for jobID : {}", jobId);

        CompletableFuture<String> objectTask = awsService.discoverObjectsFromS3Buckets(jobId.toString(),bucketName)
                .thenApplyAsync(result -> {
                    log.info("getS3BucketObjects completed for job {}", jobId);
                    return result;
                });

        return jobId.toString();
    }

    @GetMapping("/bucketCount/{bucket}")
    public ResponseEntity<Integer> getS3BucketObjectCount(@PathVariable String bucket) {
        int count = objectEntityService.findBucketCount(bucket);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/bucketMatching/{bucketName}")
    public ResponseEntity<List<String>> getMatchingObjects(
            @PathVariable String bucketName,
            @RequestParam("pattern") String pattern){
    int count = objectEntityService.findBucketCount(bucketName);
        if(count == 0){
            UUID jobId = UUID.randomUUID();
            awsService.discoverObjectsFromS3Buckets(jobId.toString(),bucketName)
                    .thenApplyAsync(result -> {
                        log.info("getS3BucketObjectLike completed for job {}", jobId);
                        return result;
                    });
        }
        return ResponseEntity.ok(objectEntityService.getBucketObjectLike(bucketName,pattern));
    }


}

