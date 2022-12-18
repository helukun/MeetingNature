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
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    OSSService ossService;
    private String ProjectMicroserviceIp="http://121.5.128.97:9006";
    private String FollowMicroserviceIp="http://121.5.128.97:9008";
    private String ProcessManagementMicroserviceIp="http://121.5.128.97:9005";

    public Object findProByOrg(String organization){
        String resp=HttpRequest.get(ProjectMicroserviceIp+"/v2.0/project-microservice/projects/organization?organization="+
                organization).body();
        return JSON.parse(resp);
    }

    public Object findProByOrgPlusPage(String organization,int index,int pageSize){
        String resp=HttpRequest.get(ProjectMicroserviceIp+"/v2.0/project-microservice/projects/organizationPlusPage?organization="+
                organization+"&index="
                +index+"&pageSize="
                +pageSize).body();
        return JSON.parse(resp);
    }

    public Object findNotBySB(String subjectId) throws IOException {
        String resp=HttpRequest.get(FollowMicroserviceIp+"/v2.0/follow-microservice/notice/subjectId?subjectId="+
                subjectId).body();
        return JSON.parse(resp);
    }

    public Object findNotBySBPlusPage(String subjectId,int index,int pageSize) throws IOException {
        String resp=HttpRequest.get(FollowMicroserviceIp+"/v2.0/follow-microservice/notice/SBPlusPage?subjectId="+
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
        String result =HttpRequest.put(ProjectMicroserviceIp+"/v2.0/project-microservice/projects").form(data).body();
    }

    public String addFeedBack(String subjectId){
        String result =HttpRequest.post(ProcessManagementMicroserviceIp
                +"/v2.0/processmanagement-microservice/feedback?subjectId="
                +subjectId).body();
        return result;
    }

    public void savePlusSubmitFeedBack(String subjectId,String createTime,String content,String status){
        Map data1 = new HashMap();
        Map data2 = new HashMap();
        if(subjectId!=null){
            data1.put("subjectId", subjectId);
            data2.put("subjectId", subjectId);
        }
        if(createTime!=null){
            data1.put("createTime", createTime);
            data2.put("createTime", createTime);
        }
        if(content!=null){
            data1.put("content", content);
        }
        if(subjectId!=null){
            data2.put("status", status);
        }
        String result1 =HttpRequest.post(ProcessManagementMicroserviceIp
                +"/v2.0/processmanagement-microservice/feedback/content").form(data1).body();
        String result2 =HttpRequest.put(ProcessManagementMicroserviceIp
                +"/v2.0/processmanagement-microservice/feedback/status").form(data2).body();




        /*String result1 =HttpRequest.post(ProcessManagementMicroserviceIp
                +"/v2.0/processmanagement-microservice/feedback/content?subjectId="+subjectId
                +"&createTime="+createTime
                +"&content="+content
        ).body();*/
        /*String result2 =HttpRequest.put(ProcessManagementMicroserviceIp
                +"/v2.0/processmanagement-microservice/feedback/status?subjectId="+subjectId
                +"&createTime="+createTime
                +"&status="+status
        ).body();*/
    }

    public String addPicToFBcon(String subjectId, String createTime,MultipartFile picture,String storagePath){
        String res=ossService.uploadFile(picture,storagePath);
        String tmp=HttpRequest.post(ProcessManagementMicroserviceIp+"/v2.0/processmanagement-microservice/feedback/picPathOnly"
        +"?subjectId="+subjectId
        +"&createTime="+createTime
        +"&newPath="+res).body();
        return tmp;
    }

    public String addPicToProCon(String id,MultipartFile picture,String storagePath){
        String res=ossService.uploadFile(picture,storagePath);
        String tmp=HttpRequest.post(ProjectMicroserviceIp+"/v2.0/project-microservice/projects/picPathOnly"
                +"?id="+id
                +"&newPath="+res).body();
        return tmp;
    }

    public String addPicToNotCon(String subjectId,String createTime,MultipartFile picture,String storagePath){
        String res=ossService.uploadFile(picture,storagePath);
        String tmp=HttpRequest.post(FollowMicroserviceIp+"/v2.0/follow-microservice/notice/picPathOnly"
                +"?subjectId="+subjectId
                +"&createTime="+createTime
                +"&newPath="+res).body();
        return tmp;
    }



    public String addNotice(String subjectId){
        String result =HttpRequest.post(FollowMicroserviceIp
                +"/v2.0/follow-microservice/notice?subjectId="+subjectId).body();
        return result;
    }

    public void savePlusSubmitNotice(String subjectId,String createTime,String content,String status){
        String result1 =HttpRequest.post(FollowMicroserviceIp
                +"/v2.0/follow-microservice/notice/content?subjectId="+subjectId
                +"&createTime="+createTime
                +"&content="+content
        ).body();
        String result2 =HttpRequest.put(FollowMicroserviceIp
                +"/v2.0/follow-microservice/notice/status?subjectId="+subjectId
                +"&createTime="+createTime
                +"&status="+status
        ).body();
    }

    public Object findAllNoticeByOrg(String organization,String index,String pageSize){
        List res=new ArrayList<>();
        String resp1=HttpRequest.get(ProjectMicroserviceIp+"/v2.0/project-microservice/proId/organization?organization="+
                organization).body();
        List tmp1=(List)JSON.parse(resp1);
        for(Object o:tmp1){
            String s= (String) o;
            String resp2=HttpRequest.get(FollowMicroserviceIp+"/v2.0/follow-microservice/notice/subjectId?subjectId="+
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
        String resp1=HttpRequest.get(ProjectMicroserviceIp+"/v2.0/project-microservice/proId/organization?organization="+
                organization).body();
        List tmp1=(List)JSON.parse(resp1);
        for(Object o:tmp1){
            String s= (String) o;
            String resp2=HttpRequest.get(ProcessManagementMicroserviceIp
                    +"/v2.0/processmanagement-microservice/feedback/subjectId?subjectId="
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
        List realFinalRes=new ArrayList<>();
        int size=Final.size();
        for(int i=0;i<size;i++){
            Object o=Final.get(i);
            String objS= String.valueOf(o);
            int pos=objS.indexOf("\"subjectId\"");
            String subs=objS.substring(pos+13);
            int firstIndex=subs.indexOf('\"');
            //找到的subjectId
            String resId=subs.substring(0,firstIndex);
            String proResp =HttpRequest.get(ProjectMicroserviceIp+"/v2.0/project-microservice/projects/Id?id="+resId).body();
            //int startPosOfProjectName=proResp.indexOf("\"projectName\"");
            String projectNamesub1=proResp.substring(proResp.indexOf("\"projectName\"")+1);
            String projectNamesub2=projectNamesub1.substring((projectNamesub1.indexOf('\"')+1));
            String projectNamesub3=projectNamesub2.substring(projectNamesub2.indexOf('\"')+1);
            String projectName=projectNamesub3.substring(0,projectNamesub3.indexOf('\"'));

            /*String organizationsub1=proResp.substring(proResp.indexOf("\"projectName\"")+1);
            String organizationsub2=organizationsub1.substring((organizationsub1.indexOf('\"')+1));
            String organizationsub3=organizationsub2.substring(organizationsub2.indexOf('\"')+1);
            String organization=organizationsub3.substring(0,organizationsub3.indexOf('\"'));*/

            String result=objS.substring(0,objS.lastIndexOf('}'))
                    +','+"\"projectName\":"+'\"'+projectName+'\"'+'}';
            realFinalRes.add(JSON.parse(result));
        }

        return realFinalRes;
    }

    public void deleteFBById(String id){
        String resp1=HttpRequest.delete(ProcessManagementMicroserviceIp
                +"/v2.0/processmanagement-microservice/feedback/Id?id="
                +id).body();
    }

    public String addProject(String projectName,String organization,String describe,String status, String monthFee){
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
        String result =HttpRequest.post(ProjectMicroserviceIp+"/v2.0/project-microservice/projects").form(data).body();
        return result;
    }

    public String addNoticeSim(String content,String subjectId){
        Map data = new HashMap();
        if(content!=null){
            data.put("content", content);
        }
        if(subjectId!=null){
            data.put("subjectId", subjectId);
        }
        String result=HttpRequest.post(FollowMicroserviceIp+"/v2.0/follow-microservice/notice/sim").form(data).body();
        return result;
    }
}
