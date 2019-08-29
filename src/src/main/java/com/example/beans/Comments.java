package com.example.beans;

import java.util.Date;

public class Comments extends Entity{
    private String content;

    private Date date;

    private String username;

    public void setContent(String content) {
        this.content=content;
    }

    public void setDate() { this.date = new Date();}

 	public void setUsername(String username){this.username=username;} 
  
    public String getContent() { return content; }

    public String getUsername(){return username;}

    public Date getDate() { return this.date; }

}
