package com.example.project_microservice.controller;

import com.example.project_microservice.model.Project;
import com.example.project_microservice.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


/**
 * @author ：ZXM+LJC
 * @description：ProjectController
 * @date ：2022-12-9 15:18
 * @version : 1.1
 */

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    //good
    @PostMapping("/v1.1/project-microservice/projects")
    public void addProject(Project newProject){
        projectService.addProject(newProject);
    }

    //good
    @PutMapping("/v1.1/project-microservice/projects")
    public int changeProjectInfo(Project newProject){
        return projectService.changeProjectInfo(newProject);
    }

    //good
    @GetMapping("/v1.1/project-microservice/projects/Id")
    public Optional<Project> findProjectById(String id){
        return projectService.findProjectById(id);
    }

    //goood
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
    @GetMapping("/v1.1/project-microservice/projects/organizaation")
    public List<Project> findAllProjectByOrg(String organization){
        return projectService.findProjectByOrg(organization);
    }

    //good
    @DeleteMapping("/v1.1/project-microservice/projects")
    public void deleteProject(String id){
        projectService.deleteProject(id);
    }
}