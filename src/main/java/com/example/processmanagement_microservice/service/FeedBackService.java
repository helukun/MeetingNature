package com.example.processmanagement_microservice.service;


import com.example.processmanagement_microservice.dao.FeedBackDao;
import com.example.processmanagement_microservice.model.FeedBack;
import com.example.processmanagement_microservice.model.Order;
import com.example.processmanagement_microservice.dao.OrderDao;
import com.example.processmanagement_microservice.model.Sponsorship;
import com.netflix.discovery.converters.Auto;
import jakarta.servlet.http.HttpServletRequest;
import org.bouncycastle.pqc.crypto.newhope.NHOtherInfoGenerator;
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
import java.util.UUID;

/**
 * @author ：ZXM+LJC
 * @description：FeedBackService
 * @date ：2022-12-9 15:47
 * @version : 1.0
 */

@Service
public class FeedBackService {
    @Autowired
    private FeedBackDao feedBackDao;
    @Autowired
    private SponsorshipService sponsorshipService;
    @Autowired
    private OSSService ossService;

    public int isExist(String subjectId,String time){
        FeedBack checkFeedBack=new FeedBack();
        checkFeedBack.setSubjectId(subjectId)
                .setCreateTime(time);
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
                    .setCreateTime(time);
            Example<FeedBack> feedBackExample=Example.of(checkFeedBack);
            List<FeedBack> feedBackList=feedBackDao.findAll(feedBackExample);
            feedBack=feedBackList.get(0);
        }
        return feedBack;
    }


    /*
    @PostMapping("/upload")
    public void uploadPicture(String fileName, MultipartFile f, HttpServletRequest request) throws IOException {
        System.out.println(fileName);
        System.out.println(f.getOriginalFilename());
        System.out.println(f.getContentType());

        String path="D:\\IDEA_ultimate\\File";
        System.out.println(path);
        saveFile(f,path);
    }*/

    public void saveFile(MultipartFile f,String path,String NewNme) throws IOException {
        File dir=new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file=new File(path+NewNme);
        f.transferTo(file);
    }

    public String addPicCon(String subjectId,String createTime,MultipartFile picture,String storagePath){
        String message="";    //合法性
        FeedBack feedBack=this.findByPKNo(subjectId,createTime);
        if(feedBack==null){
            return message+"Failed!";
        }

        String res=ossService.uploadFile(picture,storagePath);
        List tmp=feedBack.getPathList();
        tmp.add("https://meeting-nature.oss-cn-shanghai.aliyuncs.com/"+res);
        feedBack.setPathList(tmp);
        feedBackDao.save(feedBack);

        return "https://meeting-nature.oss-cn-shanghai.aliyuncs.com/"+res;
    }

    public String addPicOnly(String subjectId,String createTime,MultipartFile picture,String storagePath){
        String message="";    //合法性
        FeedBack feedBack=this.findByPKNo(subjectId,createTime);
        if(feedBack==null){
            return message+"Failed!";
        }

        String res=ossService.uploadFile(picture,storagePath);
        return res;
    }

    public String addPicPathOnly(String subjectId,String createTime,String newPath){
        String message="";    //合法性
        FeedBack feedBack=this.findByPKNo(subjectId,createTime);
        if(feedBack==null){
            return message+"Failed!";
        }

        List tmp=feedBack.getPathList();
        tmp.add("https://meeting-nature.oss-cn-shanghai.aliyuncs.com/"+newPath);
        feedBack.setPathList(tmp);
        feedBackDao.save(feedBack);

        return "https://meeting-nature.oss-cn-shanghai.aliyuncs.com/"+newPath;
    }

    public String addPicture(String subjectId,String createTime,MultipartFile picture,HttpServletRequest request) throws IOException {
        String message="";    //合法性
        FeedBack feedBack=this.findByPKNo(subjectId,createTime);
        if(feedBack==null){
            return message+"Failed!";
        }

        String tmpPath=request.getServletContext().getRealPath("/File/");
        List<String> pathlist=feedBack.getPathList();
        /*以下代码段检测图片文件类型并使用uuid进行重命名*/
        String ofileName=picture.getOriginalFilename();
        String fileType=ofileName.substring(ofileName.lastIndexOf('.'),ofileName.length());
        String NewName= UUID.randomUUID()+fileType;

        saveFile(picture,tmpPath,NewName);
        pathlist.add(tmpPath+NewName);
        feedBack.setPathList(pathlist);
        feedBackDao.save(feedBack);
        message+="/File/"+NewName;
        return message;
    }

    public void addContent(String subjectId,String createTime,String content){
        FeedBack feedBack=this.findByPKNo(subjectId,createTime);
        feedBack.setContent(content);
        feedBackDao.save(feedBack);
    }

    public void changeStatus(String subjectId,String createTime,String status){
        FeedBack feedBack=this.findByPKNo(subjectId,createTime);
        feedBack.setStatus(status);
        feedBackDao.save(feedBack);
    }

    //创建新的feedback
    public void addFeedBack(String subjectId) throws IOException {
        int exist=this.isExist(subjectId,String.valueOf(LocalDateTime.now()));
        FeedBack feedBack=new FeedBack();
        feedBack.setId(this.setNextId())
                .setSubjectId(subjectId)
                .setCreateTime(String.valueOf(LocalDateTime.now()))
                .setStatus("incomplete")
                .setPathList(new ArrayList<>());
        feedBackDao.save(feedBack);
    }

    //删除feedback时自动删除所有文件
    public void deleteFeedBackByPK(String subjectId,String time){
        int exist=this.isExist(subjectId,time);
        if(exist==0){
            return;
        }
        FeedBack feedBack=this.findByPKNo(subjectId,time);
        List<String> pathlist=feedBack.getPathList();
        for(String e:pathlist){
            File myObj = new File(e);
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
        }
        feedBackDao.delete(feedBack);
    }

    public void deleteFBById(String id){
        FeedBack feedBack=null;
        FeedBack checkFeedBack=new FeedBack();
        checkFeedBack.setId(id);
        Example<FeedBack> feedBackExample=Example.of(checkFeedBack);
        List<FeedBack> feedBackList=feedBackDao.findAll(feedBackExample);
        if(feedBackList.size()!=0) {
            feedBack=feedBackList.get(0);
            List<String> pathlist=feedBack.getPathList();
            for(String e:pathlist){
                File myObj = new File(e);
                if (myObj.delete()) {
                    System.out.println("Deleted the file: " + myObj.getName());
                } else {
                    System.out.println("Failed to delete the file.");
                }
            }
            feedBackDao.delete(feedBack);
        }
    }

    //反馈所有内容
    public List<FeedBack> findFBBySB(String subjectId){
        FeedBack checkFB=new FeedBack();
        checkFB.setSubjectId(subjectId);
        Example<FeedBack> feedBackExample=Example.of(checkFB);
        List<FeedBack> list=feedBackDao.findAll(feedBackExample);
        return list;
    }

    //反馈所有内容
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

    public List findAllFeedBack(){
        List<FeedBack> allFeedBack =feedBackDao.findAll();
        return allFeedBack;
    }
}
