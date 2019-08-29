package com.example.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.beans.User;
import com.example.result.Result;

@Controller
public class UserController {
	@Autowired
	private MongoTemplate mongotemplate;
	@CrossOrigin
	@PostMapping("/api/user")
	@ResponseBody
	//个人主页
	public User usermain(@RequestParam(value="username")String username){
		Query query=new Query();
		User ret=mongotemplate.findOne(query.addCriteria(Criteria.where("username").is(username)),User.class);
		return ret;
	}
	@CrossOrigin
	@PostMapping("/hhh")
	@ResponseBody
	public Result changeusername(String username1,String username2,String password) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username1);
		criteria.and("password").is(User.encode(password));
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret==null) {
			return new Result(400,"用户名或原密码错误");
		}
		else {
			ret.setUsername(username2);
			mongotemplate.save(ret);
			return new Result(200,"修改成功");
		}
	}
	
	@CrossOrigin
	@PostMapping("/mm")
	@ResponseBody
	public Result changepassword(String username,String password1,String password2) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username);
		criteria.and("password").is(User.encode(password1));
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret==null) {
			return new Result(400,"用户名或原密码错误");
		}
		else {
			ret.setPassword(password2);
			mongotemplate.save(ret);
			return new Result(200,"修改成功");
		}
	}
	
	@CrossOrigin
	@PostMapping("/t")
	@ResponseBody
	public Result attention(String username) {
		User ret=mongotemplate.findOne(new Query().addCriteria(Criteria.where("username").is(username)),User.class);
		if(ret==null) return new Result(400,"用户不存在");
		else {
			ret.setattention();
			mongotemplate.save(ret);
			return new Result(200,"关注成功");
		}
	}

	@CrossOrigin
	@GetMapping(value="api/userlists")
	@ResponseBody
	public List<SecurityProperties.User> finduser(@RequestParam(value="keyword") String keyword) {
		System.out.println("搜索用户 "+keyword);
		if(keyword==null) {
			return mongotemplate.findAll(SecurityProperties.User.class);
		}
		Pattern pattern = Pattern.compile("^.*" + keyword +".*$",Pattern.CASE_INSENSITIVE);//???
		Query query = new Query(Criteria.where("username").regex(pattern));
		List<SecurityProperties.User> ret = mongotemplate.find(query, SecurityProperties.User.class,"user");
		if(ret!=null) {
			return ret;
		}
		else {
			return null;
		}
	}
}
