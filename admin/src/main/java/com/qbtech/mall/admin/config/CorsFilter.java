package com.qbtech.mall.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther:  过滤器 解决跨域问题，注意单体服务整合zuul的时候只在zuul中处理即可不需要每个单体服务在处理了
 *
 *  如果不加response.setHeader("Access-Control-Allow-Origin","*");
 *  在html中设置是无效的、浏览器默认去head的设置。
 *  <meta http-equiv="Access-Control-Allow-Origin" content="*">
 * @Description:
 */
public class CorsFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(CorsFilter.class);
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;

        HttpServletRequest reqs = (HttpServletRequest) req;
        logger.info("域信息："+reqs.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Origin",reqs.getHeader("Origin"));
        //response.setHeader("Access-Control-Allow-Origin","http://localhost:8503");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

}