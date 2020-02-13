package com.coviam.apiLock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
public class ApiLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiLockApplication.class, args);
    }

}
