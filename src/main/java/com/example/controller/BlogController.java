package com.example.controller;

import com.example.beans.Blog;
import com.example.beans.User;
import com.example.result.BlogResult;
import com.example.result.ImgResult;
import com.example.result.MessageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller

public class BlogController {
    @Autowired
    private MongoTemplate mongotemplate;
    public static  final String ImgDir = "/usr/local/uploadimg/";

    @RequestMapping("/")
    public String Hello() {
        /*mongotemplate.findAllAndRemove(new Query(),Blog.class);
        mongotemplate.findAllAndRemove(new Query(),User.class);*/
        return "helloworld";
    }
    @CrossOrigin
    @GetMapping(value="/api/blogs")
    @ResponseBody
    //展示文章内容
    public Blog getBlog(@RequestParam(value="bid") long id) {
        System.out.println("展示博文 "+id);
        Query query=new Query();
        Criteria criteria=new Criteria();
        Blog ret=mongotemplate.findOne(query.addCriteria(Criteria.where("bid").is(id)),Blog.class);
        if(ret!=null) {
        	ret.setViewCount();
            mongotemplate.save(ret);
            return ret;
        }
        else {
            System.out.println("没有找到");
            return ret;
        }
    }

    //通过关键字检索

    @CrossOrigin
    @GetMapping(value="api/lists")
    @ResponseBody
    //搜索博文内容或者题目
    public List<BlogResult> searchBlog(@RequestParam(value="username") String username,@RequestParam(value="keyword") String keyword, @RequestParam(value="classification") int code)
    {
        System.out.println("博文内容搜索 "+keyword+" "+code);
        User ret=mongotemplate.findOne(new Query().addCriteria(Criteria.where("username").is(username)), User.class);
        if(ret!=null) {
        ret.addsearch(keyword);
        mongotemplate.save(ret);
        }
        Pattern pattern = Pattern.compile(keyword,Pattern.CASE_INSENSITIVE);
        Criteria criteria = new Criteria();
        if(code!=0) {
        	criteria.orOperator(Criteria.where("title").regex(pattern).and("code").is(code),Criteria.where("content").regex(pattern).and("code").is(code));
        }
        else criteria.orOperator(Criteria.where("title").regex(pattern),Criteria.where("content").regex(pattern));
        Query query = new Query(criteria);
        query.with(new Sort(Sort.Direction.DESC,"weight"));
        List<Blog> resault = mongotemplate.find(query,Blog.class);
        List<BlogResult> blogs=new ArrayList<BlogResult>();
        for(int i=0;i<resault.size();i++) {
        	BlogResult blogresult=new BlogResult();
        	blogresult.setAbstract(resault.get(i).getAbstract());
            blogresult.setTitle(resault.get(i).getTitle());
            blogresult.setAuthor(resault.get(i).getAuthor());
            blogresult.setbid(resault.get(i).getbid());
            blogresult.setcommentcount(resault.get(i).getCommentCount());
            blogresult.setlikeCount(resault.get(i).getLikeCount());
            blogresult.setViewCount(resault.get(i).getViewCount());
            blogs.add(blogresult);
        }
        return blogs;
        
    }



   @CrossOrigin
    @PostMapping(value="api/publish")
    @ResponseBody
    //发布博文
    public MessageResult publishBlog(@RequestBody Blog requestblog)
    {
        Blog blog = new Blog();
        Query query=new Query();
        blog.setId(mongotemplate.count(query,Blog.class)+1);
        blog.setbid();
        blog.setTitle(requestblog.getTitle());
        blog.setContent(requestblog.getContent());
        blog.setAuthor(requestblog.getAuthor());
        blog.setCode(requestblog.getCode());
        blog.setDate(requestblog.getDate());
        User ret=mongotemplate.findOne(query.addCriteria(Criteria.where("username").is(requestblog.getAuthor())),User.class);
        if(ret==null ) return new MessageResult(400,"发布失败");
        else {
        	ret.addBlog(blog);
        	mongotemplate.save(ret);
        	mongotemplate.save(blog);
        	return new MessageResult(200,"发布成功");
        }
    }


    @CrossOrigin
    @GetMapping(value="api/blogdelete")
    //删除博文
    public MessageResult deleteBlog(@RequestParam(value="bid") long id)
    {
        System.out.println("删除博文 "+id);
        Query query = new Query();
        Blog ret=mongotemplate.findOne(query.addCriteria(Criteria.where("bid").is(id)),Blog.class);
        
        if(ret != null)
        {
        	String username=ret.getAuthor();
            User ret2=mongotemplate.findOne(new Query().addCriteria(Criteria.where("username").is(username)), User.class);
            ret2.deleteBlog(ret);
            ret.setTitle(null);
            ret.setContent(null);
            ret.setAuthor(null);
            ret.setCode(0);
            ret.getList().clear();
            ret.setDate(null);
            mongotemplate.save(ret);
            mongotemplate.save(ret2);
            return new MessageResult(200,"删除成功");
        }
        else
        {
            return new MessageResult(400,"删除失败");
        }
    }


    @CrossOrigin
    @GetMapping(value="api/blogmodify")
    //修改文章
    public MessageResult editBlog(@RequestParam(value="bid") long id, @RequestParam(value="title") String title, @RequestParam(value="content") String content)
    {
        Query query = new Query();
        Blog ret=mongotemplate.findOne(query.addCriteria(Criteria.where("bid").is(id)),Blog.class);
        
        if(ret != null)
        {
        	String username=ret.getAuthor();
            User user=mongotemplate.findOne(new Query().addCriteria(Criteria.where("username").is(username)), User.class);
            user.deleteBlog(ret);
            ret.setContent(content);
            ret.setTitle(title);
            user.addBlog(ret);
            mongotemplate.save(ret);
            mongotemplate.save(user);
            return new MessageResult(200,"编辑成功");
        }
        else
        {
            return new MessageResult(400,"编辑失败");
        }
    }



    @PostMapping(value = "api/uploadimg")
    @CrossOrigin
    @ResponseBody
    //博客上传图片
    public ImgResult singleFileUpload(@RequestParam(value="file")MultipartFile file,HttpServletRequest request){

        System.out.println("上传图片 "+file.getOriginalFilename());
        try {

            if (file.isEmpty()) {
                System.out.println("null");
                return new ImgResult(400, null);
            }
            byte[] bytes = file.getBytes();
            System.out.println(file.getOriginalFilename());
            Path path = Paths.get(ImgDir + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(ImgDir));
            }
            //文件写入指定路径
            Files.write(path, bytes);
            String url="/uploadimg/"+file.getOriginalFilename();
            System.out.println(url);
            return new ImgResult(200, url);
        } catch (Exception e) {
            System.out.println("error");
            return new ImgResult(400, null);
        }

    }
	
	@CrossOrigin
    @PostMapping(value="/api/recommend")
    @ResponseBody
    //用户推荐
    public List<BlogResult> userRecommend(@RequestBody User requestuser){
    	String username=requestuser.getUsername();
    	User ret=mongotemplate.findOne(new Query().addCriteria(Criteria.where("username").is(username)), User.class);
    	List<BlogResult> recommend=new ArrayList<BlogResult>();
    	List<Blog> searchresult=new ArrayList<Blog>();
    	if(ret==null) {
    		Query query=new Query();
    		query.with(new Sort(Sort.Direction.DESC,"weight"));
    		query.limit(6);
    		searchresult=mongotemplate.find(query,Blog.class);
    	}
    	else {//针对搜索到的用户进行推荐
    		for(int i=0;i<20;i++) {
    			int j;
              	if(ret.searchhistory[i]==""||ret.searchhistory[i]==null) continue;
    			for(j=0;j<i;j++) {
    				if(ret.searchhistory[i].equals(ret.searchhistory[j])) break;
    				
    			}
    			if(j<i) continue;
    			else {
    				Pattern pattern = Pattern.compile("^.*"+ret.searchhistory[i]+".*$",Pattern.CASE_INSENSITIVE);
    				Criteria criteria=new Criteria();
    				criteria.orOperator(Criteria.where("title").regex(pattern),Criteria.where("content").regex(pattern));
    				Query query=new Query(criteria);
    				query.with(new Sort(Sort.Direction.DESC,"weight"));
    				searchresult.addAll(mongotemplate.find(query, Blog.class));
    			}
    		}
    		
    	}
      	int length;
      	length=6<searchresult.size()?6:searchresult.size();
    	for(int i=0;i<length;i++) {
        	BlogResult blogresult=new BlogResult();
        	blogresult.setAbstract(searchresult.get(i).getAbstract());
            blogresult.setTitle(searchresult.get(i).getTitle());
            blogresult.setAuthor(searchresult.get(i).getAuthor());
            blogresult.setbid(searchresult.get(i).getbid());
            blogresult.setcommentcount(searchresult.get(i).getCommentCount());
            blogresult.setlikeCount(searchresult.get(i).getLikeCount());
            blogresult.setViewCount(searchresult.get(i).getViewCount());
            recommend.add(blogresult);
        }
		return recommend;
    }
}
