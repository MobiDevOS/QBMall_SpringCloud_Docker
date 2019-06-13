package com.qbtech.mall.admin.thirdpart.qiniu;
 
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * 七牛配置类
 */
@Configuration
public class QiniuUploadFileConfig {
 
    /**
     * 华东机房,配置自己空间所在的区域
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() {
        return new com.qiniu.storage.Configuration(Zone.zone0());
    }
 
    /**
     * 构建一个七牛上传工具实例
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }
 
    @Value("${qiniu.AccessKey}")
    private String accessKey;
    @Value("${qiniu.SecretKey}")
    private String secretKey;
 
    /**
     * 认证信息实例
     * @return
     */
    @Bean
    public Auth auth() {
        return Auth.create(accessKey, secretKey);
    }
 
    /**
     * 构建七牛空间管理实例
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }
 
    @Bean
    public Gson gson() {
        return new Gson();
    }

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize("10240KB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }
}
 