package com.example.result;

public class BlogResult {
	private String title;
	private String author;
	private long bid;
	private String Abstract;
	private int viewCount;
	private int likeCount;
	private int commentCount;
	public void setbid(long bid) {
		this.bid=bid;
	}
	public long getbid() {
		return this.bid;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthor(){return author;}
	public void setAuthor(String author){this.author=author;}
	public String getAbstract(){return Abstract;}
	public void setAbstract(String Abstract) {this.Abstract=Abstract;}
	public int getViewCount(){return viewCount;}

	public int getLikeCount(){return likeCount;}

	public int getCommentCount(){return commentCount;}
	public void setViewCount(int viewcount) {
		this.viewCount=viewcount;
	}
	public void setlikeCount(int likecount) {
		this.likeCount=likecount;
	}
	public void setcommentcount(int commentcount) {
		this.commentCount=commentcount;
	}
}
