package com.example.controller;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class DownloadController {

    private final static String rootPath ="";

    @PostMapping(value = "api/download")
    @CrossOrigin
    public Object downloadFile(@RequestParam String filename, final HttpServletResponse response, final HttpServletRequest request){
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try{
            //取得输出流
            outputStream = response.getOutputStream();
            //清空输出流
            response.reset();
            response.setContentType("application/x-download;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(filename.getBytes("utf-8"), "iso-8859-1"));
            //读物流
            File file = new File(rootPath+filename);
            inputStream = new FileInputStream(file);
            if(inputStream == null)
            {
                return "下载附件失败";
            }
            //复制
            IOUtils.copy(inputStream,response.getOutputStream());
            response.getOutputStream().flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件的关闭放在finally中
        finally {
            try {
                if(inputStream != null)
                    inputStream.close();
            }
            catch (IOException e){
                return "error";
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                return "error";
            }
        }
        return null;
    }
}
