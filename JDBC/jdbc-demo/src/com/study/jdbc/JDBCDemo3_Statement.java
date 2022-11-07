package com.study.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC API 详解：Statement
 */
public class JDBCDemo3_Statement {
    @Test
    public void testDML() throws Exception {
        // 1、注册驱动
        // Class.forName("com.mysql.jdbc.Driver");  可以省略

        // 2、获取连接
//        String url = "jdbc:mysql://localhost:3306/db1";
        String url = "jdbc:mysql:///db1";
        String userName = "root";
        String passWord = "123456";
        Connection conn = DriverManager.getConnection(url,userName,passWord);

        // 3、定义sql语句
        String sql = "update account set money = 2000 where id = 1";

        // 4、执行获取sql的对象，Statement
        Statement stmt = conn.createStatement();

        // 5、执行sql
        int count = stmt.executeUpdate(sql); // 执行完DML语句后，受影响的行数

        // 6、处理结果
//        System.out.println(count);
        if (count > 0){
            System.out.println("修改成功");
        }else {
            System.out.println("修改失败");
        }
        // 7、释放资源
        stmt.close();
        conn.close();
    }

    @Test
    public void testDDL() throws Exception {
        // 1、注册驱动
        // Class.forName("com.mysql.jdbc.Driver");  可以省略

        // 2、获取连接
//        String url = "jdbc:mysql://localhost:3306/db1";
        String url = "jdbc:mysql:///db1";
        String userName = "root";
        String passWord = "123456";
        Connection conn = DriverManager.getConnection(url,userName,passWord);

        // 3、定义sql语句
        String sql = "create database db2";

        // 4、执行获取sql的对象，Statement
        Statement stmt = conn.createStatement();

        // 5、执行sql
        int count = stmt.executeUpdate(sql); // 执行完DDL语句后,可能是0

        // 6、处理结果
//        System.out.println(count);
//        if (count > 0){
//            System.out.println("修改成功");
//        }else {
//            System.out.println("修改失败");
//        }
        // 7、释放资源
        stmt.close();
        conn.close();
    }

}
