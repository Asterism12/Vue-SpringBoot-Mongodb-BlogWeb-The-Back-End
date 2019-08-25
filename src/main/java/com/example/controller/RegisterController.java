package com.example.controller;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.mongodb.MongodbController;
import com.example.result.Result;

//与前端交互，从api/register接受前端信息，使用Result类返回信息

@Controller
public class RegisterController {
	
	@CrossOrigin
	@RequestMapping(value="api/register")
	@ResponseBody
	public Result register(@RequestBody User requestUser) {
		
		String username=requestUser.getName();
		username=HtmlUtils.htmlEscape(username);
		
		if(MongodbController.insertUser(username,requestUser.getPassword())) {
			return new Result(200);
		}
		else {
			String message="账号已存在";
			System.out.println("test");
			return new Result(400);
		}
	}
}
