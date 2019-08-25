package com.example.mongodb;

import com.example.beans.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongodbController {
	@Autowired
	private static MongoTemplate mongotemplate;
	
	//登录
	public static boolean insertUser(String username,String password) {
		if(findUser(username)) return false;
		else {
			User user=new User();
			long m=mongotemplate.count(new Query(), User.class);
			user.setId(m);
			user.setPassword(password);
			user.setUsername(username);
			mongotemplate.save(user);
			return true;
		}
	}
	
	//注册
	
	public static boolean login(String username,String password) {
		Query query=new Query();

		query.addCriteria(new Criteria().where("username").is(username).and("password").is(password));
		User ret=mongotemplate.findOne(query, User.class);

		if(ret.toString().equals("")) return true;
		else return false;
	}
	
	//根据用户名查找用户
	
	public static boolean findUser(String username) {
		Query query=new Query();
		User ret=mongotemplate.findOne(query.addCriteria(Criteria.where("username").is(username)), User.class);
		if(ret!=null) {
			return true;
		}
		return false;
	}
	
	//修改用户名
	
	public static boolean changeusername(String username1,String username2,String password) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username1);
		criteria.and("password").is(password);
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret!=null) {
			ret.setUsername(username2);
			mongotemplate.save(ret);
			return true;
		}
		return false;
	}
	
	//修改密码
	
	public static boolean changepassword(String username,String password1,String password2) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username);
		criteria.and("password").is(password1);
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret!=null) {
			ret.setPassword(password2);
			mongotemplate.save(ret);
			return true;
		}
		return false;
	}
}