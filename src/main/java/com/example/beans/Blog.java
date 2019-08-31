package com.example.beans;


import java.util.ArrayList;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection="blog")
public class Blog extends Entity{
	public Blog() {
		viewCount=0;
		likeCount=0;
		commentCount=0;
	}
	private long bid;
	private String title;
	private String content;
	private String author;
	private String date;
	private int viewCount;
	private int likeCount;
	private int commentCount;
	private int code;//文章分类
	private String Abstract;
	private int weight;

	private ArrayList<Comments> commentsArrayList = new ArrayList<Comments>();
	public void setbid() {
		this.bid=super.getId();
      	this.weight=viewCount/5+(int)bid;
	}
	
	public long getbid() {
		return this.bid;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	
	public void setCommentsList(ArrayList<Comments> a) {
		this.commentsArrayList=a;
	}
	public void setContent(String content) {
      	if(content==null)
          this.Abstract=null;
		this.content=content;
      	this.Abstract=content.substring(0,content.length()>250?250:content.length());
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setViewCount(){
		this.viewCount++;
		this.weight=viewCount/5+(int)bid;
	}

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



	public int getViewCount(){return viewCount;}

	public int getLikeCount(){return likeCount;}

	public int getCommentCount(){return commentCount;}

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


}
