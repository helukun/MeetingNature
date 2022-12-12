package com.example.project_microservice.service;

import com.alibaba.fastjson.JSON;
import com.example.project_microservice.model.Project;
import com.example.project_microservice.dao.ProjectDao;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.json.JsonObject;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

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
        project.setStatus(project.getStatus());
        project.setFollowerList(new ArrayList<>());
        project.setPicPaths(new ArrayList<>());
        if(project.getProjectName()==null){
            project.setProjectName("");
        }
        if(project.getOrganization()==null){
            project.setOrganization("");
        }
        if(project.getDescribe()==null){
            project.setDescribe("");
        }
        if(project.getMonthFee()==null){
            project.setMonthFee("");
        }
        int exist=this.isExist(project.getId());
        if(exist!=1){
            projectDao.save(project);
            System.out.println("添加成功！");
        }
        else{
            System.out.println("该项目已存在，无法再次添加！");
        }
    }

    public void changeProjectStatus(String id,String status){
        Project project=this.findProjectById(id);
        project.setStatus(status);
        projectDao.save(project);
    }


    public void saveFile(MultipartFile f, String path, String NewNme) throws IOException {
        File dir=new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file=new File(path+NewNme);
        f.transferTo(file);
    }

    public String addPicture(String id, MultipartFile picture, HttpServletRequest request) throws IOException {
        String message="";    //合法性
        Project project=this.findProjectById(id);
        if(project==null){
            return message+"Failed!";
        }

        String tmpPath=request.getServletContext().getRealPath("/File/");
        List<String> pathlist=project.getPicPaths();
        /*以下代码段检测图片文件类型并使用uuid进行重命名*/
        String fileName=picture.getOriginalFilename();
        String fileType=fileName.substring(fileName.lastIndexOf('.'),fileName.length());
        String NewName= UUID.randomUUID()+fileType;

        saveFile(picture,tmpPath,NewName);
        pathlist.add(tmpPath+NewName);
        project.setPicPaths(pathlist);
        projectDao.save(project);
        message+="/File/"+NewName;
        return message;
    }


    public void deleteProject(String id){
        int exist=this.isExist(id);
        if(exist==0){
            return;
        }
        Project project=this.findProjectById(id);
        List<String> pathList=project.getPicPaths();
        for(String e:pathList){
            File myObj = new File(e);
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
        }
        projectDao.delete(project);
    }


    public void deletePicByProId(String id,String picPath){
        Project project=this.findProjectById(id);
        if(project==null){
            return;
        }
        //获取到picPath
        List<String> pathList=project.getPicPaths();
        //删除磁盘文件
        for(String s:pathList){
            if(s.equals(picPath)){
                File myObj = new File(s);
                if (myObj.delete()) {
                    System.out.println("Deleted the file: " + myObj.getName());
                } else {
                    System.out.println("Failed to delete the file.");
                }
            }
        }
        //删除图片路径List中的记录
        Iterator<String> iterator = pathList.iterator();
        while(iterator.hasNext()) {
            String curPath = iterator.next();
            if (curPath.equals(picPath)) {
                iterator.remove();
            }
        }
        //保存修改
        project.setPicPaths(pathList);
        projectDao.save(project);
    }


    public int changeProjectInfo(Project newProject){
        int exist=this.isExist(newProject.getId());

        if(exist==0){
            System.out.println("该项目不存在");
            return 0;
        }

        int legal=1;
        if((newProject.getProjectName()!=null&&newProject.getProjectName().length()>15)||
                (newProject.getProjectName()!=null&&newProject.getProjectName().length()==0)||
                (newProject.getProjectName()!=null&&newProject.getProjectName().contains(" "))||
                (newProject.getDescribe()!=null&&newProject.getDescribe().length()>200)
        ){
            legal=0;
        }

        if(legal==1){
            Project oldProject=this.findProjectById(newProject.getId());

            if(newProject.getProjectName()!=null){
                oldProject.setProjectName(newProject.getProjectName());
            }
            if(newProject.getOrganization()!=null){
                oldProject.setOrganization(newProject.getOrganization());
            }
            if(newProject.getDescribe()!=null){
                oldProject.setDescribe(newProject.getDescribe());
            }
            if(newProject.getStatus()!=null){
                oldProject.setStatus(newProject.getStatus());
            }
            if(newProject.getMonthFee()!=null){
                oldProject.setMonthFee(newProject.getMonthFee());
            }

            projectDao.save(oldProject);

            System.out.println("修改成功！");
        }
        else{
            System.out.println("修改失败！");
        }
        return legal;
    }


    public List findAllProject(){
        List<Project> projectList = projectDao.findAll();
        return projectList;
    }

    public List findAllProjectInfo(){
        List<Project> proList=this.findAllProject();
        int size=proList.size();
        List res=new ArrayList<>();
        for(int i=0;i<size;i++){
            Map map=new HashMap();
            map.put("id",proList.get(i).getId());
            map.put("projectName",proList.get(i).getProjectName());
            map.put("organization",proList.get(i).getOrganization());
            map.put("describe",proList.get(i).getDescribe());
            map.put("picPaths",proList.get(i).getPicPaths());
            String param= JSON.toJSONString(map);
            res.add(JSON.parse(param));
        }
        return res;
    }

    public Project findProjectById(String id){
        Project project=null;

        Project checkProject3=new Project();
        checkProject3.setId(id);
        Example<Project> projectExample=Example.of(checkProject3);
        long count2 = projectDao.count(projectExample);
        List<Project> projectList=projectDao.findAll(projectExample);

        if((int)count2!=0){
            project = projectList.get(0);
            System.out.println("找到用户！");
        }
        else{
            System.out.println("该用户不存在");
        }
        System.out.println(count2);
        return project;
    }

    public List<String> getFollowList(String id){
        Project project=this.findProjectById(id);
        return project.getFollowerList();
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

    public List findProjectByOrg(String organization){
        Project checkProject=new Project();
        checkProject.setOrganization(organization);
        Example<Project> projectExample=Example.of(checkProject);
        List<Project> projectList=projectDao.findAll(projectExample);
        return projectList;
    }

    public List findProIdByOrg(String organization){
        Project checkProject=new Project();
        checkProject.setOrganization(organization);
        Example<Project> projectExample=Example.of(checkProject);
        List<Project> projectList=projectDao.findAll(projectExample);
        List res=new ArrayList<>();
        for(Project p:projectList){
            res.add(p.getId());
        }
        return  res;
    }

    public Page findProjectByPage(int index,int pageSize){
        Pageable pageable= PageRequest.of(index-1,pageSize);
        Page<Project> projectPage=projectDao.findAll(pageable);
        return projectPage;
    }

    public Page findProjectByOrgPlusPage(String organization,int index,int pageSize){
        Project checkProject=new Project();
        checkProject.setOrganization(organization);
        Example<Project> projectExample=Example.of(checkProject);

        Pageable pageable= PageRequest.of(index-1,pageSize);
        Page<Project> projectPage=projectDao.findAll(projectExample,pageable);

        return projectPage;
    }

    public List displayRPInfo(String n){
        int mysize= Integer.parseInt(n);
        Random random = new Random();
        List<Project> proList=this.findAllProject();
        int size=proList.size();
        int[] visit=new int[size];
        for(int i=0;i<size;i++){
            visit[i]=0;
        }
        List<Project> tmp=new ArrayList<>();
        for(int i=0;i<mysize;){
            int rd=random.nextInt(size);
            if(visit[rd]==0){
                tmp.add(proList.get(rd));
                visit[rd]=1;
                i++;
            }
        }

        List res=new ArrayList<>();
        for(int i=0;i<mysize;i++){
            Map map=new HashMap();
            map.put("id",tmp.get(i).getId());
            map.put("projectName",tmp.get(i).getProjectName());
            map.put("organization",tmp.get(i).getOrganization());
            map.put("describe",tmp.get(i).getDescribe());
            map.put("picPaths",tmp.get(i).getPicPaths());
            String param= JSON.toJSONString(map);
            res.add(JSON.parse(param));
        }

        return res;
    }
}