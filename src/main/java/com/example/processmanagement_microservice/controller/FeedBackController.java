package com.example.processmanagement_microservice.controller;

import com.example.processmanagement_microservice.dao.OrderDao;
import com.example.processmanagement_microservice.model.FeedBack;
import com.example.processmanagement_microservice.model.Order;
import com.example.processmanagement_microservice.service.FeedBackService;
import com.example.processmanagement_microservice.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：FeedBackController
 * @date ：2022-12-17 17:56
 * @version : 2.0
 */

@CrossOrigin
@RestController
public class FeedBackController {
    @Autowired
    FeedBackService feedBackService;

    //good
    @PostMapping("/v2.0/processmanagement-microservice/feedback/pictures")
    public String addPicture(String subjectId, String createTime, MultipartFile picture, HttpServletRequest request) throws IOException {
        return feedBackService.addPicture(subjectId,createTime, picture,request);
        //Map map = request.getParameterMap();
        //Object o = (map.get("picture"));
        //String paravalue = ((String[])map.get("picture"))[0];
        //File file = new File(paravalue);
        //FileInputStream input = new FileInputStream(file);
        //MultipartFile multipartFile = new MockMultipartFile(file.getName(),input);
        //Map map = request.getParameterMap();
        //String picture1 = (String)map.get("picture");
        //MultipartFile multipartFile = (MultipartFile)map.get("picture");
        //MultipartFile multipartFile = new MockMultipartFile();
    }

    @PostMapping("/v2.0/processmanagement-microservice/feedback/picturesOnly")
    public String addPicOnly(String subjectId,String createTime,MultipartFile picture,String storagePath){
        return feedBackService.addPicOnly(subjectId,createTime,picture,"feedBackPic");
    }

    @PostMapping("/v2.0/processmanagement-microservice/feedback/picPathOnly")
    public String addPicPathOnly(String subjectId,String createTime,String newPath){
        return feedBackService.addPicPathOnly(subjectId,createTime,newPath);
    }

    //good
    @PostMapping("/v2.0/processmanagement-microservice/feedback/content")
    public void addContent(String subjectId,String createTime,String content){
        feedBackService.addContent(subjectId,createTime,content);
    }

    //good
    @PutMapping("/v2.0/processmanagement-microservice/feedback/status")
    public void changeStatus(String subjectId,String createTime,String status){
        feedBackService.changeStatus(subjectId,createTime,status);
    }

    //good
    @PostMapping("/v2.0/processmanagement-microservice/feedback")
    public void addFeedBack(String subjectId) throws IOException{
        feedBackService.addFeedBack(subjectId);
    }

    //good
    @DeleteMapping ("/v2.0/processmanagement-microservice/feedback")
    public void deleteFeedBack(String subjectId,String time) throws IOException{
        feedBackService.deleteFeedBackByPK(subjectId,time);
    }

    //good
    @DeleteMapping ("/v2.0/processmanagement-microservice/feedback/Id")
    public void deleteFeedBackById(String id) throws IOException{
        feedBackService.deleteFBById(id);
    }

    //good
    @GetMapping ("/v2.0/processmanagement-microservice/feedback/subjectId")
    public List findFBBySB(String subjectId) throws IOException{
        return feedBackService.findFBBySB(subjectId);
    }

    //good
    @GetMapping ("/v2.0/processmanagement-microservice/feedback/sponsorId")
    public List findFBBySP(String sponsorId) throws IOException{
        return feedBackService.findFBBySP(sponsorId);
    }

    //good
    @GetMapping ("/v2.0/processmanagement-microservice/feedback/all")
    public List findAllFB() throws IOException{
        return feedBackService.findAllFeedBack();
    }

    //new
    //good
    @GetMapping ("/v2.0/processmanagement-microservice/feedback/sponsorIdPlusPage")
    public List findFeedBackInfoBySPPlusPage(String sponsorId,String index,String pageSize){
        return feedBackService.findFeedBackInfoBySPPlusPage(sponsorId,index,pageSize);
    }
}
