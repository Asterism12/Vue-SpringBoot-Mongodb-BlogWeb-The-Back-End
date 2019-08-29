package com.example.beans;


import java.util.ArrayList;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="blog")
public class Blog extends Entity{
	private long bid;
	private String title;

	private String content;

	private String author;

	private String date;

	private Integer viewCount;

	private Integer likeCount;

	private Integer commentCount;

	private int code;//文章分类

	private String Abstract;

	private ArrayList<Comments> commentsArrayList = new ArrayList<Comments>();
	public void setbid() {
		this.bid=super.getId();
	}
	
	public long getbid() {
		return this.bid;
	}
	public void setTitle(String title) {
		this.title=title;
	}

	public void setContent(String content) {
      	if(content==null)
          this.Abstract=null;
		this.content=content;
      	this.Abstract=content.substring(0,40);
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setViewCount(){this.viewCount++;}

	public void setAuthor(String author){this.author=author;}

	public void setLikeCount(){this.likeCount++;}

	public void setCommentCount(){this.commentCount++;}

	public void setCode(int code){this.code=code;}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getAuthor(){return author;}

	public String getDate(){return date;}



	public Integer getViewCount(){return viewCount;}

	public Integer getLikeCount(){return likeCount;}

	public Integer getCommentCount(){return commentCount;}

	public int getCode(){return code;}

	public String getAbstract(){return Abstract;}

	public ArrayList<Comments> getList(){return commentsArrayList;}

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
