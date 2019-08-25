package com.example.beans;

public class Blog extends Entity{
	private String title;
	private String label;
	public void setTitle(String title) {
		this.title=title;
	}
	public void setLabel(String label) {
		this.label=label;
	}
	public String getTitle() {
		return title;
	}
	public String getLabel() {
		return label;
	}
}
