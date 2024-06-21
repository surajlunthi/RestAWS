package com.surajlunthi.RestAWS.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Id
    private String id;
    private String jobId;
    private List<BucketEntity> bucketEntity = new ArrayList<>();
    private List<InstanceEntity> instanceEntity = new ArrayList<>();
    private JobStatus status;
    private boolean isDeleted;
    private LocalDateTime createdAt;

    @Version
    private Long version;
}
