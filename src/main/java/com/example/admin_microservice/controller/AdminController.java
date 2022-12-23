package com.example.admin_microservice.controller;

import com.alibaba.fastjson.JSON;
import com.example.admin_microservice.service.AdminService;
import com.github.kevinsawicki.http.HttpRequest;
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
    //good
    @GetMapping("/v2.0/admin-microservice/projects/id")
    public Object FindProByProId(String id){
        return adminService.findProByProId(id);
    }
    //good
    @GetMapping("/v2.0/admin-microservice/feedbcks/id")
    public Object FindFBByProId(String subjectId){
        return adminService.findFBByProId(subjectId);
    }
    //good
    @GetMapping("/v2.0/admin-microservice/notices/id")
    public Object FindNoByProId(String subjectId){
        return adminService.findNotByProId(subjectId);
    }
    //good
    @GetMapping("/v2.0/admin-microservice/conplaints")
    public Object GetAllComplaint(){
        return adminService.GetAllComplaint();
    }
    //good
    @PutMapping("/v2.0/admin-microservice/conplaints/status")
    public void ChangComplaintStatusGreen(String id){
        adminService.ChangComplaintStatusGreen(id);
    }
    //good
    @DeleteMapping("/v2.0/admin-microservice/conplaints")
    public void DeleteComplaint(String id){
        adminService.DeleteComplaint(id);
    }

    @GetMapping("/v2.0/admin-microservice/subject/yellow")
    public Object GetAllYellowProject(String index,String pageSize) {
        return adminService.GetAllYellowProject(index,pageSize);
    }

}
