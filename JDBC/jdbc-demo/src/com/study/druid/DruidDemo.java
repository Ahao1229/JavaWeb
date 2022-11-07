package com.study.druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 *  Druid数据库连接池演示
 */
public class DruidDemo {
    public static void main(String[] args) throws Exception {
        // 加载配置文件
        Properties prop = new Properties();
        prop.load(new FileInputStream("jdbc-demo/src/druid.properties"));
        // 获取连接池对象
        DataSource  dataSource= DruidDataSourceFactory.createDataSource(prop);

        // 获取数据库连接
        Connection connection = dataSource.getConnection();

        System.out.println(connection);
    }
}
