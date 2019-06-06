package com.qbtech.mall.getway;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class AccessFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Object run() {
        //log.info("Enter the AccessFilter...");
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.set("startTime", System.currentTimeMillis());
        HttpServletRequest request = ctx.getRequest();
        // 不需要鉴权直接放行的接口，在此定义
        if (request.getRequestURI().equals("/account/user/login/password")
                || request.getRequestURI().equals("/account/user/validatecode/send")
                || request.getRequestURI().equals("/account/user/login/validatecode")
                || request.getRequestURI().equals("/account/user/password/forget")
                || request.getRequestURI().equals("/account/user/register")
                || request.getRequestURI().equals("/account/user/register/checkMobile")) {
            //log.info("access token is empty");
            return null;
        }

        // 取得token值
        String authHeader = request.getHeader("Authorization");
        //
        if (authHeader != null && !"".equals(authHeader)) {
            // 通过token值从redis中取得用户信息
            String userInfoStr = redisTemplate.opsForValue().get(authHeader);
            // 用户信息存在的场合，将用户信息放入header中传递给其它服务
            if (userInfoStr != null && !"".equals(userInfoStr)) {
                try {
                    ctx.addZuulRequestHeader("KEY_USERINFO_IN_HTTP_HEADER",new String(URLDecoder.decode(userInfoStr,"UTF-8")));
                    if (userInfoStr.contains("17700010001")) {// 体验账号刷新token
                        // 刷新登录时间
                        redisTemplate.opsForValue().set(authHeader, userInfoStr,3, TimeUnit.DAYS);
                    } else {
                        // 刷新登录时间
                        redisTemplate.opsForValue().set(authHeader, userInfoStr,30, TimeUnit.DAYS);
                    }
                } catch (UnsupportedEncodingException e) {
                    //log.error("用户信息设置错误",e);
                }
                return null;
            }
        }

        // 没有token值或者用户信息不存在的场合、返回401：认证失败
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(200);
        ctx.setResponseBody("{\"code\":401,\"message\":\"认证失败\",\"data\":\"\"}");
        ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        return null;
    }

}
