package com.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo2_Connection {
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
        String sql1 = "update account set money = 3000 where id = 1";
        String sql2 = "update account set money = 3000 where id = 2";

        // 4、执行获取sql的对象，Statement
        Statement stmt = conn.createStatement();


        try {
            // 开启事务
            conn.setAutoCommit(false);

            // 5、执行sql
            int count1 = stmt.executeUpdate(sql1); // 受影响的行数
//            System.out.println(3/0);
            int count2 = stmt.executeUpdate(sql2); // 受影响的行数

            // 6、处理结果
            System.out.println(count1);
            System.out.println(count2);

            // 提交事务
            conn.commit();
        } catch (Exception e) {
            // 回滚事务
            conn.rollback();
        }



        // 7、释放资源
        stmt.close();
        conn.close();

    }
}
