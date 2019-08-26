package com.example.beans;

import org.springframework.data.annotation.Id;

public class Entity {
	@Id
	private int Id;
	
	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id=Id;
	}
}
