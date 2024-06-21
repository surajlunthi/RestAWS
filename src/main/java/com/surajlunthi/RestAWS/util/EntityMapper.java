package com.surajlunthi.RestAWS.util;

import com.surajlunthi.RestAWS.model.*;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

public class EntityMapper {

    public static InstanceEntity instanceEntityFromInstance(Instance awsInstance,String jobId) {
        return InstanceEntity.builder()
                .imageId(awsInstance.imageId())
                .instanceId(awsInstance.instanceId())
                .instanceType(awsInstance.instanceTypeAsString())
                .kernelId(awsInstance.kernelId())
                .keyName(awsInstance.keyName())
                .platform(awsInstance.platformAsString())
                .privateDnsName(awsInstance.privateDnsName())
                .privateIpAddress(awsInstance.privateIpAddress())
                .publicDnsName(awsInstance.publicDnsName())
                .publicIpAddress(awsInstance.publicIpAddress())
                .ramdiskId(awsInstance.ramdiskId())
                .stateTransitionReason(awsInstance.stateTransitionReason())
                .subnetId(awsInstance.subnetId())
                .vpcId(awsInstance.vpcId())
                .architecture(awsInstance.architectureAsString())
                .clientToken(awsInstance.clientToken())
                .ebsOptimized(awsInstance.ebsOptimized())
                .enaSupport(awsInstance.enaSupport())
                .hypervisor(awsInstance.hypervisorAsString())
                .instanceLifecycle(awsInstance.instanceLifecycleAsString())
                .outpostArn(awsInstance.outpostArn())
                .rootDeviceName(awsInstance.rootDeviceName())
                .rootDeviceType(awsInstance.rootDeviceTypeAsString())
                .sourceDestCheck(awsInstance.sourceDestCheck())
                .spotInstanceRequestId(awsInstance.spotInstanceRequestId())
                .sriovNetSupport(awsInstance.sriovNetSupport())
                .virtualizationType(awsInstance.virtualizationTypeAsString())
                .capacityReservationId(awsInstance.capacityReservationId())
                .bootMode(awsInstance.bootModeAsString())
                .platformDetails(awsInstance.platformDetails())
                .ipv6Address(awsInstance.ipv6Address())
                .tpmSupport(awsInstance.tpmSupport())
                .currentInstanceBootMode(awsInstance.currentInstanceBootModeAsString())
                .jobId(jobId)
                .build();
    }
    public static BucketEntity bucketEntityFromBucket(Bucket bucket,String jobId){
        return BucketEntity.builder()
                .creationDate(bucket.creationDate())
                .name(bucket.name())
                .jobId(jobId)
                .build();
    }

    public static ObjectEntity objectEntityFromS3Object(List<BucketData> bucketData, String jobId, String bucketName){
        return ObjectEntity.builder()
                .jobId(jobId)
                .bucketData(bucketData)
                .status(JobStatus.SUCCESS)
                .bucket(bucketName)
                .build();
    }
}
