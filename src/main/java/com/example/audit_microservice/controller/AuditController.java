package com.example.audit_microservice.controller;

import com.example.audit_microservice.service.AuditService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：ZXM+LJC
 * @description：Project
 * @date ：2022-12-22 19:12
 * @version : 2.0
 */

@CrossOrigin
@RestController
public class AuditController {
    @Autowired
    private AuditService auditService;

    @PutMapping("/v2.0/audit-microservice/projects/status/red")
    public void ProjectStatusToRed(String projectId){
        auditService.ProjectStatusToRed(projectId);
    }

    @PutMapping("/v2.0/audit-microservice/projects/status/green")
    public void ProjectStatusToGreen(String projectId){
        auditService.ProjectStatusToGreen(projectId);
    }
    @PutMapping("/v2.0/audit-microservice/projects/status/yellow")
    public void ProjectStatusToYellow(String projectId){
        auditService.ProjectStatusToYellow(projectId);
    }

}
