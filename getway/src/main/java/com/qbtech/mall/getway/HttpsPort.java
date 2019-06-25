package com.qbtech.mall.getway;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpsPort {
	
	@Value("${server.port}")
	private  int sPort;
	
	@Value("${http.port}")
	private  int hPort;
	
	private static class Tomcat extends TomcatEmbeddedServletContainerFactory {// 静态内部类
		@Override
	    protected void postProcessContext(Context context) {
	        SecurityConstraint constraint = new SecurityConstraint();
	        constraint.setUserConstraint("CONFIDENTIAL");
	        SecurityCollection collection = new SecurityCollection();
	        collection.addPattern("/*");
	        constraint.addCollection(collection);
	        context.addConstraint(constraint);
	    }
	}
	
	@Bean
    public EmbeddedServletContainerFactory servletContainer() {// 创建新的tomcat示例，指向定义的http连接
		Tomcat tomcat = new Tomcat();
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
	}
	
	@Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(hPort);       
        connector.setSecure(false);
        connector.setRedirectPort(sPort);
        return connector;
    }
	
}