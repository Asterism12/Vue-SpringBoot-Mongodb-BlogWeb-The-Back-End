package com.example.beans;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;


import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Document(collection="user")
public class User extends Entity{
    @NotEmpty
    @Length(min=6,max=12)
    private String username;
    @NotEmpty
    @Length(min=6,max=12)
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
	public void deleteBlog(Blog blog) {
		blogs.remove(blog);
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
    	return Base64.getEncoder().encodeToString(str.getBytes());
    }
    
    public Date getRegistertime() {
    	return registertime;
    }
    
    public String toString() {
    	return username+password;
    }
}