package com.nbteam.dao;

import com.nbteam.entity.User;
/**
 * 用户功能接口
 * @author ma
 *
 */
public interface UserDao {

    /**
     * 用户注册方法
     * @param user 用户对象
     */
    public int registerUser(User user);

    /**
     * 用户登录方法
     * @param name  用户名
     * @param password  用户密码
     * @return  成功返回true,否则返回false
     */
    public boolean isLoginUser(String name,String password);

}
