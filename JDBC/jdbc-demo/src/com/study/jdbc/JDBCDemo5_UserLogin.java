package com.study.jdbc;

import com.study.pojo.Account;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户登录
 */
public class JDBCDemo5_UserLogin {
    @Test
    public void testLogin() throws Exception {
        // 获取连接：
        String url = "jdbc:mysql:///db1";
        String user = "root";
        String passWord = "123456";
        Connection conn = DriverManager.getConnection(url,user,passWord);

        // 接受用户输入的用户名和密码
        String name = "zhangsan";
        String pwd = "sdaf";
        String sql = "select * from tb_user where username = '"+name+"' " +
                "and password = '"+pwd+"'";

        // 获取stmt对象
        Statement stmt = conn.createStatement();

        // 执行sql语句
        ResultSet rs = stmt.executeQuery(sql);

        // 判断登录是否成功
        if (rs.next()){
            System.out.println("登录成功");
        }else {
            System.out.println("登录失败");
        }

        // 释放资源
        rs.close();
        stmt.close();
        conn.close();
    }


    /**
     * 演示SQL注入
     */
    @Test
    public void testLogin_Inject() throws Exception {
        // 获取连接：
        String url = "jdbc:mysql:///db1";
        String user = "root";
        String passWord = "123456";
        Connection conn = DriverManager.getConnection(url,user,passWord);

        // 接受用户输入的用户名和密码
        String name = "hfkls";
        String pwd = "' or '1' = '1";
        String sql = "select * from tb_user where username = '"+name+"' " +
                "and password = '"+pwd+"'";
        System.out.println(sql);
        // 获取stmt对象
        Statement stmt = conn.createStatement();

        // 执行sql语句
        ResultSet rs = stmt.executeQuery(sql);

        // 判断登录是否成功
        if (rs.next()){
            System.out.println("登录成功");
        }else {
            System.out.println("登录失败");
        }

        // 释放资源
        rs.close();
        stmt.close();
        conn.close();
    }

}
