package com.example.result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.beans.Blog;

public class UserResult {
	private String username;
	private Date registertime;
	private List<Blog> blogs=new ArrayList<Blog>();
	private String sex;
	private int age;
	private String sign;
	public void setsign(String sign) {
		this.sign=sign;
	}
	public String getsign() {
		return sign;
	}
	
	public void setage(int age) {
		this.age=age;
	}
	public int getage() {
		return age;
	}
	public void setsex(String sex) {
		this.sex=sex;
	}
	public String getsex() {
		return sex;
	}	
	public List<Blog> getBlogs(){
		return blogs;
	}
	public void addBlog(Blog blog) {
		blogs.add(blog);
	}
	public void deleteBlog(Blog blog) {
		blogs.remove(blog);
	}
	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public Date getRegistertime() {
    	return registertime;
    }
    public void setRegistertime(Date registertime) {
    	this.registertime=registertime;
    }
}
