package com.example.demo;

import java.util.ArrayList;
import java.util.Date;
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
import com.example.beans.Comments;
import com.example.beans.User;
import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbApplicationTests {
	@Autowired
	private MongoTemplate mongotemplate;
	
	@org.junit.Test
	public void contextLoads() {
		Pattern pattern = Pattern.compile("^.*"+"string"+".*$",Pattern.CASE_INSENSITIVE);
        Criteria criteria = new Criteria();
        Query query = new Query(criteria);
        List<Blog> resault = mongotemplate.find(query,Blog.class);
        System.out.println(resault.size());
	}
	
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
		Blog b=new Blog();
		b.setId(0);
		b.setbid();
		b.setCode(1);
		b.setAuthor("ljx");
		b.setContent("There was an unexpected error (type=Internal Server Error, status=500).");
		b.setTitle("the first blog");
		Comments a=new Comments();
		a.setContent("good work");
		a.setDate();
		a.setId(1);
		mongotemplate.save(a);
		
		ArrayList<Comments> c=new ArrayList<Comments>();
		c.add(a);
		b.setCommentsList(c);
		mongotemplate.save(b);
	}
}
