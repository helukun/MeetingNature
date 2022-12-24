package com.example.sponsor_microservice.controller;

import com.example.sponsor_microservice.service.SponsorService;
import com.github.kevinsawicki.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class SponsorController {
    @Autowired
    private SponsorService sponsorService;

    //随机返回size个green项目，如果size过大则返回全部green项目
    //good
    @GetMapping("/v2.0/sponsor-microservice/projects/RPInfo")
    public Object disRPInfo(String size){
        return sponsorService.disRPInfo(size);
    }

    //分页返回所有green项目
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

    //分页返回所有已赞助项目的反馈，但只返回green项目的反馈
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
    public String CreateOrder(String sponsorId, String subjectId, String amount, String SponsorshipPeriod){
        return sponsorService.CreateOrder(sponsorId,subjectId,amount,SponsorshipPeriod);
    }

    @PutMapping("/v2.0/sponsor-microservice/order")
    public int ChangeOrderStatue(String orderId){
        return sponsorService.ChangeOrderStatue(orderId);
    }

    @PostMapping("/v2.0/sponsor-microservice/sponsorship")
    public int CreateSponsorShip(String orderId, String days) {
        return sponsorService.CreateSponsorShip(orderId);
    }

    @PostMapping("/v2.0/sponsor-microservice/profile")
    public String AddProfile(String id, MultipartFile profile, String storagePath){
        return sponsorService.addProfile(id,profile,storagePath);
    }
    //good
    //分页返回赞助者已赞助项目，但是只返回green项目的反馈
    @GetMapping("/v2.0/sponsor-microservice/feedback/SPPlusPage")
    public Object findFeedBackInfoBySPPlusPage(String sponsorId,String index,String pageSize){
        return sponsorService.findFeedBackInfoBySPPlusPage(sponsorId,index,pageSize);
    }

    //good
    //分页返回关注者已关注项目公告，但只返回green项目的
    @GetMapping("/v2.0/sponsor-microservice/notice/followerId")
    public Object findNoticeInfoByPageByFollowerId(String index,String pageSize,String followerId){
        return sponsorService.findNoticeInfoBySPPlusPage(followerId,index,pageSize);
    }

    @GetMapping("/v2.0/sponsor-microservice/projects/followerId")
    public Object findProjectByPageByFollowerId(String index,String pageSize,String followerId){
        return sponsorService.findProjectInfoBySPPlusPage(followerId,index,pageSize);
    }

    @PostMapping("/v2.0/sponsor-microservice/complaints")
    public String addComplaint(String sponsorId,String subjectId,String content){
        return sponsorService.addComplaint(sponsorId,subjectId,content);
    }

    @GetMapping("/v2.0/sponsor-microservice/login")
    public ResponseEntity<Object> login(@RequestParam String name, @RequestParam String password) {
        return sponsorService.login(name,password);
    }
    @PostMapping(value = "/v2.0/sponsor-microservice/registerEmail")
    public ResponseEntity<Object> registerEmail(@RequestParam String email) {
        return sponsorService.registerEmail(email);
    }
    @PostMapping(value = "/v2.0/sponsor-microservice/register")
    public ResponseEntity<Object> register(@RequestParam String username,@RequestParam String password,
                                           @RequestParam String code,@RequestParam String email) {
        return sponsorService.register(username,password,code,email);
    }
    @PostMapping(value = "/v2.0/sponsor-microservice/recoverEmail")
    public ResponseEntity<Object> recoverEmail(@RequestParam String name) {
        return sponsorService.recoverEmail(name);
    }
    @PostMapping("/v2.0/sponsor-microservice/recover")
    public ResponseEntity<Object> recover(@RequestParam String name,@RequestParam String code,@RequestParam String newpassword) {
        return sponsorService.recover(name,code,newpassword);
    }
}
