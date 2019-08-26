package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.springframework.data.mongodb.core.query.Query;


@Controller
public class FindUserController {
    @Autowired
    private static MongoTemplate mongotemplate;

    @CrossOrigin
    @GetMapping(value="/user")
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
