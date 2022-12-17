package com.example.follow_microservice.controller;

import com.example.follow_microservice.model.Notice;
import com.example.follow_microservice.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author ：ZXM+LJC
 * @description：NoticeController
 * @date ：2022-12-17 13:48
 * @version : 2.0
 */

@CrossOrigin
@RestController
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @PostMapping("/v2.0/follow-microservice/notice/pictures")
    public String addPicture(String subjectId, String createTime, MultipartFile picture, HttpServletRequest request) throws IOException {
        return noticeService.addPicture(subjectId,createTime,picture,request);
    }

    @PostMapping("/v2.0/follow-microservice/notice/content")
    public void addContent(String subjectId,String createTime,String content){
        noticeService.addContent(subjectId,createTime,content);
    }

    @PostMapping("/v2.0/follow-microservice/notice/picPathOnly")
    public String addPicPathOnly(String subjectId,String createTime,String newPath){
        return noticeService.addPicPathOnly(subjectId,createTime,newPath);
    }

    @PutMapping("/v2.0/follow-microservice/notice/status")
    public void changeStatus(String subjectId,String createTime,String status){
        noticeService.changeStatus(subjectId,createTime,status);
    }

    @PostMapping("/v2.0/follow-microservice/notice")
    public void addNotice(String subjectId) throws IOException{
        noticeService.addNotice(subjectId);
    }

    @DeleteMapping ("/v2.0/follow-microservice/notice")
    public void deleteNotice(String subjectId,String time) throws IOException{
        noticeService.deleteNoticeByPK(subjectId,time);
    }

    @GetMapping ("/v2.0/follow-microservice/notice/subjectId")
    public List findNOTBySB(String subjectId) throws IOException{
        return noticeService.findNOTBySB(subjectId);
    }

    @GetMapping ("/v2.0/follow-microservice/notice/followerId")
    public List findNOTByUS(String followerId) throws IOException{
        return noticeService.findNOTByUS(followerId);
    }

    //good
    @GetMapping ("/v2.0/follow-microservice/notice/SBPlusPage")
    public Page findNOTBySBPlusPage(String subjectId, int index, int pageSize) throws IOException {
        return noticeService.findNOTBySBPlusPage(subjectId,index,pageSize);
    }
}