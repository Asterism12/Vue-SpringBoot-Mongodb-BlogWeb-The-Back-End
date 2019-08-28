package com.example.beans;

import org.bson.internal.Base64;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="user")
public class User extends Entity{

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static String encode(String str) {
    	return Base64.encode(str.getBytes());
    }
    
    public static String decode(String str) {
    	return new String(Base64.decode(str));
    }
    
    public String toString() {
    	return username+password;
    }
}