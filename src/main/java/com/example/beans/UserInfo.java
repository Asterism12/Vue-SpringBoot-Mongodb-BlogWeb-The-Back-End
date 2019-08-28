package com.example.beans;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="userinfo")
public class UserInfo extends User {
	private int attention;
	private ArrayList<Blog> blogs=new ArrayList<Blog>();
	private Date registertime;
	
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
	public Date getregistertime() {
		return registertime;
	}
}
