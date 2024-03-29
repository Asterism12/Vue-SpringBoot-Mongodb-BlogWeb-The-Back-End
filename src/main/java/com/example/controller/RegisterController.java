package com.example.controller;

import com.example.result.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.beans.*;

import javax.validation.Valid;

@Controller
public class RegisterController {
    @Autowired
    private MongoTemplate mongotemplate;

    @CrossOrigin
    @PostMapping(value = "/api/register")
    @ResponseBody
    public MessageResult register(@Valid @RequestBody User requestUser, BindingResult result) {
        if (result.hasErrors())
            return new MessageResult(300,"注册失败，请把用户名和密码设置为6-12位");
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
            ret.setId(mongotemplate.count(new Query(), User.class)+1);
            mongotemplate.save(ret);
            return new MessageResult(200,"注册成功");
        } else {
            return new MessageResult(400,"注册失败，用户名重复");
        }
        
    }
}