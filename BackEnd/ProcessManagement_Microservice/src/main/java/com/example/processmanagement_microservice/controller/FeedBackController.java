package com.example.processmanagement_microservice.controller;


import com.example.processmanagement_microservice.dao.OrderDao;
import com.example.processmanagement_microservice.model.FeedBack;
import com.example.processmanagement_microservice.model.Order;
import com.example.processmanagement_microservice.service.FeedBackService;
import com.example.processmanagement_microservice.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class FeedBackController {
    @Autowired
    FeedBackService feedBackService;

    //good
    @PostMapping("/processmanagement-microservice/processmanagement/feedback")
    public void addFeedBack(MultipartFile f, String subjectId) throws IOException{
        feedBackService.addFeedBack(f,subjectId);
    }

    //good
    @DeleteMapping ("/processmanagement-microservice/processmanagement/feedback")
    public void deleteFeedBack(String subjectId,String time) throws IOException{
        feedBackService.deleteByPK(subjectId,time);
    }

    //good
    @GetMapping ("/processmanagement-microservice/processmanagement/feedback/SB")
    public List findFBBySB(String subjectId) throws IOException{
        return feedBackService.findFBBySB(subjectId);
    }

    //good
    @GetMapping ("/processmanagement-microservice/processmanagement/feedback/SP")
    public List findFBBySP(String sponsorId) throws IOException{
        return feedBackService.findFBBySP(sponsorId);
    }
}
