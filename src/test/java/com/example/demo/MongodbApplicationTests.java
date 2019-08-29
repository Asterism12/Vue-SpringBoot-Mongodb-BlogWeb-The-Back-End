package com.example.demo;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.beans.Blog;
import com.example.beans.User;
import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbApplicationTests {
	@Autowired
	private MongoTemplate mongotemplate;
	
	/*@org.junit.Test
	public void contextLoads() {
		Blog blog=new Blog();
		blog.setId(3);
		blog.setbid();
		mongotemplate.save(blog);
	}*/
	
	/*@org.junit.Test
	public void testdelete() {
		User b=new User(30,"1333","2051");
		mongotemplate.save(b);
	}
	
	@org.junit.Test
	public void delete() {
		User c=new User(30L,"1227","0038");
		mongotemplate.remove(c);
	}*/
	
	@org.junit.Test
	public void find() {
		Query query=new Query();
		query.addCriteria(Criteria.where("_id").is(2L));
		Update update=Update.update("bid", 2L);
		UpdateResult upsert=mongotemplate.updateFirst(query, update, Blog.class);
		
	}
}
