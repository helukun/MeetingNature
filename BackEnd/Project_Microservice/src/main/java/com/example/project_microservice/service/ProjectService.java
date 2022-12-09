package com.example.project_microservice.service;

import com.example.project_microservice.model.Project;
import com.example.project_microservice.dao.ProjectDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：ProjectService
 * @date ：2022-12-9 15:18
 * @version : 1.0
 */

@Service
public class ProjectService {
    @Autowired
    private ProjectDao projectDao;

    public String setNextId(){
        List<Project> projectList=projectDao.findAll();
        int curMaxId=0;
        for(Project p:projectList){
            curMaxId=Math.max(curMaxId,Integer.parseInt(p.getId()));
        }
        int result=curMaxId+1;
        return result+"";
    }

    public int isExist(String id){
        Project checkProject=new Project();
        checkProject.setId(id);
        Example<Project> projectExample=Example.of(checkProject);
        long count = projectDao.count(projectExample);

        int result=0;
        if((int)count!=0){
            result=1;
        }
        return result;
    }

    public void addProject(Project project){
        project.setId(this.setNextId());
        project.setCreateTime(String.valueOf(LocalDateTime.now()));
        project.setStatus("yellow");
        project.setFollowerList(new ArrayList<>());
        int exist=this.isExist(project.getId());
        if(exist!=1){
            projectDao.save(project);
            System.out.println("添加成功！");
        }
        else{
            System.out.println("该项目已存在，无法再次添加！");
        }
    }

    public List findAllProject(){
        List<Project> projectList = projectDao.findAll();
        return projectList;
    }

    public Optional<Project> findProjectById(String id){
        Optional<Project> project=null;

        Project checkProject3=new Project();
        checkProject3.setId(id);
        Example<Project> projectExample=Example.of(checkProject3);
        long count2 = projectDao.count(projectExample);

        if((int)count2!=0){
            project = projectDao.findById(id);
            System.out.println("找到用户！");
        }
        else{
            System.out.println("该用户不存在");
        }
        System.out.println(count2);
        return project;
    }

    public List<String> getFollowList(String id){
        Optional<Project> project=this.findProjectById(id);
        return project.get().getFollowerList();
    }

    public int changeProjectInfo(Project newProject){
        int exist=this.isExist(newProject.getId());

        if(exist==0){
            System.out.println("该项目不存在");
            return 0;
        }

        Optional<Project> OldUser=this.findProjectById(newProject.getId());

        int legal=1;
        if(newProject.getProjectName().length()>15||
                newProject.getProjectName().length()==0||
                newProject.getProjectName().contains(" ")||
                newProject.getDescribe().length()>200
        ){
            legal=0;
        }

        if(legal==1){
            projectDao.save(newProject);
            System.out.println("修改成功！");
        }
        else{
            System.out.println("修改失败！");
        }
        return legal;
    }

    public void deleteProject(String id){
        projectDao.deleteById(id);
    }

    public List<Project> findProjectByStatus(String status){
        Project checkProject=new Project();
        checkProject.setStatus(status);
        Example<Project> projectExample=Example.of(checkProject);
        List<Project> projectList=projectDao.findAll(projectExample);
        return projectList;
    }

    public List<Project> findProjectByTime(String startTime,String endTime){
        List<Project> projectList=new ArrayList<>();
        List<Project> checkList=projectDao.findAll();
        for(Project p:checkList){
            String curTime=p.getCreateTime();
            int res1=curTime.compareTo(startTime);
            int res2=endTime.compareTo(curTime);
            if((res1>0&&res2>0)||res1==0||res2==0){
                projectList.add(p);
            }
        }
        return projectList;
    }

    public List<Project> findProjectByOrg(String organization){
        Project checkProject=new Project();
        checkProject.setOrganization(organization);
        Example<Project> projectExample=Example.of(checkProject);
        List<Project> projectList=projectDao.findAll(projectExample);
        return projectList;
    }

    public Page<Project> findProjectByPage(int index,int pageSize){
        Pageable pageable= PageRequest.of(index-1,pageSize);
        Page<Project> projectPage=projectDao.findAll(pageable);
        return projectPage;
    }
}