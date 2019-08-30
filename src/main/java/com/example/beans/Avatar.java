package com.example.beans;

import org.springframework.web.multipart.MultipartFile;

public class Avatar {
    private String username;
    private MultipartFile file;

    public Avatar() {
    }

    public Avatar(String username, MultipartFile file) {
        this.username = username;
        this.file = file;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
