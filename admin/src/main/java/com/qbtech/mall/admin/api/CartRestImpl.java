package com.qbtech.mall.admin.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope
@RequestMapping("/api")
public class CartRestImpl implements CartRest {


	@Value("${testValue}")
	String helloWord;

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/getList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	@Override
	public String getList() {
		return helloWord;
	}




	@HystrixCommand(fallbackMethod = "getOrderListFiled")
	@RequestMapping(value = "/getOrderList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public String getOrderList(){
		String  url = "http://localhost:8505/order/getList";
		//请求order服务
		return this.restTemplate.getForObject(url,String.class);
	}


	public String getOrderListFiled(){
		return "Hystrix-failed";
	}
}
