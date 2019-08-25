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
	
	public void insertUser(int id,String username,String password) {
		User user=new User();
		user.setId(id);
		user.setPassword(password);
		user.setUsername(username);
		mongotemplate.insert(user);
	}
	
	public static boolean login(String username,String password) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username);
		criteria.and("password").is(password);
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret!=null) return true;
		else return false;
	}
	
	public boolean findUser(String username) {
		Query query=new Query();
		User ret=mongotemplate.findOne(query.addCriteria(Criteria.where("username").is(username)), User.class);
		if(ret!=null) {
			System.out.println(ret);
			return true;
		}
		return false;
	}
	
	public void changeusername(String username1,String username2,String password) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username1);
		criteria.and("password").is(password);
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);
		if(ret!=null) ret.setUsername(username2);
		mongotemplate.save(ret);
	}
}