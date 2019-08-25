package com.example.beans;

import org.springframework.data.annotation.Id;

public class Entity {
	@Id
	private long Id;
	
	public long getId() {
		return Id;
	}
	public void setId(long m) {
		this.Id=m;
	}
}
