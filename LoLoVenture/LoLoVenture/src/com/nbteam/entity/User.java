package com.nbteam.entity;

public class User {
    // 用户id
    private  String id;
    //用户密码
    private  String password;
    //用户最高分数记录
    public int maxScore;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
        super();
    }

    public User(String id,String password){
        super();
        this.id=id;
        this.password=password;
    }
}
