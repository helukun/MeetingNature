package com.example.sponsor_microservice.controller;

import com.example.sponsor_microservice.service.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@RestController
public class SponsorController {
    @Autowired
    private SponsorService sponsorService;

    //good
    @GetMapping("/v2.0/sponsor-microservice/projects/RPInfo")
    public Object disRPInfo(String size){
        return sponsorService.disRPInfo(size);
    }

    //good
    @GetMapping("/v2.0/sponsor-microservice/projects/page")
    public Object findProjectByPage(int index,int pageSize){
        return sponsorService.findProjectByPage(index,pageSize);
    }

    //good
    @GetMapping("/v2.0/sponsor-microservice/projects/projectAndNotice")
    public Object findProjectAndNotice(String id){
        return sponsorService.findProjectAndNotice(id);
    }

    //good
    @GetMapping("/v2.0/sponsor-microservice/feedback/all")
    public Object findFeedBackInfoByPage(String index,String pageSize,String sponsorId){
        return sponsorService.findFeedBackInfoBySPPlusPage(index,pageSize,sponsorId);
    }

    //good
    @PostMapping("/v2.0/sponsor-microservice/follow/add")
    public void addFollow(String followerId,String subjectId){
        sponsorService.addFollow(followerId,subjectId);
    }

    //good
    @DeleteMapping("/v2.0/sponsor-microservice/follow")
    public  void  removeFollow(String followerId,String subjectId){sponsorService.removeFollow(followerId,subjectId);}

    @GetMapping("/v2.0/sponsor-microservice/follow/users/id")
    public Object GetUserById(String id){
        return sponsorService.getUserById(id);
    }

    @PostMapping("/v2.0/sponsor-microservice/order")
    public int CreateOrder(String sponsorId, String subjectId, String amount, String SponsorshipPeriod){
        return sponsorService.CreateOrder(sponsorId,subjectId,amount,SponsorshipPeriod);
    }

    @PutMapping("/v2.0/sponsor-microservice/order")
    public int ChangeOrderStatue(String orderId){
        return sponsorService.ChangeOrderStatue(orderId);
    }

    @PostMapping("/v2.0/sponsor-microservice/sponsorship")
    public int CreateSponsorShip(String orderId, String days) {
        return sponsorService.CreateSponsorShip(orderId,days);
    }

    @PostMapping("/v2.0/sponsor-microservice/profile")
    public String AddProfile(String id, MultipartFile profile, String storagePath){
        return sponsorService.addProfile(id,profile,storagePath);
    }
    //good
    //new
    @GetMapping("/v2.0/sponsor-microservice/feedback/SPPlusPage")
    public Object findFeedBackInfoBySPPlusPage(String sponsorId,String index,String pageSize){
        return sponsorService.findFeedBackInfoBySPPlusPage(sponsorId,index,pageSize);
    }

    //good
    @GetMapping("/v2.0/sponsor-microservice/notice/followerId")
    public Object findNoticeInfoByPageByFollowerId(String index,String pageSize,String followerId){
        return sponsorService.findNoticeInfoBySPPlusPage(followerId,index,pageSize);
    }

    @GetMapping("/v2.0/sponsor-microservice/projects/followerId")
    public Object findProjectByPageByFollowerId(String index,String pageSize,String followerId){
        return sponsorService.findProjectInfoBySPPlusPage(followerId,index,pageSize);
    }
}
