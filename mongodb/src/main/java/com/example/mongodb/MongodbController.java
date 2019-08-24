package com.example.mongodb;

import com.example.beans.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class MongodbController {
	@Autowired
	private MongoTemplate mongotemplate;
	
	public void insert(Long id,String username,String password) {
		User user=new User(id,username,password);
		mongotemplate.insert(user);
	}
}