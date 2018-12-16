package com.linda.homework.lindaindoornavigation.linstart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.linda.homework.lindaindoornavigation.model.mapper")
@ComponentScan(basePackages = {"com.linda.homework.lindaindoornavigation"})
public class LindaIndoorNavigationApplication {
    public static void main(String[] args) {
        SpringApplication.run(LindaIndoorNavigationApplication.class, args);
    }
}