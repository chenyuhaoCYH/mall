package com.cyh.mall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//开器注册中心
@EnableDiscoveryClient
@SpringBootApplication
//mapper包扫描
@MapperScan("com.cyh.mall.coupon.dao")
//启用openFeign
@EnableFeignClients(basePackages = "com.cyh.mall.coupon.rpc")
public class MallCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallCouponApplication.class, args);
    }

}
