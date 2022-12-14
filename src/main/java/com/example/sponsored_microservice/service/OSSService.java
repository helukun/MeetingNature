package com.example.sponsored_microservice.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.example.sponsored_microservice.config.OSSConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OSSService {
    @Autowired
    private OSS ossClient;
    @Autowired
    private OSSConfiguration ossConfiguration;

    public String uploadFile(MultipartFile file, String storagePath) {
        String fileName = "";
        try {
            String originFileName = file.getOriginalFilename();
            String fileType = originFileName.substring(originFileName.lastIndexOf('.'),originFileName.length());
            fileName = UUID.randomUUID().toString();
            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            fileName = storagePath + "/" + fileName + fileType;
            // 上传文件
            ossClient.putObject(ossConfiguration.getBucketName(), fileName, inputStream, objectMetadata);
        } catch (IOException e) {
        }
        return fileName;
    }
}