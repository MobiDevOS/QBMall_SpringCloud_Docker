package com.qbtech.mall.order;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@EnableFeignClients
@SpringCloudApplication
@EnableDiscoveryClient
public class OrderApplication {

	public static void main(String[] args){
		SpringApplication.run(OrderApplication.class,args);
	}

}
