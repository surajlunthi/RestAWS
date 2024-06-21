package com.surajlunthi.RestAWS;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Slf4j
@EnableAsync
public class RestAwsApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestAwsApplication.class, args);
	}

}
