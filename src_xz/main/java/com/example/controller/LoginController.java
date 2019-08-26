package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.beans.User;
//与前端交互，从api/login接受前端信息，使用Result类返回信息

@Controller
public class LoginController {
	@Autowired
	private static MongoTemplate mongotemplate;
	@CrossOrigin
	@PostMapping(value="/login")
	@ResponseBody
	public Result login(@RequestBody User requestUser) {
		
		String username=requestUser.getUsername();
		username=HtmlUtils.htmlEscape(username);

		String password = requestUser.getPassword();
		password = HtmlUtils.htmlEscape(password);

		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username);
		criteria.and("password").is(password);
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);

		if(ret!=null) {
			return new Result(200);
		}

		else {
			String message="账号密码错误";
			System.out.println("test");
			return new Result(400);
		}
	}

	
}
