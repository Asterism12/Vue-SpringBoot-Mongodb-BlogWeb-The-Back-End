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
    @ResponseBody
    //搜索作者
    public List<Blog> searchAuthor(String keyword)
    {
        System.out.println(keyword);
        Pattern pattern = Pattern.compile("^.*" + keyword +".*$",Pattern.CASE_INSENSITIVE);//???
        Query query = new Query(Criteria.where("author").regex(pattern));
        List<Blog> resault = mongotemplate.find(query,Blog.class,"blog");
        for (int i=0;i<resault.size();i++)System.out.println(i+" "+resault.get(i).getTitle()+" "+resault.get(i).getContent()+" "+resault.get(i).getAuthor());
        return resault;
    }



    @CrossOrigin
    @GetMapping(value="api/lists2")
    @ResponseBody
    //搜索博文内容或者题目
    public List<Blog> searchBlog(String keyword,Integer code)
    {
        System.out.println(keyword+" "+code);
        Pattern pattern = Pattern.compile("^.*+keyword+.*$",Pattern.CASE_INSENSITIVE);//???
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("title").regex(pattern).and("code").is(code),Criteria.where("content").regex(pattern).and("code").is(code));
        Query query = new Query(criteria);
        List<Blog> resault = mongotemplate.find(query,Blog.class,"blog");
        for (int i=0;i<resault.size();i++)System.out.println(resault.get(i).getTitle()+" "+resault.get(i).getContent());
        return resault;
    }

    @CrossOrigin
    @GetMapping(value="api/publish")
    @ResponseBody
    //发布博文
    public Result publishBlog(String username, String title, String article, Integer code)
    {
        System.out.println(username+" "+title+" "+article);
        Blog blog = new Blog();
        Query query=new Query();
        blog.setId(mongotemplate.count(query,Blog.class)+1);
        blog.setTitle(title);
        blog.setContent(article);
        blog.setAuthor(username);
        blog.setCode(code);
        blog.setDate();
        System.out.println(blog.getAuthor()+" "+blog.getTitle()+" "+blog.getContent()+" "+blog.getCode());
        mongotemplate.save(blog);
        return new Result(200);
    }


}
