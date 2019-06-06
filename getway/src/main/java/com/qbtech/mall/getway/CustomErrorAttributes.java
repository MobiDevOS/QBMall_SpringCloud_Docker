package com.qbtech.mall.getway;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.web.context.request.RequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * @see org.springframework.boot.autoconfigure.web.BasicErrorController
 * @see ErrorMvcAutoConfiguration
 */
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        Map<String, Object> result = new HashMap<>();
        result.put("code", errorAttributes.get("status"));
        result.put("message", errorAttributes.get("message"));
        result.put("data", "");
        return result;
    }
}
