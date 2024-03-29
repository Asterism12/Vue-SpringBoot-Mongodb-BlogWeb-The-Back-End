package com.example.beans;

import java.util.*;


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
	private List<Blog> blogs=new ArrayList<Blog>();
	private String sex;
	private int age;
	private String sign;
	private String avatarurl;
	public String[] searchhistory=new String[20];
	private int point=0;
	
	public void addsearch(String keyword) {
		if(keyword!="") {
			searchhistory[point]=keyword;
			point=(point+1)%20;
		}
	}

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

	public void setattention() {
		this.attention++;
	}
	public int getattention() {
		return attention;
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
    
    public User() {
    	this.registertime=new Date();
      	this.sex="男";
      	this.avatarurl="";
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