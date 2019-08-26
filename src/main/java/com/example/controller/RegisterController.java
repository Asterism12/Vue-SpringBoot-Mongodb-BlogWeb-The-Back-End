package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.result.Result;
import com.example.beans.User;
@Controller
public class RegisterController {
    @Autowired
    private MongoTemplate mongotemplate;

    @CrossOrigin
    @PostMapping(value = "/api/register")
    @ResponseBody
    public Result register(@RequestBody User requestUser) {

        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);

        String password = requestUser.getPassword();
        password = HtmlUtils.htmlEscape(password);

        Query query = new Query();
        User ret = mongotemplate.findOne(query.addCriteria(Criteria.where("username").is(username)), User.class);

        if (ret == null) {
        	ret=new User();
            ret.setPassword(password);
            ret.setUsername(username);
          	Query query2=new Query();
          	ret.setId(mongotemplate.count(query2, User.class)+1);
            mongotemplate.save(ret);
            return new Result(200);
        } else {
            return new Result(400);
        }

    }
}