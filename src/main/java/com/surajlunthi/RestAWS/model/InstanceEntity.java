package com.surajlunthi.RestAWS.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstanceEntity {
    @Id
    private String id;
    private String jobId;
    private  String imageId;
    private  String instanceId;
    private  String instanceType;
    private  String kernelId;
    private  String keyName;
    private  String platform;
    private  String privateDnsName;
    private  String privateIpAddress;
    private  String publicDnsName;
    private  String publicIpAddress;
    private  String ramdiskId;
    private  String stateTransitionReason;
    private  String subnetId;
    private  String vpcId;
    private  String architecture;
    private  String clientToken;
    private  Boolean ebsOptimized;
    private  Boolean enaSupport;
    private  String hypervisor;
    private  String instanceLifecycle;
    private  String outpostArn;
    private  String rootDeviceName;
    private  String rootDeviceType;
    private  Boolean sourceDestCheck;
    private  String spotInstanceRequestId;
    private  String sriovNetSupport;
    private  String virtualizationType;
    private  String capacityReservationId;
    private  String bootMode;
    private  String platformDetails;
    private  String usageOperation;
    private  String ipv6Address;
    private  String tpmSupport;
    private  String currentInstanceBootMode;

}
