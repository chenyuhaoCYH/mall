package com.cyh.mall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.cyh.mall.product.dao")
public class MallProducetApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallProducetApplication.class, args);
    }

}
