package com.example.admin_microservice.controller;

import com.example.admin_microservice.service.AdminService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：ZXM+LJC
 * @description：Audit_Microservice
 * @date ：2022-12-22 19:12
 * @version : 2.0
 */

@CrossOrigin
@RestController
public class AdminController {
    @Autowired
    private AdminService adminService;

    //good
    @PutMapping("/v2.0/admin-microservice/projects/status/red")
    public void ProjectStatusToRed(String projectId){
        adminService.ProjectStatusToRed(projectId);
    }
    //good
    @PutMapping("/v2.0/admin-microservice/projects/status/green")
    public void ProjectStatusToGreen(String projectId){
        adminService.ProjectStatusToGreen(projectId);
    }
    //good
    @PutMapping("/v2.0/admin-microservice/projects/status/yellow")
    public void ProjectStatusToYellow(String projectId){
        adminService.ProjectStatusToYellow(projectId);
    }

}
