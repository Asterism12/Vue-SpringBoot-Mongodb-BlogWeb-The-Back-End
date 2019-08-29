package com.example.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.beans.User;
import com.example.result.Result;

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
	public Result login(@RequestBody @Valid User requestUser, BindingResult result) {
		if (result.hasErrors())
			return new Result(300,"登录失败，请把用户名和密码设置为6-12位");
		String username=requestUser.getUsername();
		username=HtmlUtils.htmlEscape(username);
		System.out.println(username);
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username);
		criteria.and("password").is(User.encode(requestUser.getPassword()));
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret!=null) return new Result(200,"登陆成功");
		else {
			System.out.println("test");
			return new Result(400,"登录失败，用户名或密码错误");
		}
	}
	

}
