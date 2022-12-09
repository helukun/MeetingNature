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
 * @version : 1.0
 */

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    //good
    @PostMapping("/project-microservice/project")
    public void addProject(Project newProject){
        projectService.addProject(newProject);
    }

    //good
    @PutMapping("/project-microservice/project")
    public int changeProjectInfo(Project newProject){
        return projectService.changeProjectInfo(newProject);
    }

    //good
    @GetMapping("/project-microservice/project")
    public Optional<Project> findProjectById(String id){
        return projectService.findProjectById(id);
    }

    //goood
    @GetMapping("/project-microservice/project/all")
    public List findAllProject(){
        return projectService.findAllProject();
    }

    //good
    @GetMapping("/project-microservice/project/all/page")
    public Page<Project> findAllProjectByPage(int index,int pageSize){
        return projectService.findProjectByPage(index,pageSize);
    }

    @GetMapping("/project-microservice/project/status")
    public List<Project> findAllProjectByStatus(String status){
        return projectService.findProjectByStatus(status);
    }


    @GetMapping("/project-microservice/project/time")
    public List<Project> findAllProjectByTime(String startTime,String endTime){
        return projectService.findProjectByTime(startTime,endTime);
    }

    //good
    @GetMapping("/project-microservice/project/org")
    public List<Project> findAllProjectByOrg(String organization){
        return projectService.findProjectByOrg(organization);
    }

    //good
    @DeleteMapping("/project-microservice/project")
    public void deleteProject(String id){
        projectService.deleteProject(id);
    }
}