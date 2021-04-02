package com.example.aboutmovies;

public class Cast {

    private String realName;
    private String nickName;
    private String posterPath;

    public Cast() {
    }

    public Cast(String realName, String nickName) {
        this.realName = realName;
        this.nickName = nickName;
    }

    public Cast(String realName, String nickName, String posterPath) {
        this.realName = realName;
        this.nickName = nickName;
        this.posterPath = posterPath;
    }

    public String getRealName() {
        return realName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
