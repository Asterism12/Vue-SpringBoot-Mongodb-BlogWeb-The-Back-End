package com.example.result;

public class ImgResult {
    private int code;
    private String imgurl;

    public ImgResult(int code, String imgurl) {
        this.code=code;
        this.imgurl=imgurl;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code=code;
    }
    public String getImgurl() {
        return imgurl;
    }
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
