package com.example.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.beans.User;
import com.example.result.Result;

@Controller
public class UserController {
	@Autowired
	private MongoTemplate mongotemplate;
	
	@CrossOrigin
	@PostMapping("")
	@ResponseBody
	public Result changeusername(String username1,String username2,String password) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username1);
		criteria.and("password").is(User.encode(password));
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret==null) {
			return new Result(400);
		}
		else {
			ret.setUsername(username2);
			mongotemplate.save(ret);
			return new Result(200);
		}
	}
	
	@CrossOrigin
	@PostMapping("/")
	@ResponseBody
	public Result changepassword(String username,String password1,String password2) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username);
		criteria.and("password").is(User.encode(password1));
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret==null) {
			return new Result(400);
		}
		else {
			ret.setPassword(password2);
			mongotemplate.save(ret);
			return new Result(200);
		}
	}
	
	@CrossOrigin
	@PostMapping("/")
	@ResponseBody
	public Result attention(String username) {
		User ret=mongotemplate.findOne(new Query().addCriteria(Criteria.where("username").is(username)),User.class);
		if(ret==null) return new Result(400);
		else {
			ret.setattention();
			mongotemplate.save(ret);
			return new Result(200);
		}
	}
	
	@CrossOrigin
	@GetMapping("/userlists")
	@ResponseBody
	public List<User> findUsers(String keyword){
		if(keyword==null) {
			return mongotemplate.findAll(User.class);
		}
		Pattern pattern=Pattern.compile("^.*"+keyword+".*$",Pattern.CASE_INSENSITIVE);
		Query query=new Query(Criteria.where("username").regex(pattern));
		List<User> list=mongotemplate.find(query, User.class);
		return list;
	}
}
