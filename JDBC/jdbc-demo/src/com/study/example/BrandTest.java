package com.study.example;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.study.pojo.Brand;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *  品牌数据的增删改查操作
 */
public class BrandTest {



    /**
     *  查询所有
     *  1.SQL:select from tb_brand;
     * 2.参数：不需要
     * 3.结果：List<Brand
     */
    @Test
    public void testSelectAll()throws Exception{
        // 1、获取Connection对象
        Properties prop = new Properties(); // 加载配置文件
        prop.load(new FileInputStream("D:\\JDBC\\jdbc-demo\\src\\druid.properties"));
        // 获取连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        // 获取数据库连接 Connection
        Connection conn = dataSource.getConnection();

        // 2、定义SQL语句
        String sql = "select * from tb_brand";

        // 3、获取pstmt对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 4、设置参数

        // 5、执行sql
        ResultSet rs = pstmt.executeQuery();

        List<Brand> list = new ArrayList<>();
        // 6、处理结果 装在list集合
        while (rs.next()){
            // 获取数据
            int id = rs.getInt("id");
            String brandName = rs.getString("brand_name");
            String companyName = rs.getString("company_name");
            int ordered = rs.getInt("ordered");
            String description = rs.getString("description");
            int status = rs.getInt("status");
            // 封装Brand对象
            Brand brand = new Brand();
            brand.setId(id);
            brand.setBrandName(brandName);
            brand.setCompanyName(companyName);
            brand.setOrdered(ordered);
            brand.setDescription(description);
            brand.setStatus(status);
            // 装载集合
            list.add(brand);
        }
        System.out.println(list);
        // 7、释放资源
        rs.close();
        pstmt.close();
        conn.close();
    }


    /**
     *  添加
     *  1.SQL:insert into tb_brand
     * 2.参数：需要,除了id之外所有参数信息
     * 3.结果：boolean
     */
    @Test
    public void testAdd()throws Exception{
        // 接受页面信息
        String brandName = "香飘飘";
        String companyName = "香飘飘奶茶有限公司";
        int ordered = 1;
        String description = "绕地球一圈";
        int status =1;

        // 1、获取Connection对象
        Properties prop = new Properties(); // 加载配置文件
        prop.load(new FileInputStream("D:\\JDBC\\jdbc-demo\\src\\druid.properties"));
        // 获取连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        // 获取数据库连接 Connection
        Connection conn = dataSource.getConnection();

        // 2、定义SQL语句
        String sql = "insert into tb_brand(brand_name,company_name,ordered,description,status) values(?,?,?,?,?)";

        // 3、获取pstmt对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 4、设置参数
        pstmt.setString(1,brandName);
        pstmt.setString(2,companyName);
        pstmt.setInt(3,ordered);
        pstmt.setString(4,description);
        pstmt.setInt(5,status);

        // 5、执行sql
        int count = pstmt.executeUpdate(); // 影响的行数

        // 6、处理结果
        System.out.println(count > 0);

        // 7、释放资源
        pstmt.close();
        conn.close();
    }



    /**
     *  修改
     *  1.SQL:update tb_brand set
     * 2.参数：所有参数信息
     * 3.结果：boolean
     */
    @Test
    public void testUpdate()throws Exception{
        // 接受页面信息
        String brandName = "香飘飘";
        String companyName = "香飘飘奶茶有限公司";
        int ordered = 1000;
        String description = "绕地球三圈";
        int status =1;
        int id = 4;

        // 1、获取Connection对象
        Properties prop = new Properties(); // 加载配置文件
        prop.load(new FileInputStream("D:\\JDBC\\jdbc-demo\\src\\druid.properties"));
        // 获取连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        // 获取数据库连接 Connection
        Connection conn = dataSource.getConnection();

        // 2、定义SQL语句
        String sql = "" +"update tb_brand set brand_name = ?,company_name = ?,ordered = ?,description = ?,status=? where id = ?";

        // 3、获取pstmt对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 4、设置参数
        pstmt.setString(1,brandName);
        pstmt.setString(2,companyName);
        pstmt.setInt(3,ordered);
        pstmt.setString(4,description);
        pstmt.setInt(5,status);
        pstmt.setInt(6,id);

        // 5、执行sql
        int count = pstmt.executeUpdate(); // 影响的行数

        // 6、处理结果
        System.out.println(count > 0);

        // 7、释放资源
        pstmt.close();
        conn.close();
    }




    /**
     *  删除
     *  1.SQL:delete from tb_brand where id = ?;
     * 2.参数：id
     * 3.结果：boolean
     */
    @Test
    public void testDelete()throws Exception{
        // 接受页面信息
        int id = 4;

        // 1、获取Connection对象
        Properties prop = new Properties(); // 加载配置文件
        prop.load(new FileInputStream("D:\\JDBC\\jdbc-demo\\src\\druid.properties"));
        // 获取连接池对象
        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);
        // 获取数据库连接 Connection
        Connection conn = dataSource.getConnection();

        // 2、定义SQL语句
        String sql = "delete from tb_brand where  id = ?";

        // 3、获取pstmt对象
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // 4、设置参数
        pstmt.setInt(1,id);

        // 5、执行sql
        int count = pstmt.executeUpdate(); // 影响的行数

        // 6、处理结果
        System.out.println(count > 0);

        // 7、释放资源
        pstmt.close();
        conn.close();
    }

}
