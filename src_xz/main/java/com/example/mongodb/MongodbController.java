package com.example.mongodb;

import com.example.beans.Blog;
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
	
	//注册
	
	public static boolean register(String username,String password) {
		if(findUser(username)) return false;
		else {
			User user=new User();
			user.setId(0);
			user.setPassword(password);
			user.setUsername(username);
			mongotemplate.save(user);
			return true;
		}
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
	
	public void changeusername(String username1,String username2,String password) {
		Query query=new Query();
		Criteria criteria=new Criteria();
		criteria.and("username").is(username1);
		criteria.and("password").is(password);
		User ret=mongotemplate.findOne(query.addCriteria(criteria), User.class);

		if(ret!=null) ret.setUsername(username2);
		mongotemplate.save(ret);
	}

	//获取文章内容

	public Blog getBlog(int id)
	{
		Query query = new Query();
		Blog blog=mongotemplate.findOne(query.addCriteria(Criteria.where("id").is(id)),Blog.class);
		if(blog!=null){
			return blog;
		}
		else{
			System.out.println("获取失败");
			return null;
		}
	}
}