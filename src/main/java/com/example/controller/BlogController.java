package com.example.controller;

import com.example.beans.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;
import java.util.regex.Pattern;

@Controller
public class BlogController {
    @Autowired
    private static MongoTemplate mongotemplate;

    @CrossOrigin
    @GetMapping(value="/user")
    @ResponseBody
    //展示文章内容
    public Blog getBlog(@RequestBody int id) {

        Query query=new Query();
        Blog ret=mongotemplate.findOne(query.addCriteria(Criteria.where("id").is(id)),Blog.class);
        if(ret!=null) {
            return ret;
        }
        else {
            System.out.println("没有找到");
            return null;
        }
    }

    //通过关键字检索
    @CrossOrigin
    @GetMapping(value="/lists")
    @ResponseBody
    //搜索博文题目
    public List<Blog> searchTitle(String keyword, Integer code)
    {
        Pattern pattern = Pattern.compile("^.*+keyword+.*$",Pattern.CASE_INSENSITIVE);//???
        Query query = new Query(Criteria.where("title").regex(pattern).and("code").is(code));
        List<Blog> resault = mongotemplate.find(query,Blog.class,"blog");
        return resault;
    }



    @CrossOrigin
    @GetMapping(value="/lists")
    @ResponseBody
    //搜索博文内容
    public List<Blog> searchContent(String keyword,Integer code)
    {
        Pattern pattern = Pattern.compile("^.*+keyword+.*$",Pattern.CASE_INSENSITIVE);//???
        Query query = new Query(Criteria.where("Content").regex(pattern).and("code").is(code));
        List<Blog> resault = mongotemplate.find(query,Blog.class,"blog");
        return resault;
    }

    @CrossOrigin
    @GetMapping(value="/publish")
    @ResponseBody
    //发布博文
    public void publishBlog(String username,String title,String article)
    {
        Blog blog = new Blog();
        blog.setAuthor(username);
        blog.setContent(article);
        blog.setTitle(title);
        blog.setDate();
        mongotemplate.save(blog);
    }


}
