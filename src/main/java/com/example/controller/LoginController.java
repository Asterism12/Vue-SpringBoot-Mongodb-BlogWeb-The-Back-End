package com.example.controller;


import com.example.mongodb.MongodbController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.beans.User;
import com.example.result.Result;


@Controller
public class LoginController {
	@Autowired
	private MongoTemplate mongotemplate;
	
    @RequestMapping("/")
    public String Hello() {
        return "helloworld";
    }


	@CrossOrigin
	@PostMapping(value="/login")
	@ResponseBody
	public Result login(User requestUser) {
		String username=requestUser.getUsername();
		username=HtmlUtils.htmlEscape(username);
		System.out.println(username);
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username);
		criteria.and("password").is(requestUser.getPassword());
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret!=null) return new Result(200);
		else {
			System.out.println("test");
			return new Result(400);
		}
	}
	

}