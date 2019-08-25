package com.example.controller;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
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
	
	@CrossOrigin
	@PostMapping(value="/login")
	@ResponseBody
	public Result login(@RequestBody User requestUser) {
		System.out.println("hello world");
		String username=requestUser.getName();
		username=HtmlUtils.htmlEscape(username);
		System.out.println(username);
		if(MongodbController.login(username,requestUser.getPassword())) {
			return new Result(200);
		}
		else {
			String message="账号密码错误";
			System.out.println("test");
			return new Result(400);
		}
	}
}
