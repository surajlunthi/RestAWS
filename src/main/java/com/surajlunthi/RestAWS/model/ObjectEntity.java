package com.surajlunthi.RestAWS.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;


import java.time.Instant;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectEntity {
    @Id
    private  String id;
    private String jobId;
    private List<BucketData> bucketData;
    private String bucket;
    private  JobStatus status;
    @Version
    private Long version;
}
