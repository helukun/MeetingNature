package com.example.processmanagement_microservice.service;


import com.example.processmanagement_microservice.dao.FeedBackDao;
import com.example.processmanagement_microservice.model.FeedBack;
import com.example.processmanagement_microservice.model.Order;
import com.example.processmanagement_microservice.dao.OrderDao;
import com.example.processmanagement_microservice.model.Sponsorship;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeedBackService {
    @Autowired
    private FeedBackDao feedBackDao;
    @Autowired
    private SponsorshipService sponsorshipService;

    public int isExist(String subjectId,String time){
        FeedBack checkFeedBack=new FeedBack();
        checkFeedBack.setSubjectId(subjectId)
                .setTime(time);
        Example<FeedBack> feedBackExample=Example.of(checkFeedBack);
        long count=feedBackDao.count(feedBackExample);

        int result=0;
        if((int)count!=0){
            result=1;
        }
        return result;
    }

    public String setNextId(){
        List<FeedBack> feedBackList=feedBackDao.findAll();
        int curMaxId=0;
        for(FeedBack f:feedBackList){
            curMaxId=Math.max(curMaxId,Integer.parseInt(f.getId()));
        }
        int result=curMaxId+1;
        return result+"";
    }

    public FeedBack findByPKNo(String subjectId,String time){
        FeedBack feedBack=null;
        int exist = this.isExist(subjectId,time);
        if(exist!=0){
            FeedBack checkFeedBack=new FeedBack();
            checkFeedBack.setSubjectId(subjectId)
                    .setTime(time);
            Example<FeedBack> feedBackExample=Example.of(checkFeedBack);
            List<FeedBack> feedBackList=feedBackDao.findAll(feedBackExample);
            feedBack=feedBackList.get(0);
        }
        return feedBack;
    }


    @PostMapping("/upload")
    public void upload(String fileName, MultipartFile f, HttpServletRequest request) throws IOException {
        System.out.println(fileName);
        System.out.println(f.getOriginalFilename());
        System.out.println(f.getContentType());

        String path="D:\\IDEA_ultimate\\File";
        System.out.println(path);
        saveFile(f,path);
    }

    public void saveFile(MultipartFile f,String path) throws IOException {
        File dir=new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file=new File(path);
        f.transferTo(file);
    }

    public void addFeedBack(MultipartFile f,String subjectId) throws IOException {
        int exist=this.isExist(subjectId,String.valueOf(LocalDateTime.now()));
        FeedBack feedBack=new FeedBack();
        feedBack.setId(this.setNextId())
                .setSubjectId(subjectId)
                .setTime(String.valueOf(LocalDateTime.now()))
                .setPath("D:\\IDEA_ultimate\\File\\"+f.getOriginalFilename());
        saveFile(f,feedBack.getPath());
        feedBackDao.save(feedBack);
    }

    public void deleteByPK(String subjectId,String time){
        int exist=this.isExist(subjectId,time);
        if(exist==0){
            return;
        }
        FeedBack feedBack=this.findByPKNo(subjectId,time);
        File myObj = new File(feedBack.getPath());
        if (myObj.delete()) {
            System.out.println("Deleted the file: " + myObj.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
        feedBackDao.delete(feedBack);
    }

    public List<FeedBack> findFBBySB(String subjectId){
        FeedBack checkFB=new FeedBack();
        checkFB.setSubjectId(subjectId);
        Example<FeedBack> feedBackExample=Example.of(checkFB);
        List<FeedBack> list=feedBackDao.findAll(feedBackExample);
        return list;
    }

    public List<FeedBack> findFBBySP(String sponsorId){
        List<Sponsorship> sponsorshipList=sponsorshipService.findAllSponsorshipBySPid(sponsorId);
        List<String> stringList = new ArrayList<>();
        for(Sponsorship s:sponsorshipList){
            stringList.add(s.getSubjectId());
        }
        List<FeedBack> finalList=new ArrayList<>();
        for(String sid:stringList){
            List<FeedBack> tmp=this.findFBBySB(sid);
            for(FeedBack f:tmp){
                finalList.add(f);
            }
        }
        return finalList;
    }
}
