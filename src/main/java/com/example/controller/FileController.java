package com.example.controller;


import com.example.beans.UploadFile;
import com.example.beans.User;
import com.example.result.MessageResult;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class FileController {
    @Autowired
    private MongoTemplate mongotemplate;

    private final static String FileDir = "/usr/local/uploadfiles/";
    private final static String rootPath = System.getProperty("user.home") + File.separator + FileDir + File.separator;


    @PostMapping(value = "api/upload")
    @CrossOrigin
    @ResponseBody

   //博客上传文件
    public MessageResult singleFileUpload(@RequestParam(value = "file") MultipartFile file,@RequestParam(value = "date")String date, @RequestParam(value = "author")String author,@RequestParam(value = "size")long size,@RequestParam(value = "name") String title,HttpServletRequest request) {

        UploadFile uploadfile = new UploadFile();
        uploadfile.setId(mongotemplate.count(new Query(), UploadFile.class)+1);
        uploadfile.setDate(date);
        uploadfile.setAuthor(author);
        uploadfile.setSize(size);
        uploadfile.setTitle(title);

        System.out.println("上传文件 ");
        try {
            byte[] bytes = file.getBytes();
            if (file.isEmpty()) {
                System.out.println("null");
                return new MessageResult(400, "文件为空");
            }
            Path path = Paths.get(FileDir + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(FileDir));
            }
            //文件写入指定路径
            Files.write(path, bytes);
            uploadfile.setPath(path);
            mongotemplate.save(uploadfile);

            return new MessageResult(200, "上传成功");
        } catch (Exception e) {
            System.out.println("error");
            return new MessageResult(400, "error");
        }

    }


    /**
     * http://localhost:8080/file/download?fileName=新建文本文档.txt
     *
     * @param fileName
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("api/download")
    public MessageResult downloadFile(@RequestParam String fileName, final HttpServletResponse response, final HttpServletRequest request) {
        OutputStream os = null;
        InputStream is = null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setContentType("application/x-download;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso-8859-1"));
            //读取流
            File f = new File(rootPath + fileName);
            is = new FileInputStream(f);
            if (is == null) {
                return new MessageResult(400, "下载附件失败");
            }
            //复制
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            return new MessageResult(400, "下载附件失败");
        }
        //文件的关闭放在finally中
        finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                return new MessageResult(400, "e.getMessage()");
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                return new MessageResult(400, "e.getMessage()");
            }
        }
        return new MessageResult(200, "下载成功");
    }

    @PostMapping(value = "api/filelist")
    @CrossOrigin
    @ResponseBody

    public List<UploadFile> showFile() {
        return mongotemplate.findAll(UploadFile.class);
    }
}
