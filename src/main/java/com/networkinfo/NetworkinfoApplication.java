package com.networkinfo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.networkinfo.dao")

@SpringBootApplication
public class NetworkinfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetworkinfoApplication.class, args);
    }

}
