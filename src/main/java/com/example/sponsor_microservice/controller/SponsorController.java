package com.example.sponsor_microservice.controller;

import com.example.sponsor_microservice.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class SponsorController {
    @Autowired
    private SponsorService sponsorService;

    //good
    @GetMapping("/v1.0/sponsor-microservice/projects/RPInfo")
    public Object disRPInfo(String size){
        return sponsorService.disRPInfo(size);
    }

    //good
    @GetMapping("/v1.0/sponsor-microservice/projects/page")
    public Object findProjectByPage(int index,int pageSize){
        return sponsorService.findProjectByPage(index,pageSize);
    }

    //good
    @GetMapping("/v1.0/sponsor-microservice/projects/projectAndNotice")
    public Object findProjectAndNotice(String id){
        return sponsorService.findProjectAndNotice(id);
    }

    //good
    @GetMapping("/v1.0/sponsor-microservice/feedback/all")
    public List findFeedBackInfoByPage(String index,String pageSize,String sponsorId){
        return sponsorService.findFeedBackInfoByPage(index,pageSize,sponsorId);
    }

    //good
    @PostMapping("/v1.0/sponsor-microservice/follow/add")
    public void addFollow(String followerId,String subjectId){
        sponsorService.addFollow(followerId,subjectId);
    }
}
