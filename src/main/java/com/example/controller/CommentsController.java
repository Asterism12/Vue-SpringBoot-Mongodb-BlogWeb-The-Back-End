package com.example.controller;

import com.example.beans.Blog;
import com.example.beans.Comments;
import com.example.result.Result;
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
    @GetMapping(value="api/comment")

    //发布评论
    public Result publishComment(int id, String username, String comment)
    {
        Query query = new Query();
        username= HtmlUtils.htmlEscape(username);
        comment=HtmlUtils.htmlEscape(comment);
        Blog blog=mongotemplate.findOne(query.addCriteria(Criteria.where("id").is(id)),Blog.class);
        if(blog != null)
        {
            Comments comments = new Comments();
            comments.setContent(comment);
            comments.setUsername(username);
            blog.writeComments(comments);
            return new Result(200);
        }
        else return new Result(400);
    }

}
