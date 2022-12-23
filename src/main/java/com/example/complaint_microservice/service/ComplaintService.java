package com.example.complaint_microservice.service;

import com.alibaba.fastjson.JSON;
import com.example.complaint_microservice.model.Complaint;
import com.example.complaint_microservice.dao.ComplaintDao;
import com.github.kevinsawicki.http.HttpRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
public class ComplaintService {
    @Autowired
    private ComplaintDao complaintDao;

    private String ProjectMicroserviceIp="http://121.5.128.97:9006";
    private String FollowMicroserviceIp="http://121.5.128.97:9008";
    private String ProcessManagementMicroserviceIp="http://121.5.128.97:9005";

    public String setNextId(){
        List<Complaint> complaintList=complaintDao.findAll();
        int curMaxId=0;
        for(Complaint c:complaintList){
            curMaxId=Math.max(curMaxId,Integer.parseInt(c.getId()));
        }
        int result=curMaxId+1;
        return result+"";
    }


    public int isExist(String id){
        Complaint check=new Complaint();
        check.setId(id);
        Example<Complaint> complaintExample=Example.of(check);
        long count = complaintDao.count(complaintExample);

        int result=0;
        if((int)count!=0){
            result=1;
        }
        return result;
    }

    public String addComplaint(Complaint complaint){
        complaint.setId(this.setNextId());
        complaint.setStatus("yellow");
        if(complaint.getSponsorId()==null){
            complaint.setSponsorId("");
        }
        if(complaint.getSubjectId()==null){
            complaint.setSubjectId("");
        }
        if(complaint.getContent()==null){
            complaint.setContent("");
        }
        int exist=this.isExist(complaint.getId());
        if(exist!=1){
            complaintDao.save(complaint);
            System.out.println("添加成功！");
            return complaint.getId();
        }
        else{
            System.out.println("该投诉已存在，无法再次添加！");
            return "false";
        }
    }

    public Complaint findComplaintById(String id){
        Complaint complaint=null;

        Complaint checkComplaint=new Complaint();
        checkComplaint.setId(id);
        Example<Complaint> complaintExample=Example.of(checkComplaint);
        long count = complaintDao.count(complaintExample);
        List<Complaint> complaintList=complaintDao.findAll(complaintExample);

        if((int)count!=0){
            complaint = complaintList.get(0);
            System.out.println("找到用户！");
        }
        else{
            System.out.println("该用户不存在");
        }
        System.out.println(count);
        return complaint;
    }

    public void deleteComplaint(String id){
        int exist=this.isExist(id);
        if(exist==0){
            return;
        }
        Complaint complaint=this.findComplaintById(id);
        complaintDao.delete(complaint);
    }

    public List findAllComplaint(){
        List<Complaint> complaintList = complaintDao.findAll();
        return complaintList;
    }

    public Object findProByProId(String subjectId){
        String resp= HttpRequest.get(ProjectMicroserviceIp+"/v2.0/project-microservice/projects/Id?id="+
                subjectId).body();
        return JSON.parse(resp);
    }

    public Object findFBByProId(String subjectId){
        String resp= HttpRequest.get(ProcessManagementMicroserviceIp+"/v2.0/processmanagement-microservice/feedback/subjectId?subjectId="+
                subjectId).body();
        return JSON.parse(resp);
    }

    public Object findNotByProId(String subjectId){
        String resp= HttpRequest.get(FollowMicroserviceIp+"/v2.0/follow-microservice/notice/subjectId?subjectId="+
                subjectId).body();
        return JSON.parse(resp);
    }

    public void changeStatus(String id,String status){
        Complaint complaint=this.findComplaintById(id);
        if(complaint!=null){
            complaint.setStatus(status);
            complaintDao.save(complaint);
        }
    }
}
