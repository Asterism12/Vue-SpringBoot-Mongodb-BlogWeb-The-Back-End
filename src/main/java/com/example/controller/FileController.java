package com.example.controller;


import com.example.result.MessageResult;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.ROOT;

@RestController
public class FileController {
    private final static String fileDir="files";
    private final static String rootPath = System.getProperty("user.home")+ File.separator+fileDir+File.separator;


    @PostMapping(value = "api/upload")
    @CrossOrigin
    @ResponseBody

    //博客上传文件
    public MessageResult singleFileUpload(@RequestParam(value="file")MultipartFile file, HttpServletRequest request){

        System.out.println("上传文件 ");
        try {
            byte[] bytes = file.getBytes();
            if (file.isEmpty()) {
                System.out.println("null");
                return new MessageResult(400, "文件为空");
            }
            Path path = Paths.get(ROOT + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(ROOT));
            }
            //文件写入指定路径
            Files.write(path, bytes);
            return new MessageResult(200, path.toString());
        } catch (Exception e) {
            System.out.println("error");
            return new MessageResult(400, "error");
        }

    }




    /**
     * http://localhost:8080/file/download?fileName=新建文本文档.txt
     * @param fileName
     * @param response
     * @param request
     * @return
     */
    @RequestMapping("api/download")
    public MessageResult downloadFile(@RequestParam String fileName, final HttpServletResponse response, final HttpServletRequest request){
        OutputStream os = null;
        InputStream is= null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setContentType("application/x-download;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"), "iso-8859-1"));
            //读取流
            File f = new File(rootPath+fileName);
            is = new FileInputStream(f);
            if (is == null) {
                return new MessageResult(400,"下载附件失败");
            }
            //复制
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            return new MessageResult(400,"下载附件失败");
        }
        //文件的关闭放在finally中
        finally
        {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                return new MessageResult(400,"e.getMessage()");
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                return new MessageResult(400,"e.getMessage()");
            }
        }
        return new MessageResult(200,"下载成功");
    }
}
