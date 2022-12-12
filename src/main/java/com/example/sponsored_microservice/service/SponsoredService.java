package com.example.sponsored_microservice.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.kevinsawicki.http.HttpRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.hc.client5.http.entity.mime.FileBody;
import org.apache.tomcat.util.json.JSONParser;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.binary.*;;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SponsoredService {
    private String ProjectMicroserviceIp="http://localhost:9006";
    private String FollowMicroserviceIp="http://localhost:9008";
    private String ProcessManagementMicroserviceIp="http://localhost:9005";

    public Object findProByOrg(String organization){
        String resp=HttpRequest.get(ProjectMicroserviceIp+"/v1.1/project-microservice/projects/organization?organization="+
                organization).body();
        return JSON.parse(resp);
    }

    public Object findProByOrgPlusPage(String organization,int index,int pageSize){
        String resp=HttpRequest.get(ProjectMicroserviceIp+"/v1.1/project-microservice/projects/organizationPlusPage?organization="+
                organization+"&index="
                +index+"&pageSize="
                +pageSize).body();
        return JSON.parse(resp);
    }

    public Object findNotBySB(String subjectId) throws IOException {
        String resp=HttpRequest.get(FollowMicroserviceIp+"/v1.1/follow-microservice/notice/subjectId?subjectId="+
                subjectId).body();
        return JSON.parse(resp);
    }

    public Object findNotBySBPlusPage(String subjectId,int index,int pageSize) throws IOException {
        String resp=HttpRequest.get(FollowMicroserviceIp+"/v1.1/follow-microservice/notice/SBPlusPage?subjectId="+
                subjectId+"&index="
                +index+"&pageSize="
                +pageSize).body();
        return JSON.parse(resp);
    }

    public void changeProject(String id,String projectName,String organization,String describe,String status, String monthFee){
        Map data = new HashMap();
        data.put("id", id);
        if(projectName!=null){
            data.put("projectName", projectName);
        }
        if(organization!=null){
            data.put("organization", organization);
        }
        if(describe!=null){
            data.put("describe", describe);
        }
        if(status!=null){
            data.put("status", status);
        }
        if(monthFee!=null){
            data.put("monthFee", monthFee);
        }
        String result =HttpRequest.put(ProjectMicroserviceIp+"/v1.1/project-microservice/projects").form(data).body();
    }

    public void addFeedBack(String subjectId){
        String result =HttpRequest.post(ProcessManagementMicroserviceIp
                +"/v1.1/processmanagement-microservice/feedback?subjectId="
        +subjectId).body();
    }

    public void savePlusSubmitFeedBack(String subjectId,String createTime,String content,String status){
        String result1 =HttpRequest.post(ProcessManagementMicroserviceIp
                +"/v1.1/processmanagement-microservice/feedback/content?subjectId="+subjectId
                +"&createTime="+createTime
                +"&content="+content
        ).body();
        String result2 =HttpRequest.put(ProcessManagementMicroserviceIp
                +"/v1.1/processmanagement-microservice/feedback/status?subjectId="+subjectId
                +"&createTime="+createTime
                +"&status="+status
        ).body();
    }

    /*public File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        if (fileName == null){
            return null;
        }
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若须要防止生成的临时文件重复,能够在文件名后添加随机码
        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /*public String getStr(File jsonFile){
        String jsonStr;
        try {
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            return jsonStr;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/


    //暂时使用中间层
    public String addPicToFB(String subjectId, String createTime,MultipartFile picture, HttpServletRequest request) throws IOException{
        //File file=this.MultipartFileToFile(picture);
        //String picStr=this.getStr(file);
        Map data = new HashMap();
        data.put("subjectId", subjectId);
        data.put("createTime",createTime);
        data.put("picture", picture);
        String result =HttpRequest.post(ProcessManagementMicroserviceIp
                +"/v1.1/processmanagement-microservice/feedback/pictures").form(data).body();
        return result;
    }

    public void addNotice(String subjectId){
        String result =HttpRequest.post(FollowMicroserviceIp
                +"/v1.1/follow-microservice/notice?subjectId="+subjectId).body();
    }

    public void savePlusSubmitNotice(String subjectId,String createTime,String content,String status){
        String result1 =HttpRequest.post(FollowMicroserviceIp
                +"/v1.1/follow-microservice/notice/content?subjectId="+subjectId
                +"&createTime="+createTime
                +"&content="+content
        ).body();
        String result2 =HttpRequest.put(FollowMicroserviceIp
                +"/v1.1/follow-microservice/notice/status?subjectId="+subjectId
                +"&createTime="+createTime
                +"&status="+status
        ).body();
    }

    public Object findAllNoticeByOrg(String organization,String index,String pageSize){
        List res=new ArrayList<>();
        String resp1=HttpRequest.get(ProjectMicroserviceIp+"/v1.1/project-microservice/proId/organization?organization="+
                organization).body();
        List tmp1=(List)JSON.parse(resp1);
        for(Object o:tmp1){
            String s= (String) o;
            String resp2=HttpRequest.get(FollowMicroserviceIp+"/v1.1/follow-microservice/notice/subjectId?subjectId="+
                    s).body();
            List tmp2 = (List)JSON.parse(resp2);
            for(Object os:tmp2){
                res.add(os);
            }
        }
        List Final=new ArrayList<>();
        int realSize=res.size();
        int realIndex= Integer.parseInt(index);
        int realPageSize= Integer.parseInt(pageSize);
        int rest=realSize%realPageSize;
        int numOfPages=0;
        if(rest==0){
            numOfPages=realSize/realPageSize;
        }
        else{
            numOfPages=realSize/realPageSize+1;
        }
        if(realIndex>numOfPages){
            return Final;
        }
        for(int i=(realIndex-1)*realPageSize;i<realSize&&i<realIndex*realPageSize;i++){
            Final.add(res.get(i));
        }
        return Final;
    }


    public Object findAllFeedBackByOrg(String organization,String index,String pageSize){
        List res=new ArrayList<>();
        String resp1=HttpRequest.get(ProjectMicroserviceIp+"/v1.1/project-microservice/proId/organization?organization="+
                organization).body();
        List tmp1=(List)JSON.parse(resp1);
        for(Object o:tmp1){
            String s= (String) o;
            String resp2=HttpRequest.get(ProcessManagementMicroserviceIp
                    +"/v1.1/processmanagement-microservice/feedback/subjectId?subjectId="
                    +s).body();
            List tmp2 = (List)JSON.parse(resp2);
            for(Object os:tmp2){
                res.add(os);
            }
        }
        List Final=new ArrayList<>();
        int realSize=res.size();
        int realIndex= Integer.parseInt(index);
        int realPageSize= Integer.parseInt(pageSize);
        int rest=realSize%realPageSize;
        int numOfPages=0;
        if(rest==0){
            numOfPages=realSize/realPageSize;
        }
        else{
            numOfPages=realSize/realPageSize+1;
        }
        if(realIndex>numOfPages){
            return Final;
        }
        for(int i=(realIndex-1)*realPageSize;i<realSize&&i<realIndex*realPageSize;i++){
            Final.add(res.get(i));
        }
        return Final;
    }

    public void deleteFBById(String id){
        String resp1=HttpRequest.delete(ProcessManagementMicroserviceIp
                +"/v1.1/processmanagement-microservice/feedback/Id?id="
                +id).body();
    }

    public void addProject(String projectName,String organization,String describe,String status, String monthFee){
        Map data = new HashMap();
        if(projectName!=null){
            data.put("projectName", projectName);
        }
        if(organization!=null){
            data.put("organization", organization);
        }
        if(describe!=null){
            data.put("describe", describe);
        }
        if(status!=null){
            data.put("status", status);
        }
        if(monthFee!=null){
            data.put("monthFee", monthFee);
        }
        String result =HttpRequest.post(ProjectMicroserviceIp+"/v1.1/project-microservice/projects").form(data).body();
    }
}
