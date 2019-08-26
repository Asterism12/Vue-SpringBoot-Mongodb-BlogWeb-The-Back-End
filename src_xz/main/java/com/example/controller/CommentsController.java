package com.example.controller;

import com.example.beans.Blog;
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

import java.util.List;
import java.util.regex.Pattern;

@Controller
public class CommentsController {

    private static MongoTemplate mongotemplate;

    @CrossOrigin
    @PostMapping(value="/comment")
    @ResponseBody
    //发布评论
    public void publishComment(String username,String comment) {
    }


}
