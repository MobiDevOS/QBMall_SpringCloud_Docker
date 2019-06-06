package com.qbtech.mall.config;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@EnableDiscoveryClient
@SpringCloudApplication
public class MallConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallConfigApplication.class, args);
	}

}
