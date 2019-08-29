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
    public Result publishComment(@RequestParam(value="bid") int id, @RequestParam(value="username") String username, @RequestParam(value="content")String comment)
    {
        Query query = new Query();
        username= HtmlUtils.htmlEscape(username);
        comment=HtmlUtils.htmlEscape(comment);
        Blog blog=mongotemplate.findOne(query.addCriteria(Criteria.where("_id").is(id)),Blog.class);
        if(blog != null)
        {
            Comments comments = new Comments();
            comments.setContent(comment);
            comments.setUsername(username);
            
            comments.setId(mongotemplate.count(new Query(), Comments.class));
            blog.writeComments(comments);
            return new Result(200,"发布成功");
        }
        else return new Result(400,"发布失败");
    }
}
