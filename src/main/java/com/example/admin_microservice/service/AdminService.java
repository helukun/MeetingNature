package com.example.admin_microservice.service;

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
public class AdminService {

    private String AuditMicroserviceIp = "http://121.5.128.97:9011";

    /*项目状态修改为红*/
    public void ProjectStatusToRed(String projectId) {
        HttpRequest.put(AuditMicroserviceIp + "/v2.0/audit-microservice/projects/status/red?projectId=" + projectId).body();
    }

    /*项目状态修改为绿*/
    public void ProjectStatusToGreen(String projectId) {
        HttpRequest.put(AuditMicroserviceIp + "/v2.0/audit-microservice/projects/status/green?projectId=" + projectId).body();
    }

    /*项目状态修改为黄*/
    public void ProjectStatusToYellow(String projectId) {
        HttpRequest.put(AuditMicroserviceIp + "/v2.0/audit-microservice/projects/status/yellow?projectId=" + projectId).body();
    }
}
