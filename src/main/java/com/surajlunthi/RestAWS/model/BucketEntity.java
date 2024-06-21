package com.surajlunthi.RestAWS.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BucketEntity {
    @Id
    private String id;
    private String jobId;
    private  String name;
    private  Instant creationDate;
}
