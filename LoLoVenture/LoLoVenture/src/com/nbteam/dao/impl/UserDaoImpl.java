package com.nbteam.dao.impl;

import com.nbteam.dao.UserDao;
import com.nbteam.entity.User;

import java.io.*;

public class UserDaoImpl implements UserDao  {



    private static final File file = new File("LoLoVenture/src/com/nbteam/userInformationC.txt");



    @Override
    public int registerUser(User user) {

        //初始化输出流
        BufferedWriter bw = null;
        BufferedReader br = null;
        String id = user.getId();
        //用户信息 不能为空
        try {
            br = new BufferedReader(new FileReader(file));

            String str = null;
            //循环判断
            while((str = br.readLine()) != null) {
                String[] data = str.split("=>");
                if (data[0].equals(id)) {
                    return 2;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        if(user != null){
            try {
                //实例化输出流
                bw = new BufferedWriter( new FileWriter(file,true));

                //写入数据
                bw.write(user.getId()+"=>"+user.getPassword());
                //创建新行
                bw.newLine();
                //刷新数据
                bw.flush();

            } catch (IOException e) {
                //注册失败信息
                System.out.println("注册失败："+e.getMessage());
                return 0;
            }finally{
                try {
                    bw.close();
                } catch (IOException e) {
                    System.out.println("关闭BufferedWriter输出流异常："+e.getMessage());
                }
            }
            return 1;
        }else{
            //注册失败信息
            System.out.println("注册失败：用户信息不能为空");
            return 0;
        }

    }

    @Override
    public boolean isLoginUser(String name, String password) {
        boolean flag = false;
        //初始化BufferedReader
        BufferedReader br = null;

        if(!name.equals("") && !password.equals("")){
            try {
                br = new BufferedReader(new FileReader(file));

                String str = null;
                //循环判断
                while((str = br.readLine()) != null){
                    String[] data = str.split("=>");
                    if(data[0].equals(name)&&data[1].equals(password)){
                        flag = true;
                        break;
                    }

                }

            } catch (FileNotFoundException e) {
                System.out.println("登录异常："+e.getMessage());
            } catch (IOException e) {
                System.out.println("登录异常："+e.getMessage());
            }finally{
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("关闭BufferedReader输入流异常："+e.getMessage());
                }
            }
        } else {
            System.out.println("用户名和密码不能为空");
        }



        return flag;
    }
}
