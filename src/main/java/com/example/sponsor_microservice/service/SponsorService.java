package com.example.sponsor_microservice.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.kevinsawicki.http.HttpRequest;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SponsorService {
    @Autowired
    OSSService ossService;
    private String ProjectMicroserviceIp = "http://121.5.128.97:9006";
    private String FollowMicroserviceIp = "http://121.5.128.97:9008";
    private String ProcessManagementMicroserviceIp = "http://121.5.128.97:9005";
    private String UserMicroserviceIp = "http://121.5.128.97:9007";

    /*底层返回的已经是green项目所以不用再筛选*/
    public Object disRPInfo(String size) {
        String resp = HttpRequest.get(ProjectMicroserviceIp + "/v2.0/project-microservice/projects/random?size=" + size).body();
        System.out.println("result:"+resp);
        return JSON.parse(resp);
    }

    /*底层返回*/
    public Object findProjectByPage(int index, int pageSize) {
        String resp = HttpRequest.get(ProjectMicroserviceIp
                + "/v2.0/project-microservice/projects/page/green?index="
                + index + "&pageSize="
                + pageSize).body();
        return JSON.parse(resp);
    }

    public Object findProjectAndNotice(String id) {
        String resp1 = HttpRequest.get(ProjectMicroserviceIp + "/v2.0/project-microservice/projects/Id?id=" + id).body();
        String resp2 = HttpRequest.get(FollowMicroserviceIp + "/v2.0/follow-microservice/notice/subjectId?subjectId=" + id).body();
        System.out.println(resp1.substring(0, resp1.lastIndexOf('}')) + ',' + "\"notice\":" + resp2 + '}');
        Object obj = JSON.parse(resp1.substring(0, resp1.lastIndexOf('}')) + ',' + "\"notice\":" + resp2 + '}');
        return obj;
    }

    public void addFollow(String followerId, String subjectId) {
        Map data = new HashMap();
        data.put("followerId", followerId);
        data.put("subjectId", subjectId);
        String result = HttpRequest.post(FollowMicroserviceIp + "/v2.0/follow-microservice/follow").form(data).body();
    }

    public void removeFollow(String followerId,String subjectId){
        Map data = new HashMap();
        data.put("followerId", followerId);
        data.put("subjectId", subjectId);
        String result = HttpRequest.delete(FollowMicroserviceIp + "/v2.0/follow-microservice/follow").form(data).body();
    }

    public Object getUserById(String id) {
        String user = HttpRequest.get(UserMicroserviceIp + "/v2.0/user-microservice/users/id?id=" + id).body();
        return user;
    }

    //创建订单，但此时订单未完成
    public String CreateOrder(String sponsorId, String subjectId, String amount, String SponsorshipPeriod) {
        Map data = new HashMap();
        data.put("sponsorId", sponsorId);
        data.put("amount", amount);
        data.put("subjectId", subjectId);
        data.put("SponsorshipPeriod", SponsorshipPeriod);
        String res=HttpRequest.post(ProcessManagementMicroserviceIp + "/v2.0/processmanagement-microservice/orders").form(data).body();
        return res;
    }

    //改变订单状态，将订单状态从未完成变为已完成
    //订单完成的同时会生成对应订单的赞助关系
    public int ChangeOrderStatue(String orderId) {
        String result = HttpRequest.put(ProcessManagementMicroserviceIp + "/v2.0/processmanagement-microservice/orders?orderId=" + orderId).body();
        return Integer.parseInt(result);
    }


    //根据sponsorId生成对应的赞助关系，在修改订单状态之后调用,成功生成返回1，否则返回0
    public int CreateSponsorShip(String orderId) {
        String order = HttpRequest.get(ProcessManagementMicroserviceIp + "/v2.0/processmanagement-microservice/orders/Id?Id=" + orderId).body();
        String sponsorIdSubOne = order.substring(order.indexOf("\"sponsorId\"") + 13);
        String sponsorId = sponsorIdSubOne.substring(0, sponsorIdSubOne.indexOf('\"'));
        String subjectIdSubOne = order.substring(order.indexOf("\"subjectId\"") + 13);
        String subjectId = subjectIdSubOne.substring(0, subjectIdSubOne.indexOf('\"'));
        String creatTimeSubOne = order.substring(order.indexOf("\"setupTime\"") + 13);
        String creatTime = creatTimeSubOne.substring(0, 10);
        String daysSubone=order.substring(order.indexOf("\"sponsorshipPeriod\"")+21);
        String days=daysSubone.substring(0,daysSubone.indexOf("\""));

        /*该段代码确定原赞助关系是否存在，如果存在则创建日期选原赞助日期的起始时间*/
        String originSponsorship=HttpRequest.get(ProcessManagementMicroserviceIp+"/v2.0/processmanagement-microservice/PK?sponsorId="+sponsorId+"&subjectId="+subjectId).body();
        if(!originSponsorship.equals("")){/*假如赞助关系已经存在*/
            creatTimeSubOne=originSponsorship.substring(originSponsorship.indexOf("\"cutoffTime\"")+14);
            creatTime=creatTimeSubOne.substring(0,10);
        }
        int[] months = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int startyear = Integer.parseInt(creatTime.substring(0, creatTime.indexOf('-')));
        int startmonth = Integer.parseInt(creatTime.substring(creatTime.indexOf('-') + 1, creatTime.indexOf('-') + 3));
        int startday = Integer.parseInt(creatTime.substring(creatTime.lastIndexOf('-') + 1, creatTime.lastIndexOf('-') + 3));
        int daynumber = Integer.parseInt(days);
        while (daynumber > 0) {
            daynumber -= months[startmonth];
            if (daynumber >= 0) {
                startmonth += 1;
                if (startmonth >= 13) {
                    startyear += 1;
                    startmonth = 1;
                }
            }
            else
                startday = daynumber + months[startmonth];
        }

        /*生成最终的日期串*/
        String EndTime = startyear + "-";
        if(startmonth<10)
            EndTime+="0";
        EndTime+=startmonth+"-";
        if(startday<10)
            EndTime+="0";
        EndTime+=startday;

        Map data = new HashMap();
        data.put("sponsorId", sponsorId);
        data.put("subjectId", subjectId);
        data.put("cutoffTime", EndTime);
        String result = HttpRequest.post(ProcessManagementMicroserviceIp + "/v2.0/processmanagement-microservice/sponsorship").form(data).body();
        return Integer.parseInt(result);
    }

    public String addProfile(String id, MultipartFile picture, String storagePath){
        String res=ossService.uploadFile(picture,storagePath);
        String tmp=HttpRequest.post(UserMicroserviceIp+"/v2.0/user-microservice/picturePath"
                +"?id="+id
                +"&newPath="+res).body();
        return tmp;
    }


    //new
    public Object findFeedBackInfoBySPPlusPage(String sponsorId,String index,String pageSize){
        String res=HttpRequest.get(ProcessManagementMicroserviceIp+"/v2.0/processmanagement-microservice/feedback/sponsorIdPlusPage?sponsorId="+sponsorId
                +"&index="+index
                +"&pageSize="+pageSize).body();

        String SizeRes=HttpRequest.get(ProcessManagementMicroserviceIp+"/v2.0/processmanagement-microservice/feedback/sponsorId?sponsorId="+sponsorId).body();
        List tmp2= (List) JSON.parse(SizeRes);
        int Totalsize=tmp2.size();

        List tmp= (List) JSON.parse(res);
        int size=tmp.size();

        List tmpPro=new ArrayList<>();
        for(int i=0;i<size;i++){
            Object o=tmp.get(i);
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

            String organizationsub1=proResp.substring(proResp.indexOf("\"organization\"")+1);
            String organizationsub2=organizationsub1.substring((organizationsub1.indexOf('\"')+1));
            String organizationsub3=organizationsub2.substring(organizationsub2.indexOf('\"')+1);
            String organization=organizationsub3.substring(0,organizationsub3.indexOf('\"'));

            String statussub1=proResp.substring(proResp.indexOf("\"status\"")+1);
            String statussub2=statussub1.substring((statussub1.indexOf('\"')+1));
            String statussub3=statussub2.substring(statussub2.indexOf('\"')+1);
            String status=statussub3.substring(0,statussub3.indexOf('\"'));
            if(!status.equals("green"))
                continue;
            String result=objS.substring(0,objS.lastIndexOf('}'))+','+"\"projectName\":"+'\"'+projectName+'\"'+','+"\"organization\":"+'\"'+organization+'\"'+'}';
            tmpPro.add(JSON.parse(result));
        }

        Map map=new HashMap<>();
        map.put("List",tmpPro);
        map.put("Total",Totalsize);
        Object o=map;
        return o;
    }

    public Object findNoticeInfoBySPPlusPage(String followerId,String index,String pageSize){
        String res=HttpRequest.get(FollowMicroserviceIp+"/v2.0/follow-microservice/notice/page/followId?followerId="+followerId
                +"&index="+index
                +"&pageSize="+pageSize).body();

        String SizeRes=HttpRequest.get(FollowMicroserviceIp+"/v2.0/follow-microservice/notice/followerId?followerId="+followerId).body();
        List tmp2= (List) JSON.parse(SizeRes);
        int Totalsize=tmp2.size();

        List tmp= (List) JSON.parse(res);
        int size=tmp.size();

        List tmpPro=new ArrayList<>();
        for(int i=0;i<size;i++){
            Object o=tmp.get(i);
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

            String organizationsub1=proResp.substring(proResp.indexOf("\"projectName\"")+1);
            String organizationsub2=organizationsub1.substring((organizationsub1.indexOf('\"')+1));
            String organizationsub3=organizationsub2.substring(organizationsub2.indexOf('\"')+1);
            String organization=organizationsub3.substring(0,organizationsub3.indexOf('\"'));

            String statussub1=proResp.substring(proResp.indexOf("\"status\"")+1);
            String statussub2=statussub1.substring((statussub1.indexOf('\"')+1));
            String statussub3=statussub2.substring(statussub2.indexOf('\"')+1);
            String status=statussub3.substring(0,statussub3.indexOf('\"'));
            if(!status.equals("green"))
                continue;
            String result=objS.substring(0,objS.lastIndexOf('}'))+','+"\"projectName\":"+'\"'+projectName+'\"'+','+"\"organization\":"+'\"'+organization+'\"'+'}';
            tmpPro.add(JSON.parse(result));
        }

        Map map=new HashMap<>();
        map.put("List",tmpPro);
        map.put("Total",Totalsize);
        Object o=map;
        return o;
    }
    /*根据followerId分页返回其关注的项目信息*/
    public Object findProjectInfoBySPPlusPage(String followerId,String index,String pageSize){
        String tmpList=HttpRequest.get(FollowMicroserviceIp+"/v2.0/follow-microservice/follow/followerId?followerId="+followerId).body();
        List followList= (List) JSON.parse(tmpList);
        List originList=new ArrayList<>();
        List result=new ArrayList<>();
        for(int i=0;i<followList.size();i++){
            String follow=String.valueOf(followList.get(i));
            /*从中手动提取出subjectId*/
            String subjectId=follow.substring(follow.indexOf("\"subjectId\"")+13,follow.lastIndexOf("\""));
            String project=HttpRequest.get(ProjectMicroserviceIp+"/v2.0/project-microservice/projects/Id?id="+subjectId).body();
            originList.add(JSON.parse(project));
        }
        for(int i=0;i<originList.size();i++){
            String proResp=String.valueOf(originList.get(i));
            String statussub1=proResp.substring(proResp.indexOf("\"status\"")+1);
            String statussub2=statussub1.substring((statussub1.indexOf('\"')+1));
            String statussub3=statussub2.substring(statussub2.indexOf('\"')+1);
            String status=statussub3.substring(0,statussub3.indexOf('\"'));
            if(!status.equals("green"))
                continue;
            result.add(originList.get(i));
        }
        /*关注的green总项目数*/
        int total=result.size();
        //页数
        int pages=total/Integer.parseInt(pageSize);
        if(total%Integer.parseInt(pageSize)!=0)
            pages+=1;
        List pageresult=new ArrayList<>();
        for(int i=(Integer.parseInt(index)-1)*Integer.parseInt(pageSize);i<(Integer.parseInt(index)-1)*Integer.parseInt(pageSize)+3;i++)
        {
            if(i>total-1)
                break;
            pageresult.add(result.get(i));
        }
        Map map=new HashMap<>();
        map.put("List",pageresult);
        map.put("Total",total+"");
        Object o=map;
        return o;
    }
}



