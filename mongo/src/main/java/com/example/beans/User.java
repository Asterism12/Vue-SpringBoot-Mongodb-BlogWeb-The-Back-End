package com.example.beans;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection="user")
public class User {

    private String userId;

    private String name;

    private String uclass;

    private String email;

    private Date birthday;

    private int age;

    private int dataStatus;

    public User() {
    }

    public String getName() {
        return name;
    }

    public int getAge() {
    	return age;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUclass() {
        return uclass;
    }

    public void setUclass(String uclass) {
        this.uclass = uclass;
    }

    public String getEmail() {
        return email;
    }
}