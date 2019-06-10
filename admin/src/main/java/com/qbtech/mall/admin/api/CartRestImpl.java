package com.qbtech.mall.admin.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartRestImpl implements CartRest {

	@RequestMapping(value = "/getList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	@Override
	public String getList() {
		return "Hello Spring Cloud";
	}
}
