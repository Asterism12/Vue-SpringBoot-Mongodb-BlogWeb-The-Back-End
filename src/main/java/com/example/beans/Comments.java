package com.example.beans;

import java.util.Date;

public class Comments extends Entity{
    private String comment;

    private Date date;

    private String username;

    public void setContent(String content) {
        this.comment=content;
    }

    public void setDate() { this.date = new Date();}

 	public void setUsername(String username){this.username=username;} 
  
    public String getContent() { return comment; }

    public String getUsername(){return username;}

    public Date getDate() { return this.date; }

}
