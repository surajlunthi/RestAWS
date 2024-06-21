package com.surajlunthi.RestAWS.config;

import com.surajlunthi.RestAWS.model.InstanceEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.concurrent.Executor;

@Configuration
public class AwsConfig {

    private final Region region = Region.AP_SOUTH_1;

    @Bean("asyncExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("aws-lookup-");
        executor.initialize();
        return executor;
    }


    @Bean
    public Ec2Client ec2Client() {
        return Ec2Client.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(region)
                .build();

    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(region)
                .build();
    }


}