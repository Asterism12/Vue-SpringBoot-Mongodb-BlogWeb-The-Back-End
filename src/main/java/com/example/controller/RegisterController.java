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
import com.example.beans.*;
@Controller
public class RegisterController {
    @Autowired
    private static MongoTemplate mongotemplate;

    @CrossOrigin
    @PostMapping(value = "/api/register")
    @ResponseBody
    public Result register(@RequestBody User requestUser) {
		return new Result(200);
        
    }
}