# 一、JDBC 快速入门

## 1、 JDBC简介

### 1.1 JDBC概念

* JDBC就是使用)Java语言操作关系型数据库的一套API
* 全称：( Java DataBase Connectivity ) Java数据库连接

### 1.2 JDBC本质：

* 官方(sun公司)定义的一套操作所有关系型数据库的规则，即接口
* 各个数据库厂商去实现这套接口，提供数据库驱动jar包
* 我们可以使用这套接口 ( JDBC ) 编程，真正执行的代码是驱动jar包
  中的实现类

![](https://pic1.imgdb.cn/item/6368770f16f2c2beb187c702.jpg)

## 2、JDBC 快速入门

### 2.1 步骤

![](https://pic1.imgdb.cn/item/6368816a16f2c2beb195d332.jpg)

### 2.2 代码实现

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * JDBC快速入门
 */
public class JDBCDemo {
    public static void main(String[] args) throws Exception {
        // 1、注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2、获取连接
        String url = "jdbc:mysql://localhost:3306/db1";
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
```

* **注意：运行前必须先打开navicat连接，并打开db1数据库**

### 2.3 运行结果

![](https://pic1.imgdb.cn/item/6368820016f2c2beb196fe40.jpg)



# 二、JDBC API 详情

## 1、DriverManager

* DriverManager（驱动管理类）作用：
  * 1、注册驱动
  * 2、获取数据库连接

### 2.1 注册驱动

* Class.forName("com.mysql.jdbc.Driver");
* 提示：
  * MySQL5之后的驱动包，可以省略注册驱动的步骤
    自动加载jar包中META-lNF/services/java.sql.Driver文件中的驱动类

### 2.1 获取连接

* static Connection

* Connection coon = getConnection(String url, String user, String password)
* 参数：
  * 1、url：连接路径
    * 语法：jdbc:mysql://ip地址（域名）：端口号/数据库名称？参数键值对1&参数键值对2..
    * 示例：jdbc:mysql://127.0.0.1:3306/db1
    * 细节：
      * 如果连接的是本机mysql服务器，并且mysql服务默认端口是3306，则url可以简写为：jdbc:mysql:://数据库名称？参数键值对
      * 配置useSSL=false参数，禁用安全连接方式，解决警告提示
  * 2、user：用户名
  * 3、password：密码

​		

## 2、Connection

* Connection（数据库连接对象）作用：
  * 1、获取执行SQL的对象
  * 2、管理事务

### 2.1 获取执行SQL的对象

* 普通执行SQL对象
  * Statement createStatement()
* 预编译SQL的执行SQL对象：防止SQL注入
  * PreparedStatement preparedStatement(sql)
* 执行存储过程的对象
  * CallableStatement prepareCall(sql)

### 2.2 事务管理

* mysql事务管理
  * 开启事务：BEGIN;/START TRANSACTION;
  * 提交事务：COMMIT:
  * 回滚事务：ROLLBACK:
  * MySQL默认自动提交事务

* JDBC事务管理：Connection接口中定义了3个对应方法
  * 开启事务：setAutoCommit(boolean autoCommit):true为自动提交事务；false为手动提交事务，即为开启事务
  * 提交事务：commit()
  * 回滚事务：rollback()

```java
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
```



## 3、Statement

* Statement作用：
  * 执行SQL语句
* 执行SQL语句：
  * int executeUpdate(sql) : 执行DML、DDL语句
    * 返回值：
      * (1)DML语句影响的行数
      * (2)DDL语句执行后，执行成功也可能返回0 
  * ResultSet executeQuery(sql):执行DQL语句
    * 返回值：
      * ResultSet结果集对象

> excuteUpdate 代码实现：

```java
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
```



## 4、 ResultSet

### 4.1 ResultSet 概述

* ResultSet(结果集对象)作用：
  * 封装了DQL查询语句的结果：
  * ResultSet stmt.executeQuery(sql):执行DQL语句，返回ResultSet对象

* 获取查询结果
  * boolean next():
    * (1)将光标从当前位置向前移动一行
    * (2)判断当前行是否为有效行
    * 返回值：
      * true:有效行，当前行有数据
      * false:无效行，当前行没有数据
  * xxx getXxx(参数)：获取数据
    * xxx:数据类型；如：int getint(参数)；String getString(参数)
    * 参数：
      * int：列的编号，从1开始
      * String：列的名称

> 使用步骤：

![](https://pic1.imgdb.cn/item/6368adf216f2c2beb1dab5b9.jpg)

> 代码实现：

```java
import org.junit.Test;

import java.sql.*;

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

}
```



### 4.2 ResultSet 案例

* 需求：
  * 查询account账户表数据，封装为Account对象中，并且存储到ArrayList集合中

* 代码实现：

```java
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
```



## 5、PreparedStatement

* PreparedStatement作用：
  * 预编译SQL语句并执行：预防SQL注入问题
* SQL注入:
  * SQL注入是通过操作输入来修改事先定义好的SQL语句，用以达到执行代码对服务器进行**攻击**的方法。

### 5.1 SQL 注入演示

> 需求：完成用户登录

```java
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
```

> 为什么会实现这种情况？

* 输入的sql语句实际上是：select * from tb_user where username = 'hfkls' and password = '' or '1' = '1'
* 查询的判断条件实际上是：username = 'hfkls' and password = '' or '1' = '1'
* username = ‘ ’ and password = ‘ ’ 正常来说必须都要为真才可以满足条件进行查询但是进行注入之后，就会变成username=一个用户随意输入即使假的也可以，密码会被识别成空字符串，这个时候and虽然判断为假，但是输入的or会被认为成判断条件，1=1恒为真，所以会被认为是满足了查询条件，就会被查询

### 5.2 SQL注入如何解决

> 使用 PreparedStatement 解决

* PreparedStatement作用：
  * 预编译SQL并执行SQL语句

![](https://pic1.imgdb.cn/item/6368bb3d16f2c2beb1ecb9bc.jpg)

> 代码实现：

```java
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

}
```

### 5.3 PreparedStatement 原理

* PreparedStatement 好处：
  * 1、预编译SQL，性能更高
  * 2、防止SQL注入：将敏感字符进行转义

> 理解预编译：

![](https://pic1.imgdb.cn/item/6368c19e16f2c2beb1f84f44.jpg)

* 只会预编译一次

![](https://pic1.imgdb.cn/item/6368c15d16f2c2beb1f805aa.jpg)

* PreparedStatement原理：
  * 1.在获取PreparedStatement对象时，将sql语句发送给mysql服务器进行检查，编译（这些步骤很耗时）
  * 2.执行时就不用再进行这些步骤了，速度更快
  * 3.如果sql模板一样，则只需要进行一次检查、编译



# 三、数据库连接池

## 1、数据库连接池简介

### 1.1 什么是数据库连接池

* 数据库连接池是个容器，负责分配、管理数据库连接(Connection)
* 它允许应用程序重复使用一个现有的数据库连接，而不是再重新建立一个；
* 释放空闲时间超过最大空闲时间的数据库连接来避免因为没有释放数据库连接而引起的数据库连接遗漏

> 数据库连接池有好处

* 资源重用
* 提升系统响应速度
* 避免数据库连接遗漏

### 1.2 数据库连接池实现

* 标准接口：DataSource
  * 官方(SUN)提供的数据库连接池标准接口，由第三方组织实现此接口。
  * 功能：获取连接
  * Connection getConnection()
* 常见的数据库连接池：
  * DBCP
  * C3PO
  * Druid
* Druid(德鲁伊)
  * Druid连接池是阿里巴巴开源的数据库连接池项目
  * 功能强大，性能优秀，是Java语言最好的数据库连接池之一

### 1.3 Driud使用步骤

* 1.导入jar包druid-1.1.12jar
* 2.定义配置文件
* 3.加载配置文件
* 4.获取数据库连接池对象
* 5.获取连接

### 1.4 代码实现

> 配置文件 druid.properties ：

```
diverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql:///db1?userSSL=false&useServerPrepStmts=true
username=root
password=123456
# 初始化连接数量
initialSize=5
# 最大连接数
maxActive=10
# 最大等待时间
maxWait=3000
```

> 测试类：

```java
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
```



## 2、JDBC练习

> 练习：完成商品品牌数据的增删改查操作

* 查询：查询所有数据
* 添加：添加品牌
* 修改：根据id修改
* 删除：根据id删除

### 2.1 准备环境

* 数据库表tb_brand

```mysql
DROP TABLE IF EXISTS tb_brand;
CREATE TABLE tb_brand(
	id INT PRIMARY KEY auto_increment,	-- id
	brand_name VARCHAR(20),							-- 品牌名称
	company_name VARCHAR(20),						-- 厂家名称
	ordered INT,												-- 排序字段
	description VARCHAR(100),						-- 描述信息
	status INT													-- 状态：0禁用，1启用
)ENGINE = INNODB;

INSERT INTO tb_brand (brand_name,company_name,ordered,description,status) VALUES
('三只松鼠','三只松鼠股份有限公司',5,'好吃不上火',0),
('华为','华为技术有限公司',100,'不买华为不爱国',1),
('小米','小米科技有限公司',50,'小米为发烧而生',1);

SELECT * FROM tb_brand;
```

* 实体类Brand

```java
/**
 * 品牌
 * alt+鼠标左键：整列编辑
 * 在实体类中，基本数据类型建议使用其对应的包装类型
 */
public class Brand {
    private Integer id;  // id
    private String brandName;  // 品牌名称
    private String companyName;    // 厂家名称
    private Integer ordered; // 排序字段
    private String description; // 描述信息
    private Integer status;      // 状态：0禁用，1启用

    public Brand() {
    }

    public Brand(Integer id, String brandName, String companyName, Integer ordered, String description, Integer status) {
        this.id = id;
        this.brandName = brandName;
        this.companyName = companyName;
        this.ordered = ordered;
        this.description = description;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getOrdered() {
        return ordered;
    }

    public void setOrdered(Integer ordered) {
        this.ordered = ordered;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", ordered=" + ordered +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
```

* 测试用例

```java
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

}
```



### 2.2 完成商品的增删改查操作

> 需求：

- 查询：查询所有数据
- 添加：添加品牌
- 修改：根据id修改
- 删除：根据id删除

> 代码实现：

* 查询：

```java
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
```

* 添加：

```java
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
```

* 修改：

```java
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
```

* 删除：

```java
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
```