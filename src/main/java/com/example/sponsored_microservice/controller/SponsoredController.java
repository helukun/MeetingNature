package com.example.sponsored_microservice.controller;

import com.example.sponsored_microservice.service.SponsoredService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class SponsoredController {
    @Autowired
    private SponsoredService sponsoredService;

    //good
    @PostMapping("/v2.0/sponsored-microservice/FeedBack/addPicCon")
    public String addPicToFBcon(String subjectId, String createTime,MultipartFile picture){
        return sponsoredService.addPicToFBcon(subjectId,createTime,picture,"feedBackPic");
    }

    //good
    @PostMapping("/v2.0/sponsored-microservice/Project/addPicCon")
    public String addPicToProCon(String id,MultipartFile picture){
        return sponsoredService.addPicToProCon(id,picture,"ProjectPic");
    }

    //good
    @PostMapping("/v2.0/sponsored-microservice/Notice/addPicCon")
    public String addPicToNotCon(String subjectId,String createTime,MultipartFile picture){
        return sponsoredService.addPicToNotCon(subjectId,createTime,picture,"NoticePic");
    }

    //good
    @GetMapping("/v2.0/sponsored-microservice/projects/Org")
    public Object findProByOrg(String organization){
        return sponsoredService.findProByOrg(organization);
    }

    //good
    @GetMapping("/v2.0/sponsored-microservice/projects/OrgPlusPage")
    public Object findProByOrgPlusPage(String organization,int index,int pageSize){
        return sponsoredService.findProByOrgPlusPage(organization,index,pageSize);
    }

    //good
    @GetMapping("/v2.0/sponsored-microservice/Notice/SB")
    public int NumOffindNotBySB(String subjectId) throws IOException{
        return sponsoredService.NumOffindNotBySB(subjectId);
    }

    //good
    @GetMapping("/v2.0/sponsored-microservice/Notice/SBPlusPage")
    public Object findNotBySBPlusPage(String subjectId,int index,int pageSize) throws IOException{
        return sponsoredService.findNotBySBPlusPage(subjectId,index,pageSize);
    }

    //good
    @PutMapping("/v2.0/sponsored-microservice/Project")
    public void changeProject(String id,String projectName,String organization,String describe,String status, String monthFee){
        sponsoredService.changeProject(id,projectName,organization,describe,status,monthFee);
    }

    //good
    @PostMapping("/v2.0/sponsored-microservice/FeedBack")
    public String addFeedBack(String subjectId){
        return sponsoredService.addFeedBack(subjectId);
    }

    //good
    @PostMapping("/v2.0/sponsored-microservice/FeedBack/savePlusSubmit")
    public void savePlusSubmitFeedBack(String subjectId,String createTime,String content,String status){
        sponsoredService.savePlusSubmitFeedBack(subjectId,createTime,content,status);
    }

    //good
    @PostMapping("/v2.0/sponsored-microservice/Notice")
    public String addNotice(String subjectId){
        return sponsoredService.addNotice(subjectId);
    }

    //good
    @PostMapping("/v2.0/sponsored-microservice/Notice/savePlusSubmit")
    public void savePlusSubmitNotice(String subjectId,String createTime,String content,String status){
        sponsoredService.savePlusSubmitNotice(subjectId,createTime,content,status);
    }


    //good
    @GetMapping("/v2.0/sponsored-microservice/Notice/AllByOrg")
    public Object findAllNoticeByOrg(String organization,String index,String pageSize){
        return sponsoredService.findAllNoticeByOrg(organization,index,pageSize);
    }

    //good
    @GetMapping("/v2.0/sponsored-microservice/FeedBack/AllByOrg")
    public Object findAllFeedBackByOrg(String organization,String index,String pageSize){
        return  sponsoredService.findAllFeedBackByOrg(organization,index,pageSize);
    }

    //good
    @DeleteMapping("/v2.0/sponsored-microservice/FeedBack/Id")
    public void deleteFBById(String id){
        sponsoredService.deleteFBById(id);
    }

    //good
    @DeleteMapping("/v2.0/sponsored-microservice/FeedBack")
    public void deleteFBByPK(String subjectId,String time){
        sponsoredService.deleteFBByPK(subjectId,time);
    }

    @DeleteMapping("/v2.0/sponsored-microservice/Project/Pic")
    public void deletePicOfPro(String id,String picPath){
        sponsoredService.deletePicOfPro(id,picPath);
    }

    //good
    @PostMapping("/v2.0/sponsored-microservice/Project")
    public String addProject(String projectName,String organization,String describe,String status, String monthFee){
        return sponsoredService.addProject(projectName,organization,describe,status,monthFee);
    }

    @PostMapping("/v2.0/sponsored-microservice/Notice/sim")
    public String addNoticeSim(String content,String subjectId){
        return sponsoredService.addNoticeSim(content,subjectId);
    }

    @GetMapping("/v2.0/sponsored-microservice/FeedBack/AllByOrg/num")
    public Object findNumOfFeedBackByOrg(String organization){
        return sponsoredService.findNumOfFeedBackByOrg(organization);
    }
    @GetMapping("/v2.0/sponsored-microservice/login")
    public ResponseEntity<Object> login(@RequestParam String name, @RequestParam String password) {
        return sponsoredService.login(name,password);
    }
    @PostMapping(value = "/v2.0/sponsored-microservice/recoverEmail")
    public ResponseEntity<Object> recoverEmail(@RequestParam String name) {
        return sponsoredService.recoverEmail(name);
    }
    @PostMapping("/v2.0/sponsored-microservice/recover")
    public ResponseEntity<Object> recover(@RequestParam String name,@RequestParam String code,@RequestParam String newpassword) {
        return sponsoredService.recover(name,code,newpassword);
    }
}
