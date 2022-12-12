package com.example.sponsored_microservice.controller;

import com.example.sponsored_microservice.service.SponsoredService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class SponsoredController {
    @Autowired
    private SponsoredService sponsoredService;

    //good
    @GetMapping("/v1.2/sponsored-microservice/projects/Org")
    public Object findProByOrg(String organization){
        return sponsoredService.findProByOrg(organization);
    }

    //good
    @GetMapping("/v1.2/sponsored-microservice/projects/OrgPlusPage")
    public Object findProByOrgPlusPage(String organization,int index,int pageSize){
        return sponsoredService.findProByOrgPlusPage(organization,index,pageSize);
    }

    //good
    @GetMapping("/v1.2/sponsored-microservice/Notice/SB")
    public Object findNotBySB(String subjectId) throws IOException{
        return sponsoredService.findNotBySB(subjectId);
    }

    //good
    @GetMapping("/v1.2/sponsored-microservice/Notice/SBPlusPage")
    public Object findNotBySBPlusPage(String subjectId,int index,int pageSize) throws IOException{
        return sponsoredService.findNotBySBPlusPage(subjectId,index,pageSize);
    }

    //good
    @PutMapping("/v1.2/sponsored-microservice/Project")
    public void changeProject(String id,String projectName,String organization,String describe,String status, String monthFee){
        sponsoredService.changeProject(id,projectName,organization,describe,status,monthFee);
    }

    //good
    @PostMapping("/v1.2/sponsored-microservice/FeedBack")
    public void addFeedBack(String subjectId){
        sponsoredService.addFeedBack(subjectId);
    }

    //good
    @PostMapping("/v1.2/sponsored-microservice/FeedBack/savePlusSubmit")
    public void savePlusSubmitFeedBack(String subjectId,String createTime,String content,String status){
        sponsoredService.savePlusSubmitFeedBack(subjectId,createTime,content,status);
    }

    //暂时使用中间层
    @PostMapping("/v1.2/sponsored-microservice/FeedBack/addPic")
    public String addPicToFB(String subjectId, String createTime, MultipartFile picture, HttpServletRequest request) throws IOException {
        return sponsoredService.addPicToFB(subjectId,createTime,picture,request);
    }

    //good
    @PostMapping("/v1.2/sponsored-microservice/Notice")
    public void addNotice(String subjectId){
        sponsoredService.addNotice(subjectId);
    }

    //good
    @PostMapping("/v1.2/sponsored-microservice/Notice/savePlusSubmit")
    public void savePlusSubmitNotice(String subjectId,String createTime,String content,String status){
        sponsoredService.savePlusSubmitNotice(subjectId,createTime,content,status);
    }

    //公告的添加图片暂时由中间层实现

    //good
    @GetMapping("/v1.2/sponsored-microservice/Notice/AllByOrg")
    public Object findAllNoticeByOrg(String organization,String index,String pageSize){
        return sponsoredService.findAllNoticeByOrg(organization,index,pageSize);
    }

    //good
    @GetMapping("/v1.2/sponsored-microservice/FeedBack/AllByOrg")
    public Object findAllFeedBackByOrg(String organization,String index,String pageSize){
        return  sponsoredService.findAllFeedBackByOrg(organization,index,pageSize);
    }

    //good
    @DeleteMapping("/v1.2/sponsored-microservice/FeedBack/Id")
    public void deleteFBById(String id){
        sponsoredService.deleteFBById(id);
    }

    //good
    @PostMapping("/v1.2/sponsored-microservice/Project")
    public void addProject(String projectName,String organization,String describe,String status, String monthFee){
        sponsoredService.addProject(projectName,organization,describe,status,monthFee);
    }

    //项目的图片增加暂时使用中间层接口
}
