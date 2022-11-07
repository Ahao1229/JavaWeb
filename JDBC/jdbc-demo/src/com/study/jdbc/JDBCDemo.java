package com.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * JDBC快速入门
 */
public class JDBCDemo {
    public static void main(String[] args) throws Exception {
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
        int count = stmt.executeUpdate(sql); // 受影响的行数

        // 6、处理结果
        System.out.println(count);

        // 7、释放资源
        stmt.close();
        conn.close();

    }
}
