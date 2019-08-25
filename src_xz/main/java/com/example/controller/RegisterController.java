package com.example.controller;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.mongodb.MongodbController;
import com.example.result.Result;
import com.example.beans.*;
@Controller
public class RegisterController {
    private static MongoTemplate mongotemplate;
    @CrossOrigin
    @PostMapping(value="/register")
    @ResponseBody
    public Result register(@RequestBody User requestUser) {

        String username=requestUser.getName();
        username=HtmlUtils.htmlEscape(username);

        if(register(username,requestUser.getPassword())) {
            return new Result(200);
        }
        else {
            String message="注册失败";
            System.out.println("test");
            return new Result(400);
        }
    }

    //注册

    public static boolean register(String username,String password) {
        if(findUser(username)) return false;
        else {
            User user=new User();
            user.setPassword(password);
            user.setName(username);
            mongotemplate.save(user);
            return true;
        }
    }

    //根据用户名查找用户

    public static boolean findUser(String username) {
        Query query=new Query();
        com.example.beans.User ret=mongotemplate.findOne(query.addCriteria(Criteria.where("username").is(username)), com.example.beans.User.class);
        if(ret!=null) {
            return true;
        }
        return false;
    }
}
