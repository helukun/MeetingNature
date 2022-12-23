package com.example.audit_microservice.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.kevinsawicki.http.HttpRequest;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuditService {

    private String ProjectMicroserviceIp = "http://121.5.128.97:9006";

    /*项目状态修改为红*/
    public void ProjectStatusToRed(String projectId) {
        HttpRequest.put(ProjectMicroserviceIp + "/v2.0/project-microservice/projects/status/?projectId=" + projectId
                + "&status=" + "red");
    }

    /*项目状态修改为绿*/
    public void ProjectStatusToGreen(String projectId) {
        HttpRequest.put(ProjectMicroserviceIp + "/v2.0/project-microservice/projects/status?projectId=" + projectId
                + "&status=" + "green");
    }

    /*项目状态修改为黄*/
    public void ProjectStatusToYellow(String projectId) {
        HttpRequest.put(ProjectMicroserviceIp + "/v2.0/project-microservice/projects/status?projectId=" + projectId
                + "&status=" + "yellow");
    }
}