package com.surajlunthi.RestAWS.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.model.Owner;

import java.time.Instant;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BucketData {
    private  String key;
    private Instant lastModified;
    private  String eTag;
    private  Long size;
    private  String storageClass;
}
