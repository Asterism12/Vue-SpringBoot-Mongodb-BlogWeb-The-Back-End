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
	private String avatarurl;

	public String getAvatarurl() {
		return avatarurl;
	}

	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}

	public void setsign(String sign) {
		this.sign=sign;
	}
	public String getsign() {
		return sign;
	}
	public void setblogs(List<Blog> a) {
		blogs=a;
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
