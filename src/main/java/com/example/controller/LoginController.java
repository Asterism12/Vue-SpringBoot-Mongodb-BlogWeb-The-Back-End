package com.example.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.beans.User;
import com.example.mongodb.MongodbController;
import com.example.result.Result;


@RestController
public class LoginController {
	
	@CrossOrigin
	@PostMapping(value="/login")
	@ResponseBody
	public Result login(@RequestBody User requestUser) {
		String username=requestUser.getUsername();
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
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
}
