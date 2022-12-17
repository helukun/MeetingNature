package com.example.sponsor_microservice.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.kevinsawicki.http.HttpRequest;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SponsorService {
    private String ProjectMicroserviceIp="http://121.5.128.97:9006";
    private String FollowMicroserviceIp="http://121.5.128.97:9008";
    private String ProcessManagementMicroserviceIp="http://121.5.128.97:9005";
    public Object disRPInfo(String size) {
        String resp =HttpRequest.get(ProjectMicroserviceIp+"/v1.1/project-microservice/projects/random?size="+size).body();
        return JSON.parse(resp);
    }

    public Object findProjectByPage(int index,int pageSize){
        String resp=HttpRequest.get(ProjectMicroserviceIp
                + "/v1.1/project-microservice/projects/page?index="
                +index+"&pageSize="
                +pageSize).body();
        return JSON.parse(resp);
    }

    public Object findProjectAndNotice(String id){
        String resp1 =HttpRequest.get(ProjectMicroserviceIp+"/v1.1/project-microservice/projects/Id?id="+id).body();
        String resp2 =HttpRequest.get(FollowMicroserviceIp+"/v1.1/follow-microservice/notice/subjectId?subjectId="+id).body();
        System.out.println(resp1.substring(0,resp1.lastIndexOf('}'))+','+"\"notice\":"+resp2+'}');
        Object obj=JSON.parse( resp1.substring(0,resp1.lastIndexOf('}'))+','+"\"notice\":"+resp2+'}');
        return obj;
    }

    //new
    public Object findFeedBackInfoBySPPlusPage(String sponsorId,String index,String pageSize){
        String res=HttpRequest.get(ProcessManagementMicroserviceIp+"/v1.6/processmanagement-microservice/feedback/sponsorIdPlusPage?sponsorId="+sponsorId
        +"&index="+index
        +"&pageSize="+pageSize).body();

        List tmp= (List) JSON.parse(res);
        Object size=tmp.size();

        Map map=new HashMap<>();
        map.put("List",JSON.parse(res));
        map.put("Total",size);
        Object o=map;
        return o;
    }
    //CAONIMA
    public List findFeedBackInfoByPage(String index,String pageSize,String sponsorId){
        String resp1 =HttpRequest.get(ProcessManagementMicroserviceIp+"/v1.1/processmanagement-microservice/feedback/sponsorId?sponsorId="+sponsorId).body();
        List tmp= (List) JSON.parse(resp1);
        List result_list=new ArrayList<>();
        int size=tmp.size();
        for(int i=0;i<size;i++){
            Object o=tmp.get(i);
            String objS= String.valueOf(o);
            int pos=objS.indexOf("\"subjectId\"");
            String subs=objS.substring(pos+13);
            int firstIndex=subs.indexOf('\"');
            //找到的subjectId
            String resId=subs.substring(0,firstIndex);
            String proResp =HttpRequest.get(ProjectMicroserviceIp+"/v1.1/project-microservice/projects/Id?id="+resId).body();
            //int startPosOfProjectName=proResp.indexOf("\"projectName\"");
            String projectNamesub1=proResp.substring(proResp.indexOf("\"projectName\"")+1);
            String projectNamesub2=projectNamesub1.substring((projectNamesub1.indexOf('\"')+1));
            String projectNamesub3=projectNamesub2.substring(projectNamesub2.indexOf('\"')+1);
            String projectName=projectNamesub3.substring(0,projectNamesub3.indexOf('\"'));

            String organizationsub1=proResp.substring(proResp.indexOf("\"projectName\"")+1);
            String organizationsub2=organizationsub1.substring((organizationsub1.indexOf('\"')+1));
            String organizationsub3=organizationsub2.substring(organizationsub2.indexOf('\"')+1);
            String organization=organizationsub3.substring(0,organizationsub3.indexOf('\"'));

            String result=objS.substring(0,objS.lastIndexOf('}'))+','+"\"projectName\":"+'\"'+projectName+'\"'+','+"\"organization\":"+'\"'+organization+'\"'+'}';
            result_list.add(JSON.parse( result));
        }
        int realSize=result_list.size();
        int pageNum= realSize/Integer.parseInt(pageSize);
        int rest=realSize%Integer.parseInt(pageSize);
        List finalList=new ArrayList<>();
        if(rest==0){
            if(Integer.parseInt(index)>pageNum){
                return finalList;
            }
            for(int i=(Integer.parseInt(index)-1)*Integer.parseInt(pageSize);i<(Integer.parseInt(index))*Integer.parseInt(pageSize);i++){
                finalList.add(result_list.get(i));
            }
        }
        else{
            if(Integer.parseInt(index)<pageNum+1){
                for(int i=(Integer.parseInt(index)-1)*Integer.parseInt(pageSize);i<(Integer.parseInt(index))*Integer.parseInt(pageSize);i++){
                    finalList.add(result_list.get(i));
                }
            }
            else if(Integer.parseInt(index)==pageNum+1){
                for(int i=(Integer.parseInt(index)-1)*Integer.parseInt(pageSize);i<realSize;i++){
                    finalList.add(result_list.get(i));
                }
            }
            else{
                return finalList;
            }
        }
        return finalList;
    }

    public void addFollow(String followerId,String subjectId){
        Map data = new HashMap();
        data.put("followerId", followerId);
        data.put("subjectId", subjectId);
        String result =HttpRequest.post(FollowMicroserviceIp+"/v1.1/follow-microservice/follow").form(data).body();
    }
}
