package com.qbtech.mall.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 对应最核心业务层
 * */
@EnableFeignClients
@SpringCloudApplication
@EnableDiscoveryClient
@EnableHystrix
public class AdminApplication {

	public static void main(String args[]){
		SpringApplication.run(AdminApplication.class, args);
	}

}
