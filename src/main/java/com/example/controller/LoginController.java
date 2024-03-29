package com.example.controller;

import com.example.result.MessageResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.beans.User;

import javax.validation.Valid;


@Controller
public class LoginController {
	@Autowired
	private MongoTemplate mongotemplate;

    @RequestMapping("/hh")
	@ResponseBody
    public void Hello() {
        mongotemplate.dropCollection(User.class);
    }


	@CrossOrigin
	@PostMapping(value="/api/login")
	@ResponseBody
	public MessageResult login(@RequestBody @Valid User requestUser, BindingResult result) {
		if (result.hasErrors())
			return new MessageResult(300,"登录失败，请把用户名和密码设置为6-12位");
		String username=requestUser.getUsername();
		username=HtmlUtils.htmlEscape(username);
		System.out.println("用户登录 "+username);
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username);
		criteria.and("password").is(User.encode(requestUser.getPassword()));
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret!=null) return new MessageResult(200,"登录成功");
		else {
			return new MessageResult(400,"登录失败，用户名或密码错误");
		}
	}
	

}
