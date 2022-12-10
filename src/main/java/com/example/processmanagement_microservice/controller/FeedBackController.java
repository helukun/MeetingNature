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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：FeedBackController
 * @date ：2022-12-9 15:43
 * @version : 1.1
 */

@RestController
public class FeedBackController {
    @Autowired
    FeedBackService feedBackService;

    //good
    @PostMapping("/v1.1/processmanagement-microservice/feedback/pictures")
    public String addPicture(String subjectId, String createTime, MultipartFile picture, HttpServletRequest request) throws IOException {
        return feedBackService.addPicture(subjectId,createTime,picture,request);
    }

    //good
    @PostMapping("/v1.1/processmanagement-microservice/feedback/content")
    public void addContent(String subjectId,String createTime,String content){
        feedBackService.addContent(subjectId,createTime,content);
    }

    //good
    @PostMapping("/v1.1/processmanagement-microservice/feedback/status")
    public void changeStatus(String subjectId,String createTime,String status){
        feedBackService.changeStatus(subjectId,createTime,status);
    }

    //good
    @PostMapping("/v1.1/processmanagement-microservice/feedback")
    public void addFeedBack(String subjectId) throws IOException{
        feedBackService.addFeedBack(subjectId);
    }

    //good
    @DeleteMapping ("/v1.1/processmanagement-microservice/feedback")
    public void deleteFeedBack(String subjectId,String time) throws IOException{
        feedBackService.deleteFeedBackByPK(subjectId,time);
    }

    //good
    @GetMapping ("/v1.1/processmanagement-microservice/feedback/subjectId")
    public List findFBBySB(String subjectId) throws IOException{
        return feedBackService.findFBBySB(subjectId);
    }

    //good
    @GetMapping ("/v1.1/processmanagement-microservice/feedback/sponsorId")
    public List findFBBySP(String sponsorId) throws IOException{
        return feedBackService.findFBBySP(sponsorId);
    }
}
