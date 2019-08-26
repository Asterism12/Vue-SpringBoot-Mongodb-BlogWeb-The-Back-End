package com.example.beans;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.RestController;

@Document(collection="blog")
public class Blog extends Entity{
    private static MongoTemplate mongotemplate;

	private String title;

	private String content;

	private String imgURL;

	private String author;

	private Date date;

    private Integer viewCount;

    private Integer likeCount;

    private Integer commentCount;

    private Integer code;//文章分类

	private String Abstract;

    private ArrayList<Comments> commentsArrayList = new ArrayList<Comments>();

    public void setTitle(String title) {
		this.title=title;
	}

	public void setContent(String content) {
		this.content=content;
	}

	public void setImgURL(String imgURL){this.imgURL=imgURL;}

    public void setDate() { this.date = new Date();}

	public void setViewCount(Integer viewCount){this.viewCount=viewCount;}

	public void setAuthor(String author){this.author=author;}

	public void setLikeCount(Integer likeCount){this.likeCount=likeCount;}

	public void setCommentCount(Integer commentCount){this.commentCount=commentCount;}

	public void setCode(Integer code){this.code=code;}

	public void setAbstract(String Abstract){this.Abstract=Abstract;}

	public String getTitle() {
		return title;
	}

	public String getLabel() {
		return content;
	}

	public String getAuthor(){return author;}

	public Date getDate(){return date;}

	public String getImgURL(){return imgURL;}

	public Integer getViewCount(){return viewCount;}

	public Integer getLikeCount(){return likeCount;}

	public Integer getCommentCount(){return commentCount;}

	public Integer getCode(){return code;}

	public String getAbstract(){return Abstract;}

	//发评论
    public void writeComments(Comments comments)
    {
        commentsArrayList.add(comments);
        comments.setDate();
    }
    //删评论
    public void deleteComments(Comments comments)
    {
        commentsArrayList.remove(comments);
    }

   /* //搜索博文题目
    public List<Blog> searchTitle(String keyword,Integer code)
    {
        Pattern pattern = Pattern.compile("^.*+keyword+.*$",Pattern.CASE_INSENSITIVE);//???
        Query query = new Query(Criteria.where("title").regex(pattern).and("code").is(code));
        List<Blog> resault = mongotemplate.find(query,Blog.class,"blog");
        return resault;
    }

    //搜索博文内容
	public List<Blog> searchContent(String keyword,Integer code)
	{
		Pattern pattern = Pattern.compile("^.*+keyword+.*$",Pattern.CASE_INSENSITIVE);//???
		Query query = new Query(Criteria.where("Content").regex(pattern).and("code").is(code));
		List<Blog> resault = mongotemplate.find(query,Blog.class,"blog");
		return resault;
	}*/


}
