package com.example.controller;

import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.*;

@Controller

public class UploadController {
    private String UPLOAD_FOLDER;

    @PostMapping(value = "api/upload")
    @CrossOrigin
    public Object singleFileUpload(MultipartFile file){
        if (Objects.isNull(file) || file.isEmpty()) {
            return "文件为空，请重新上传";
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(UPLOAD_FOLDER));
            }
            //文件写入指定路径
            Files.write(path, bytes);
            return "文件上传成功";
        } catch (IOException e) {
            e.printStackTrace();
            return "后端异常...";
        }
    }
}


