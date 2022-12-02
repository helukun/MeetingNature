package com.example.project_microservice.controller;

import com.example.project_microservice.model.Project;
import com.example.project_microservice.service.ProjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

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
    @DeleteMapping("/project-microservice/project")
    public void deleteProject(String id){
        projectService.deleteProject(id);
    }
}