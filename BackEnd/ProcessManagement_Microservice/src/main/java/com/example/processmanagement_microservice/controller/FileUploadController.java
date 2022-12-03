package com.example.processmanagement_microservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileUploadController {

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
        File file=new File(path+f.getOriginalFilename());
        f.transferTo(file);
    }
}
