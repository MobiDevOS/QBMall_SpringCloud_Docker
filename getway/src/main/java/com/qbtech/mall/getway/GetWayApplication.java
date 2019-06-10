package com.qbtech.mall.getway;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * 网关层，拦截特定url 或者处理单点登录等操作
 * */

@SpringCloudApplication
@EnableZuulProxy
public class GetWayApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(GetWayApplication.class).web(true).run(args);
	}
	@Bean
	public DefaultErrorAttributes errorAttributes() {
		return new CustomErrorAttributes();
	}
}
