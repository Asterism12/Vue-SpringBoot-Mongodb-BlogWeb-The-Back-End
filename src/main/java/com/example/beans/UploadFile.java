package com.example.beans;

import org.springframework.data.mongodb.core.mapping.Document;

import java.nio.file.Path;
import java.util.Date;

@Document(collection="uploadfile")
public class UploadFile extends Entity{
    private String author;

    private String title;

    private String date;

    private String size;

    private String path;

    public void setAuthor(String author){this.author = author;}

    public void setTitle(String title){this.title = title;}

    public void setSize(long size){
        if(size <= 1024)
            this.size=size + "B";
        else if (size>1024 && size<=1024*1024){
            this.size=size/1024 + "KB";
        }
        else
            this.size=size/1024/1024 + "MB";
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPath(String path){this.path = path;}

    public String getAuthor(){return this.author;}

    public String getTitle(){return this.title;}

    public String getDate(){return this.date;}

    public String getSize(){return this.size;}

    public String getPath(){return this.path;}
}
