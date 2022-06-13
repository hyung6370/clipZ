package com.example.clipz2.Class;

public class User {
    private String idToken;
    private String userId;
    private String userNickname;
    private String passwd;
    private String imageurl;

    public User() {
        this.idToken = idToken;
        this.userId = userId;
        this.userNickname = userNickname;
        this.passwd = passwd;
        this.imageurl = imageurl;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
