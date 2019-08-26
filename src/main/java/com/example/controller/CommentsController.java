package com.example.controller;

import com.example.beans.Blog;
import com.example.beans.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class CommentsController {
    @Autowired
    private static MongoTemplate mongotemplate;

    @CrossOrigin
    @GetMapping(value="/comment")
    @ResponseBody
    //发布评论
    public void publishComment(int id,String username,String comment)
    {
        Query query = new Query();
        username= HtmlUtils.htmlEscape(username);
        comment=HtmlUtils.htmlEscape(comment);
        Blog blog=mongotemplate.findOne(query.addCriteria(Criteria.where("id").is(id)),Blog.class);
        Comments comments = new Comments();
        comments.setContent(comment);
        comments.setUsername(username);
        blog.writeComments(comments);
    }


}
