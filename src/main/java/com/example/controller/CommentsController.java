package com.example.controller;

import com.example.beans.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;




@Controller
public class CommentsController {
    @Autowired
    private static MongoTemplate mongotemplate;

    @CrossOrigin
    @GetMapping(value="/comment")
    @ResponseBody
    //发布评论
    public void publishComment(String username,String comment) {
    }


}
