package com.example.controller;

import com.example.beans.Blog;
import com.example.beans.Comments;
import com.example.result.MessageResult;
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
    private MongoTemplate mongotemplate;

    @CrossOrigin
    @GetMapping(value="api/comment")
    @ResponseBody
    //发布评论
    public MessageResult publishComment(@RequestParam(value="username") String username, @RequestParam(value="bid") int id, @RequestParam(value="content")String comment)
    {
      	System.out.println("发布评论 "+username+" "+id+" "+comment);
        Query query = new Query();
        username= HtmlUtils.htmlEscape(username);
        comment=HtmlUtils.htmlEscape(comment);
        Blog blog=mongotemplate.findOne(query.addCriteria(Criteria.where("bid").is(id)),Blog.class);
      
        if(blog != null)
        {
            Comments comments = new Comments();
            comments.setContent(comment);
            comments.setUsername(username);
            
            comments.setId(mongotemplate.count(new Query(), Comments.class)+1);
            blog.writeComments(comments);
            blog.setCommentCount();
            mongotemplate.save(blog);
            return new MessageResult(200,"发布成功");
        }
        else return new MessageResult(400,"发布失败");
    }
}
