package com.example.beans;

import org.springframework.data.mongodb.core.mapping.Document;

import java.nio.file.Path;
import java.util.Date;

@Document(collection="uploadfile")
public class UploadFile extends Entity{
    private String author;

    private String title;

    private String date;

    private long size;

    private Path path;

    public void setAuthor(String author){this.author = author;}

    public void setTitle(String title){this.title = title;}

    public void setSize(long size){this.size = size;}

    public void setDate(String date) {
        this.date = date;
    }

    public void setPath(Path address){this.path = path;}

    public String getAuthor(){return this.author;}

    public String getTitle(){return this.title;}

    public String getDate(){return this.date;}

    public long getSize(){return this.size;}

    public Path getPath(){return this.path;}
}
