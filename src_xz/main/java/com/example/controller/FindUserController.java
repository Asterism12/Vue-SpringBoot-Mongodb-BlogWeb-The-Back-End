package com.example.controller;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.springframework.data.mongodb.core.query.Query;

import com.example.mongodb.MongodbController;

@Controller
public class FindUserController {

    private static MongoTemplate mongotemplate;

    @CrossOrigin
    @PostMapping(value="/user")
    @ResponseBody

    public User finduser(@RequestBody String username) {

        username=HtmlUtils.htmlEscape(username);
        Query query=new Query();
        User ret=mongotemplate.findOne(query.addCriteria(Criteria.where("username").is(username)),User.class);
        if(ret!=null) {
            return ret;
        }
        else {
            System.out.println("没有找到");
            return null;
        }
    }
}
