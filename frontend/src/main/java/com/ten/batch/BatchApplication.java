package com.ten.batch;

import com.ten.batch.utils.IdWorker;
import com.ten.batch.utils.JwtUtils;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin(origins = "*")
@SpringBootApplication(scanBasePackages = "com.ten.batch")
@EntityScan(basePackages = {"com.ten.batch.entity"})
public class BatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
}
