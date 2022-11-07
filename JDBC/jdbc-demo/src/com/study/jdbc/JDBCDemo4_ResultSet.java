package com.study.jdbc;

import com.study.pojo.Account;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC快速入门
 */
public class JDBCDemo4_ResultSet {
    @Test
    public void testResultSet() throws Exception {
        // 获取连接：
        String url = "jdbc:mysql:///db1";
        String user = "root";
        String passWord = "123456";
        Connection conn = DriverManager.getConnection(url,user,passWord);

        // 定义sql
        String sql = "select * from account";

        // 获取statement对象
        Statement stmt = conn.createStatement();

        // 执行sql语句
        ResultSet rs = stmt.executeQuery(sql);

        // 处理结果
        // 光标向下移动一行，并且判断当前行是否有数据
//        while (rs.next()){
//            int id = rs.getInt(1);
//            String name = rs.getString(2);
//            double money = rs.getDouble(3);
//            System.out.print(id + "\t");
//            System.out.print(name + "\t");
//            System.out.println(money);
//            System.out.println("------------------------");
//        }
        while (rs.next()){
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double money = rs.getDouble("money");
            System.out.print(id + "\t");
            System.out.print(name + "\t");
            System.out.println(money);
            System.out.println("------------------------");
        }

        // 释放资源
        stmt.close();
        conn.close();
    }


    /**
     *  查询account账户表数据，封装为Account对象中，并且存储到ArrayList集合中
     * 1.走义实体类Account
     * 2.查询数据，封装到Account对象中
     * 3.将Account.对象存入ArrayList集合中
     */
    @Test
    public void testResultSet2() throws Exception {
        // 获取连接：
        String url = "jdbc:mysql:///db1";
        String user = "root";
        String passWord = "123456";
        Connection conn = DriverManager.getConnection(url,user,passWord);

        // 定义sql
        String sql = "select * from account";

        // 获取statement对象
        Statement stmt = conn.createStatement();

        // 执行sql语句
        ResultSet rs = stmt.executeQuery(sql);

        // 创建集合
        List<Account> list = new ArrayList<>();

        while (rs.next()){
            Account account = new Account();

            int id = rs.getInt("id");
            String name = rs.getString("name");
            double money = rs.getDouble("money");

            // 赋值
            account.setId(id);
            account.setName(name);
            account.setMoney(money);

            list.add(account);
        }
        System.out.println(list);

        // 释放资源
        stmt.close();
        conn.close();
    }

}
