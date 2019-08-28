package com.example.beans;

import java.util.ArrayList;
import java.util.Date;

import org.bson.internal.Base64;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User extends Entity{

    private String username;
    private String password;
    private Date registertime;
    private int attention;
	private ArrayList<Blog> blogs=new ArrayList<Blog>();
	
	public void setattention() {
		this.attention++;
	}
	public int getattention() {
		return attention;
	}
	public ArrayList<Blog> getBlogs(){
		return blogs;
	}
	public void addBlog(Blog blog) {
		blogs.add(blog);
	}
    
    public User() {
    	this.registertime=new Date();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = encode(password);
    }
    
    public static String encode(String str) {
    	return Base64.encode(str.getBytes());
    }
    
    public static String decode(String str) {
    	return new String(Base64.decode(str));
    }
    
    public Date getRegistertime() {
    	return registertime;
    }
    
    public String toString() {
    	return username+password;
    }
}