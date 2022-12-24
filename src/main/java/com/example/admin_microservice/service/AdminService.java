package com.example.admin_microservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.kevinsawicki.http.HttpRequest;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AdminService {

    private String AuditMicroserviceIp = "http://121.5.128.97:9011";
    private  String ProjectMicroserviceIp="http://121.5.128.97:9006";
    private  String ComplaintMicroserviceIp="http://121.5.128.97:9013";
    private String FollowMicroserviceIp="http://121.5.128.97:9008";
    private String ProcessManagementMicroserviceIp="http://121.5.128.97:9005";
    private String UserMicroserviceIp = "http://121.5.128.97:9007";
    /*项目状态修改为红*/
    public void ProjectStatusToRed(String projectId) {
        HttpRequest.put(AuditMicroserviceIp + "/v2.0/audit-microservice/projects/status/red?projectId=" + projectId).body();
    }

    /*项目状态修改为绿*/
    public void ProjectStatusToGreen(String projectId) {
        HttpRequest.put(AuditMicroserviceIp + "/v2.0/audit-microservice/projects/status/green?projectId=" + projectId).body();
    }

    /*项目状态修改为黄*/
    public void ProjectStatusToYellow(String projectId) {
        HttpRequest.put(AuditMicroserviceIp + "/v2.0/audit-microservice/projects/status/yellow?projectId=" + projectId).body();
    }

    /*根据项目id返回对应项目*/
    public Object findProByProId(String subjectId){
        String resp= HttpRequest.get(ProjectMicroserviceIp+"/v2.0/project-microservice/projects/Id?id="+
                subjectId).body();
        return JSON.parse(resp);
    }

    /*根据项目id找到所有的反馈*/
    public Object findFBByProId(String subjectId){
        String resp= HttpRequest.get(ProcessManagementMicroserviceIp+"/v2.0/processmanagement-microservice/feedback/subjectId?subjectId="+
                subjectId).body();
        return JSON.parse(resp);
    }

    public Object findNotByProId(String subjectId){
        String resp= HttpRequest.get(FollowMicroserviceIp+"/v2.0/follow-microservice/notice/subjectId?subjectId="+
                subjectId).body();
        return JSON.parse(resp);
    }
    public Object GetAllComplaint(){
        String resp= HttpRequest.get(ComplaintMicroserviceIp+"/v2.0/complaint-microservice/complaint/all").body();
        return JSON.parse(resp);
    }
    public void ChangComplaintStatusGreen(String id){
        HttpRequest.put(ComplaintMicroserviceIp+"/v2.0/complaint-microservice/complaint/status?id="+id
        +"&status="+"green").body();
    }

    public void DeleteComplaint(String id){
        HttpRequest.delete(ComplaintMicroserviceIp+"/v2.0/complaint-microservice/complaintOld?id="+id).body();
    }

    /*分页返回所有待审核项目*/
    public Object GetAllYellowProject(String index,String pageSize) {
        String resp = HttpRequest.get(ProjectMicroserviceIp + "/v2.0/project-microservice/projects/page/yellow?index="+index
        +"&pageSize="+pageSize).body();
        return JSON.parse(resp);
    }

    public ResponseEntity<Object> login(String name, String password) {
        HttpRequest httpRequest=HttpRequest.get(UserMicroserviceIp+"/v2.0/user-microservice/login?name="+name
                +"&password="+password+"&role="+"3");
        String res=httpRequest.body();
        if(httpRequest.badRequest()){
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        JSONObject jsonObject =  JSON.parseObject(res);
        return ResponseEntity.ok(jsonObject);
    }

    public ResponseEntity<Object> recoverEmail(String name) {
        Map data=new HashMap<>();
        data.put("name",name);
        HttpRequest httpRequest=HttpRequest.post(UserMicroserviceIp+"/v2.0/user-microservice/recoverEmail").form(data);
        String res=httpRequest.body();
        if(httpRequest.badRequest()){
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("验证码已发送",HttpStatus.OK);
    }

    public ResponseEntity<Object> recover(String name, String code, String newpassword) {
        Map data=new HashMap<>();
        data.put("name",name);
        data.put("code",code);
        data.put("newpassword",newpassword);
        HttpRequest httpRequest=HttpRequest.post(UserMicroserviceIp+"/v2.0/user-microservice/recover").form(data);
        String res=httpRequest.body();
        if(httpRequest.badRequest()){
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("新密码修改成功",HttpStatus.OK);
    }
}
