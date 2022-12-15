package com.example.sponsored_microservice.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class OSSConfiguration {
    private String endPoint = "https://oss-cn-shanghai.aliyuncs.com";// 地域节点
    private String accessKeyId = "LTAI5t8A5fZEVnXHDvmagwZt";
    private String accessKeySecret = "b23SvypcKCkVgUoQXAfI5A9kgVH98I";
    private String bucketName = "meeting-nature";// OSS的Bucket名称
    private String urlPrefix;// Bucket 域名

    // 将OSS 客户端交给Spring容器托管
    @Bean
    public OSS OSSClient() {
        return new OSSClient(endPoint, accessKeyId, accessKeySecret);
    }
}