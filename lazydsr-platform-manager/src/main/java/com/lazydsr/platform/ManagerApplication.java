package com.lazydsr.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheConfig;

/**
 * ManagerApplication
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform
 * Created by Lazy on 2018/6/2 18:38
 * Version: 0.1
 * Info: @TODO:...
 */
@SpringBootApplication
@CacheConfig
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}
