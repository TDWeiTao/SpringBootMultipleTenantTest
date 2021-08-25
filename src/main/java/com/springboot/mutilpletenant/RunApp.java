package com.springboot.mutilpletenant;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.springboot.mutilpletenant.mapper")
public class RunApp {

    public static void main(String[] args) {
        SpringApplication.run(RunApp.class);
    }
}
