package com.study.jdbc;

import org.junit.Test;

import java.sql.*;

/**
 * API详解：PreparedStatement
 */
public class JDBCDemo6_PreparedStatement {
    @Test
    public void testPreparedStatement() throws Exception {
        // 获取连接：
        String url = "jdbc:mysql:///db1";
        String user = "root";
        String passWord = "123456";
        Connection conn = DriverManager.getConnection(url,user,passWord);

        // 接受用户输入的用户名和密码
        String name = "zhangsan";
        String pwd = "123";

        // 定义sql
        String sql = "select * from tb_user where username = ? and password = ?";

        // 获取pstmt对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置？的值
        pstmt.setString(1,name);
        pstmt.setString(2,pwd);

        // 执行sql语句
        ResultSet rs = pstmt.executeQuery();

        // 判断登录是否成功
        if (rs.next()){
            System.out.println("登录成功");
        }else {
            System.out.println("登录失败");
        }

        // 释放资源
        rs.close();
        pstmt.close();
        conn.close();
    }


    /**
     * PreparedStatement原理
     */

    @Test
    public void testPreparedStatement2() throws Exception {
        // 获取连接：
        String url = "jdbc:mysql:///db1?useSSL=false&userServerPrepStmts=true";
        String user = "root";
        String passWord = "123456";
        Connection conn = DriverManager.getConnection(url,user,passWord);

        // 接受用户输入的用户名和密码
        String name = "zhangsan";
        String pwd = "123";

        // 定义sql
        String sql = "select * from tb_user where username = ? and password = ?";

        // 获取pstmt对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 设置？的值
        pstmt.setString(1,name);
        pstmt.setString(2,pwd);

        // 执行sql语句
        ResultSet rs = pstmt.executeQuery();

        // 判断登录是否成功
        if (rs.next()){
            System.out.println("登录成功");
        }else {
            System.out.println("登录失败");
        }

        // 释放资源
        rs.close();
        pstmt.close();
        conn.close();
    }

}
