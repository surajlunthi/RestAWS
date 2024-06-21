package com.surajlunthi.RestAWS.service;

import com.surajlunthi.RestAWS.model.BucketData;
import com.surajlunthi.RestAWS.model.InstanceEntity;
import com.surajlunthi.RestAWS.model.JobStatus;
import com.surajlunthi.RestAWS.repository.BucketEntityRepository;
import com.surajlunthi.RestAWS.repository.InstanceEntityRepository;
import com.surajlunthi.RestAWS.repository.JobRepository;
import com.surajlunthi.RestAWS.repository.ObjectEntityRepository;
import com.surajlunthi.RestAWS.util.EntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.surajlunthi.RestAWS.util.EntityMapper.objectEntityFromS3Object;

@Service
public class AwsService {

    private static final Logger log = LoggerFactory.getLogger(AwsService.class);
    private final Ec2Client ec2Client;
    private final InstanceEntityRepository instanceRepository;
    private final BucketEntityRepository bucketRepository;
    private final S3Client s3Client;


    @Autowired
    @Qualifier("asyncExecutor")
    private Executor asyncExecutor;
    @Autowired
    private JobService jobService;
    @Autowired
    private ObjectEntityRepository objectEntityRepository;

    @Autowired
    public AwsService(Ec2Client ec2Client, InstanceEntityRepository instanceRepository,S3Client s3Client,BucketEntityRepository bucketRepository) {
        this.ec2Client = ec2Client;
        this.instanceRepository = instanceRepository;
        this.s3Client = s3Client;
        this.bucketRepository = bucketRepository;
    }
    @Async("asyncExecutor")
    @Transactional
    public CompletableFuture<String> discoverEC2Instances(String jobId) {
        try {
            DescribeInstancesResponse response = ec2Client.describeInstances(DescribeInstancesRequest.builder().build());

            List<Instance> instances = response.reservations().stream()
                    .flatMap(r -> r.instances().stream())
                    .toList();

            instances.forEach(instance -> {
                log.info("Thread :{} Instance ID: {}", Thread.currentThread().getName(), instance.instanceId());
                InstanceEntity instanceEntity = EntityMapper.instanceEntityFromInstance(instance, jobId);
                instanceRepository.save(instanceEntity);
            });

        }
        catch (Exception ex){
            log.error("Error in discoverEC2Instances: {}", ex.getMessage());
            jobService.saveJob(jobId,JobStatus.FAILED);

        }
        return CompletableFuture.completedFuture(jobId);
    }

    @Async("asyncExecutor")
    @Transactional
    public CompletableFuture<String> discoverS3Buckets(String jobId) {
        try {
            ListBucketsResponse response = s3Client.listBuckets();
            List<Bucket> bucketList = response.buckets();
            for (Bucket bucket : bucketList) {
                log.info("Thread :{} Bucket name :{}", Thread.currentThread().getName(), bucket.name());
                bucketRepository.save(EntityMapper.bucketEntityFromBucket(bucket, jobId));
            }
            log.info("Success discoverS3Buckets for jobId :{}",jobId);
        }
        catch (Exception ex){
            log.error("Error in discoverS3Buckets: {}", ex.getMessage());
            jobService.saveJob(jobId,JobStatus.FAILED);
        }
        return CompletableFuture.completedFuture(jobId);
    }

    @Async("asyncExecutor")
    @Transactional
    public CompletableFuture<String> discoverObjectsFromS3Buckets(String jobId,String bucketName) {
        try {
            ListObjectsRequest listObjects = ListObjectsRequest
                    .builder()
                    .bucket(bucketName)
                    .build();

            ListObjectsResponse res = s3Client.listObjects(listObjects);
            List<S3Object> objects = res.contents();
            List<BucketData> bucketData = new ArrayList<>();
            for (S3Object myValue : objects) {
                BucketData temp = BucketData.builder()
                        .key(myValue.key())
                        .lastModified(myValue.lastModified())
                        .eTag(myValue.eTag())
                        .size(myValue.size())
                        .storageClass(myValue.storageClass().toString())
                        .build();
                bucketData.add(temp);
                log.info("Success discoverObjectsFromS3Buckets for jobId :{}  with key :{} with bucket :{} ",jobId,myValue.key(),bucketName);
            }
            log.info("save discoverObjectsFromS3Buckets for jobId :{}",jobId);
            objectEntityRepository.save(objectEntityFromS3Object(bucketData,jobId,bucketName));


        }
        catch (Exception ex){
            log.error("Error in discoverObjectsFromS3Buckets: {}", ex.getMessage());
        }
        return CompletableFuture.completedFuture(jobId);
    }

}
