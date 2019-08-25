package com.example.controller;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.mongodb.MongodbController;
import com.example.result.Result;

//与前端交互，从api/login接受前端信息，使用Result类返回信息

@Controller
public class LoginController {
	private static MongoTemplate mongotemplate;
	@CrossOrigin
	@PostMapping(value="/login")
	@ResponseBody
	public Result login(@RequestBody User requestUser) {
		
		String username=requestUser.getName();
		username=HtmlUtils.htmlEscape(username);
		
		if(login(username,requestUser.getPassword())) {
			return new Result(200);
		}
		else {
			String message="账号密码错误";
			System.out.println("test");
			return new Result(400);
		}
	}

	//登录

	public static boolean login(String username,String password) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username);
		criteria.and("password").is(password);
		com.example.beans.User ret=mongotemplate.findOne(query.addCriteria(criteria), com.example.beans.User.class);
		if(ret!=null) return true;
		else return false;
	}
}
