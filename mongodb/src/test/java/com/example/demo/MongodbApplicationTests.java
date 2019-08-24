package com.example.demo;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.beans.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbApplicationTests {
	@Autowired
	private MongoTemplate mongotemplate;
	
	/*@org.junit.Test
	public void contextLoads() {
		User a=new User(20L,"1999","0227");
		mongotemplate.insert(a);
	}*/
	
	@org.junit.Test
	public void testdelete() {
		User b=new User(30L,"1333","2051");
		mongotemplate.save(b);
	}
	
	@org.junit.Test
	public void delete() {
		User c=new User(30L,"1227","0038");
		mongotemplate.remove(c);
	}
	
	@org.junit.Test
	public void find() {
		Query query=new Query();
		query.addCriteria(Criteria.where("id").is(20L));
		System.out.println(mongotemplate.find(query, User.class));
	}
}
