package com.example.follow_microservice.service;

import com.example.follow_microservice.dao.NoticeDao;
import com.example.follow_microservice.model.Notice;
import com.example.follow_microservice.model.Follow;
import com.netflix.discovery.converters.Auto;
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
import java.util.UUID;

@Service
public class NoticeService {
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private FollowService followService;

    public int isExist(String subjectId,String time){
        Notice checkNotice=new Notice();
        checkNotice.setSubjectId(subjectId)
                .setCreateTime(time);
        Example<Notice> noticeExample=Example.of(checkNotice);
        long count=noticeDao.count(noticeExample);

        int result=0;
        if((int)count!=0){
            result=1;
        }
        return result;
    }

    public String setNextId(){
        List<Notice> noticeList=noticeDao.findAll();
        int curMaxId=0;
        for(Notice n:noticeList){
            curMaxId=Math.max(curMaxId,Integer.parseInt(n.getId()));
        }
        int result=curMaxId+1;
        return result+"";
    }

    public Notice findByPKNo(String subjectId,String time){
        Notice notice=null;
        int exist = this.isExist(subjectId,time);
        if(exist!=0){
            Notice checkNotice=new Notice();
            checkNotice.setSubjectId(subjectId)
                    .setCreateTime(time);
            Example<Notice> noticeExample=Example.of(checkNotice);
            List<Notice> noticeList=noticeDao.findAll(noticeExample);
            notice=noticeList.get(0);
        }
        return notice;
    }

    public void saveFile(MultipartFile f,String path,String NewNme) throws IOException {
        File dir=new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file=new File(path+NewNme);
        f.transferTo(file);
    }

    public String addPicture(String subjectId,String createTime,MultipartFile picture,HttpServletRequest request) throws IOException {
        String message="";    //合法性
        Notice notice=this.findByPKNo(subjectId,createTime);
        if(notice==null){
            return message+"Failed!";
        }

        String tmpPath=request.getServletContext().getRealPath("/File/");
        List<String> pathlist=notice.getPathList();
        /*以下代码段检测图片文件类型并使用uuid进行重命名*/
        String ofileName=picture.getOriginalFilename();
        String fileType=ofileName.substring(ofileName.lastIndexOf('.'),ofileName.length());
        String NewName= UUID.randomUUID()+fileType;

        saveFile(picture,tmpPath,NewName);
        pathlist.add(tmpPath+NewName);
        notice.setPathList(pathlist);
        noticeDao.save(notice);
        message+="/File/"+NewName;
        return message;
    }

    public void addContent(String subjectId,String createTime,String content){
        Notice notice=this.findByPKNo(subjectId,createTime);
        notice.setContent(content);
        noticeDao.save(notice);
    }

    public void changeStatus(String subjectId,String createTime,String status){
        Notice notice=this.findByPKNo(subjectId,createTime);
        notice.setStatus(status);
        noticeDao.save(notice);
    }

    public void addNotice(String subjectId) throws IOException {
        int exist=this.isExist(subjectId,String.valueOf(LocalDateTime.now()));
        Notice notice=new Notice();
        notice.setId(this.setNextId())
                .setSubjectId(subjectId)
                .setCreateTime(String.valueOf(LocalDateTime.now()))
                .setStatus("incomplete")
                .setPathList(new ArrayList<>());
        noticeDao.save(notice);
    }

    public void deleteNoticeByPK(String subjectId,String time){
        int exist=this.isExist(subjectId,time);
        if(exist==0){
            return;
        }
        Notice notice=this.findByPKNo(subjectId,time);
        List<String> pathlist=notice.getPathList();
        for(String e:pathlist){
            File myObj = new File(e);
            if (myObj.delete()) {
                System.out.println("Deleted the file: " + myObj.getName());
            } else {
                System.out.println("Failed to delete the file.");
            }
        }
        noticeDao.delete(notice);
    }

    public List<Notice> findNOTBySB(String subjectId){
        Notice checkNOT=new Notice();
        checkNOT.setSubjectId(subjectId);
        Example<Notice> noticeExample=Example.of(checkNOT);
        List<Notice> list=noticeDao.findAll(noticeExample);
        return list;
    }

    public List<Notice> findNOTByUS(String followerId){
        List<Follow> followList=followService.findAllFollowByFOid(followerId);
        List<String> stringList = new ArrayList<>();
        for(Follow f:followList){
            stringList.add(f.getSubjectId());
        }
        List<Notice> finalList=new ArrayList<>();
        for(String sid:stringList){
            List<Notice> tmp=this.findNOTBySB(sid);
            for(Notice n:tmp){
                finalList.add(n);
            }
        }
        return finalList;
    }
}
