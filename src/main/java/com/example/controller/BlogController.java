package com.example.controller;

import com.example.beans.Blog;
import com.example.beans.User;
import com.example.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;
import java.util.regex.Pattern;

@Controller
public class BlogController {
    @Autowired
    private MongoTemplate mongotemplate;
    @RequestMapping("/")
    public String Hello() {
        return "helloworld";
    }
    @CrossOrigin
    @GetMapping(value="/user")
    //展示文章内容
    public Blog getBlog(long id) {

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
    @GetMapping(value="api/lists1")

    //搜索作者
    public List<User> searchAuthor(String keyword)
    {
        Pattern pattern = Pattern.compile("^.* + keyword + .*$",Pattern.CASE_INSENSITIVE);//???
        Query query = new Query(Criteria.where("author").regex(pattern));
        List<User> resault = mongotemplate.find(query,User.class,"user");
        return resault;
    }



    @CrossOrigin
    @GetMapping(value="api/lists2")

    //搜索博文内容或者题目
    public List<Blog> searchBlog(String keyword,Integer code)
    {
        Pattern pattern = Pattern.compile("^.*+keyword+.*$",Pattern.CASE_INSENSITIVE);//???
        Query query = new Query();
        query.addCriteria(Criteria.where("Content").regex(pattern).and("code").is(code));
        query.addCriteria(Criteria.where("title").regex(pattern).and("code").is(code));
        List<Blog> resault = mongotemplate.find(query,Blog.class,"blog");
        return resault;
    }

    @CrossOrigin
    @GetMapping(value="api/publish")
    @ResponseBody
    //发布博文
    public Result publishBlog(String username, String title, String article)
    {
        System.out.println(username+" "+title+" "+article);
        Blog blog = new Blog();
        Query query=new Query();
        blog.setId(mongotemplate.count(query,Blog.class)+1);
        blog.setAuthor(username);
        blog.setContent(article);
        blog.setTitle(title);
        blog.setDate();
        System.out.println(blog.getAuthor()+" "+blog.getTitle()+" "+blog.getContent());
        mongotemplate.save(blog);
        return new Result(200);
    }


}
