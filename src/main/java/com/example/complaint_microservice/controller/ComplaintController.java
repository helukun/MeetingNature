package com.example.complaint_microservice.controller;

import com.example.complaint_microservice.model.Complaint;
import com.example.complaint_microservice.service.ComplaintService;
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
 * @description：ProjectController
 * @date ：2022-12-9 15:18
 * @version : 2.0
 */

@CrossOrigin
@RestController
public class ComplaintController {
    @Autowired
    private ComplaintService complaintService;


    //good
    @GetMapping("/v2.0/complaint-microservice/complaint/findSub")
    public Object findProByProId(String subjectId){
        return complaintService.findProByProId(subjectId);
    }

    //good
    @PostMapping("/v2.0/complaint-microservice/complaint")
    public String addComplaint(Complaint complaint){
        return complaintService.addComplaint(complaint);
    }


    //good
    @DeleteMapping("/v2.0/complaint-microservice/complaint")
    public void deleteComplaint(String id){
        complaintService.deleteComplaint(id);
    }


    //good
    @GetMapping("/v2.0/complaint-microservice/complaint/all")
    public List findAllComplaint(){
        return complaintService.findAllComplaint();
    }


    //good
    @GetMapping("/v2.0/complaint-microservice/complaint/FB")
    public Object findFBByProId(String subjectId){
        return complaintService.findFBByProId(subjectId);
    }


    //good
    @GetMapping("/v2.0/complaint-microservice/complaint/NOT")
    public Object findNotByProId(String subjectId){
        return complaintService.findNotByProId(subjectId);
    }

    //good
    @PutMapping("/v2.0/complaint-microservice/complaint/status")
    public void changeStatus(String id,String status){
        complaintService.changeStatus(id,status);
    }
}
