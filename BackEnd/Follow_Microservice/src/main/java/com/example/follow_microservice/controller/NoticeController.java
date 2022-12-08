package com.example.follow_microservice.controller;

import com.example.follow_microservice.model.Notice;
import com.example.follow_microservice.service.NoticeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @PostMapping("/follow-microservice/notice/picture")
    public int addPicture(String subjectId,String createTime,MultipartFile picture) throws IOException {
        return noticeService.addPicture(subjectId,createTime,picture);
    }

    @PostMapping("/follow-microservice/notice/content")
    public void addContent(String subjectId,String createTime,String content){
        noticeService.addContent(subjectId,createTime,content);
    }

    @PostMapping("/follow-microservice/notice/status")
    public void changeStatus(String subjectId,String createTime,String status){
        noticeService.changeStatus(subjectId,createTime,status);
    }

    @PostMapping("/follow-microservice/notice")
    public void addNotice(String subjectId) throws IOException{
        noticeService.addNotice(subjectId);
    }

    @DeleteMapping ("/follow-microservice/notice")
    public void deleteNotice(String subjectId,String time) throws IOException{
        noticeService.deleteNoticeByPK(subjectId,time);
    }

    @GetMapping ("/follow-microservice/notice/SB")
    public List findNOTBySB(String subjectId) throws IOException{
        return noticeService.findNOTBySB(subjectId);
    }

    @GetMapping ("/follow-microservice/notice/US")
    public List findNOTByUS(String followerId) throws IOException{
        return noticeService.findNOTByUS(followerId);
    }
}
