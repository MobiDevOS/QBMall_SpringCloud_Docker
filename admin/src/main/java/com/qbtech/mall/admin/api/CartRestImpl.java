package com.qbtech.mall.admin.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.qbtech.mall.admin.client.OrderFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
@RequestMapping("/api")
public class CartRestImpl implements CartRest {

	private static final Logger logger = LoggerFactory.getLogger(CartRestImpl.class);

	@Value("${testValue}")
	String helloWord;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	OrderFeignClient orderFeignClient;

	@RequestMapping(value = "/getList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	@ResponseBody
	@Override
	public String getList() {
		return helloWord;
	}



	/*
	*添加熔断机制；ignoreExceptions 过滤掉特定的exception
	*commandProperties 熔断条件
	*threadPoolProperties 线程池配置
	*具体参数配置参考wiki
	*
	*
			commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000"),
					@HystrixProperty(name = "metrics.rollingState.timeInMilliseconds",value = "1000")},
			threadPoolProperties = {@HystrixProperty(name = "coreSize",value = "1"),
									@HystrixProperty(name = "maxQueueSize",value = "10")}
	**/
	@HystrixCommand(fallbackMethod = "getOrderListFiled",
			ignoreExceptions = {IllegalArgumentException.class})
	@RequestMapping(value = "/getOrderList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public String getOrderList(){
		String  url = "http://localhost:8505/order/getList";
		//请求order服务
		return this.restTemplate.getForObject(url,String.class);
	}


	public String getOrderListFiled(){
		return "Hystrix-failed";
	}

	@RequestMapping(value = "/testOrder", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public String getOrderListByOrderService(){
		return orderFeignClient.getOrderList();
	}
}
