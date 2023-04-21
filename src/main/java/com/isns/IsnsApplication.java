package com.isns;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.isns.mapper")
public class IsnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IsnsApplication.class, args);
    }

}
