package com.example.project_microservice.controller;

import com.example.project_microservice.model.Project;
import com.example.project_microservice.service.OSSService;
import com.example.project_microservice.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


/**
 * @author ：ZXM+LJC
 * @description：ProjectController
 * @date ：2022-12-9 15:18
 * @version : 1.1
 */

@CrossOrigin
@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;


    @Autowired
    private OSSService ossService;

    //good
    @PostMapping("/v1.1/project-microservice/projects")
    public String addProject(Project newProject){
        return projectService.addProject(newProject);
    }

    //good
    @PostMapping("/v1.1/project-microservice/projects/picture")
    public String addPicture(String id, MultipartFile picture, HttpServletRequest request) throws IOException {
        return projectService.addPicture(id,picture,request);
    }

    //good
    @PostMapping("/v1.1/project-microservice/projects/picture/con")
    public String addPicCon(String id, MultipartFile picture){
        return projectService.addPicCon(id,picture,"sss");
    }


    @PostMapping("/v1.1/project-microservice/projects/picPathOnly")
    public String addPicPathOnly(String id,String newPath){
        return projectService.addPicPathOnly(id,newPath);
    }

    //good
    @DeleteMapping("/v1.1/project-microservice/projects")
    public void deleteProject(String id){
        projectService.deleteProject(id);
    }

    //good
    @DeleteMapping("/v1.1/project-microservice/projects/deletePic")
    public void deletePicByProId(String id,String picPath){
        projectService.deletePicByProId(id,picPath);
    }

    //good
    @PutMapping("/v1.1/project-microservice/projects")
    public int changeProjectInfo(Project newProject){
        return projectService.changeProjectInfo(newProject);
    }

    //good
    @GetMapping("/v1.1/project-microservice/projects/Id")
    public Project findProjectById(String id){
        return projectService.findProjectById(id);
    }

    //good
    @GetMapping("/v1.1/project-microservice/projects")
    public List findAllProject(){
        return projectService.findAllProject();
    }

    //good
    @GetMapping("/v1.1/project-microservice/projects/page")
    public Page<Project> findAllProjectByPage(int index,int pageSize){
        return projectService.findProjectByPage(index,pageSize);
    }

    @GetMapping("/v1.1/project-microservice/projects/status")
    public List<Project> findAllProjectByStatus(String status){
        return projectService.findProjectByStatus(status);
    }


    @GetMapping("/v1.1/project-microservice/projects/time")
    public List<Project> findAllProjectByTime(String startTime,String endTime){
        return projectService.findProjectByTime(startTime,endTime);
    }

    //good
    @GetMapping("/v1.1/project-microservice/projects/organization")
    public List findAllProjectByOrg(String organization){
        return projectService.findProjectByOrg(organization);
    }

    //good
    @GetMapping("/v1.1/project-microservice/proId/organization")
    public List findAllProIdByOrg(String organization){
        return projectService.findProIdByOrg(organization);
    }

    //good
    @GetMapping("/v1.1/project-microservice/projects/organizationPlusPage")
    public Page findAllProjectByOrgPlusPage(String organization,int index,int pageSize){
        return projectService.findProjectByOrgPlusPage(organization,index,pageSize);
    }

    //good
    @GetMapping("/v1.1/project-microservice/projects/random")
    public List disRPInfo(String size){
        return projectService.displayRPInfo(size);
    }
}