# 一、数据库相关概念

## 1、数据库相关概念



> 数据库：

* 存储数据的仓库，数据是有组织的进行存储
* 英文：DataBase,简称DB

> 数据库管理系统：

* 管理数据库的大型软件
* 英文：DataBase Management System,简称DBMS

> SQL：

* 英文：Structured Query Language,简称SQL,结构化查询语言
* 操作关系型数据库的编程语言
* 定义操作所有关系型数据库的统一标准



## 2、MySQL数据模型

### 2.1 关系型数据库

* 关系型数据库是建立在关系模型基础上的数据库，简单说，关系型数据库是由多张能互相连接的二维表组成的数据库
* 优点：
  * 1、都是使用表结构，格式一致，易于维护。
  * 2、使用通用的SQL语言操作，使用方便，可用于复杂查询
  * 3、数据存储在磁盘中，安全。



# 二、SQL

## 1、SQL简介

* 英文：Structured Query Language,简称SQL
* 结构化查询语言，一门操作关系型数据库的编程语言
* 定义操作所有关系型数据库的统一标准
* 对于同一个需求，每一种数据库操作的方式可能会存在一些不一样的地方，我们称为“方言”

## 2、SQL通用语法

* 1.SQL语句可以单行或多行书写，以分号结尾。
* 2.MySQL数据库的SQL语句不区分大小写，关键字建议使用大写。
* 3.注释
  * 单行注释：一 注释内容 或 #注释内容（MySQL特有）
  * 多行注释：/* 注释体 */

### 3、SQL分类

* DDL(Data Definition Language)数据定义语言，用来定义数据库对象：数据库，表，列等
* **DML(Data Manipulation Language)数据操作语言，用来对数据库中表的数据进行增删改**
* **DQL(Data Query Language))数据查询语言，用来查询数据库中表的记录（数据）**
* DCL(Data Control Language)数据控制语言，用来定义数据库的访问权限和安全级别，及创建用户

![](https://pic1.imgdb.cn/item/6364b1eb16f2c2beb147d98c.jpg)



# 三、DDL-操作数据库、表

## 1、DDL-操作数据库

### 1.1 查询

* SHOW DATABASES;

### 1.2 创建

* 创建数据库
  * CREATE DATABASE 数据库名称；
* 创建数据库（判断，如果不存在则创建）
  * CREARE DATABASE IF NOT EXISTS 数据库名称；

### 1.3 删除

* 删除数据库
  * DROP DATABASE 数据库名称；
* 删除数据库（判断，如果存在则删除）
  * DROP DATABASE IF EXISTS 数据库名称；

### 1.4 使用数据库

* 查看当前使用的数据库
  * SELECT DATABASE()；
* 使用数据库
  * USE 数据库名称；



## 2、DDL-操作表

### 2.1 创建（Create）

* 创建表

  * CREATE TABLE 表名（

    ​			字段名1	数据类型1，

    ​			字段名2	数据类型2，

    ​			...

    ​			字段名n	数据类型n

    ）;

    **==注意：最后一行末尾，不能加逗号。==**

> 案例：创建tb_user表

![](https://pic1.imgdb.cn/item/6364b7f116f2c2beb150623e.jpg)

* 代码：

  ```mysql
  mysql> create table tb_user (
      -> id int,
      -> username varchar(20),
      -> password varchar(32)
      -> );
  ```

* 运行结果：

  ![](https://pic1.imgdb.cn/item/6364b84b16f2c2beb150e891.jpg)

### 2.2 查询（Retrieve）

* 查询当前数据库下所有表名称
  * SHOW TABLES；
* 查询表结构
  * DESC 表名称；

### 2.3 MySQL数据类型

> MySQL支持多种数据类型，可以分为三类：

* 数值
  * age int ;
  * score double (总长度，小数点后保留的位数)     成绩0-100，小数点后2位
    * score double(5,2)；

* 日期
  * birthday date；
* 字符串
  * 同时存储“张三”
  * 定长字符串char（以空间换时间）
    * name char(10)	
    * 占用10个字符空间	存储性能高	浪费空间
  * 变长字符串varchar（以时间换空间）
    * name varchar(10)
    * 占用2个字符空间      存储性能低    节约空间

> 数据类型表：

![](https://pic1.imgdb.cn/item/6364b8ff16f2c2beb152a4f7.jpg)

> 案例:

* 需求：设计一个学生表，注意数据类型和长度的合理性
  * 1. 编号
  * 2. 姓名，姓名最长不超过10个汉字
  * 3. 性别，因为取值只有两种可能，因此最多一个汉字
  * 4. 生日，取值为年月日
  * 5. 入学成绩，小数点后保留两位
  * 6. 邮件地址，最大长度不超过64
  * 7. 家庭联系电话，不一定是手机号码，可能会出现-等字符
  * 8. 学生状态（用数字表示，正常、休学、毕业...）

* 代码：

  ```mysql
  mysql> create table student(
      -> id int,
      -> name varchar(10),
      -> gender char(1),
      -> birthday date,
      -> score double(5,2),
      -> emial varchar(64),
      -> tel varchar(15),
      -> status tinyint
      -> );
  ```

* 运行结果：

  ![](https://pic1.imgdb.cn/item/6364be9116f2c2beb15c8c3f.jpg)



### 2.4 删除（Delete）

* 1、删除表
  * DROP TABLE 表名；
* 2、删除表时判断表是否存在；
  * DROP TABLE IF EXISTS 表名；

### 2.5 修改（Update）

* 1、修改表名
  * ALTER TABLE 表名 RENAME TO 新的表名；
* 2、添加一列
  * ALTER TABLE 表名 ADD 列名 数据类型；
* 3.修改数据类型
  * ALTER TABLE 表名 MODIFY 列名 新的数据类型；
* 4、修改列名和数据类型
  * ALTER TABLE 表名 CHANGE 列名 新列名 新数据类型；
* 5、删除列
  * ALTER TABLE 表名 DROP 列名；



# 四、DML-对表中数据进行增删改

## 1、添加（insert）

### 1.1 给指定的列添加数据

* INSERT INTO 表名(列名1，列名2,...) VALUES(值1，值2，...)；

### 1.2 给全部列添加数据

* INSERT INTO 表名 VALUES(值1，值2，...)；

### 1.3 批量添加数据

* INSERT INTO 表名(列名1，列名2，...) VALUES(值1，值2，...)，(值1，值2，...)，...；
* INSERT INTO 表名VALUES(值1，值2，...)，(值1，值2，...)，(值1，值2，...)，...；



## 2、修改（update）

> 修改表数据

* UPDATE 表名 SET 列名1=值1，列名2=值2，...[WHERE 条件]；

## 3、删除（delete）

> 删除数据

* DELETE FROM 表名 [WHERE 条件]；



# 五、DQL-查询数据库

## 1、查询语法

![](https://pic1.imgdb.cn/item/6364c9ca16f2c2beb170c2d5.jpg)

## 2、基础查询

> 1、查询多个字段

* SELECT 字段列表 FROM 表名；
* SELECT * FROM表名；--查询所有数据

> 2、去除重复记录

* SELECT DISTINCT 字段列表 FROM 表名；

> 3、起别名

* AS: AS 也可以省略

> 代码实现：

```mysql
-- 基础查询================

-- 查询name age 两列
SELECT name,age FROM stu;

-- 查询所有列的数据，列名的列表可以用*代替
SELECT * FROM stu;

-- 查询地址信息
SELECT address FROM stu;

-- 去除重复记录
SELECT DISTINCT address FROM stu;

SELECT * FROM stu;

-- 查询姓名，数学成绩和英语成绩
SELECT name,math as 数学成绩,english as 英语成绩 FROM stu;
```



## 3、条件查询

### 3.1 条件查询语法

* SELECT 字段列表 FROM 表名 WHERE 条件列表；

### 3.2 条件

![](https://pic1.imgdb.cn/item/6364d49416f2c2beb1805fca.jpg)

### 3.3 代码实现

```mysql
-- 条件查询 ==============
-- 1、查询年龄大于20岁的学员信息
SELECT * FROM stu WHERE age > 20;

-- 2、查询年龄大于等于20岁的学员信息
SELECT * FROM stu WHERE age >= 20;

-- 3、查询年龄大于等于20岁的学员信息 并且 年龄小于等于30的学员信息
SELECT * FROM stu WHERE age >= 20 AND age<=30;

SELECT * FROM stu WHERE age BETWEEN 20 AND 30;

-- 4、查询入学日期在‘1998-09-01’到‘1999-09-01’之间的学员信息
SELECT * FROM stu WHERE hire_date BETWEEN '1998-09-01' AND '1999-09-01';

-- 5、查询年龄等于18岁的学员信息
SELECT * FROM stu WHERE age = 18 ;

-- 6、查询年龄不等于18岁的学员信息
SELECT * FROM stu WHERE age != 18;
SELECT * FROM stu WHERE age <> 18;

-- 7、查询年龄等于18 或者 年龄等于20 或者 年龄等于22岁的学员信息
SELECT * FROM stu WHERE age = 18 OR age = 20 OR age = 22 ;
SELECT * FROM stu WHERE age in(18, 20 ,22);

-- 8、查询英语成绩为null的学员信息
-- 注意：null值的比较爱哦啊不能使用 = ，！= 。需要使用 is，is not；
 SELECT * FROM stu WHERE english is null;
 
 SELECT * FROM stu WHERE english IS NOT null;
 
 -- 模糊查询 like ==========
 /*
		通配符：
		（1）_:代表单个任意字符
		（2）%:代表任意个数字符
 */
 -- 1、查询姓马的学员信息
 SELECT * FROM stu WHERE name like '马%';
 -- 2、查询第二个字是化的学员信息
 SELECT * FROM stu WHERE name LIKE '_化%';
 -- 3、查询名字中包含德的学员信息
 SELECT * FROM stu WHERE name LIKE '%德%';

```



## 4、排序查询

### 4.1 排序查询语法

* SELECT 字段列表 FROM 表名 ORDER BY 排序字段名1[排序方式1]，排序字段名2[排序方式2]...；
* 排序方式：
  * ASC：升序排列（默认值）
  * DESC：降序排列

* **注意：如果有多个排序条件，当前边的条件值一样时，才会根据第二条件进行排序**

### 4.2 代码实现

```mysql
-- 排序查询 =========================

-- 1、查询学生信息，按照年龄升序排列

SELECT * FROM stu ORDER BY age ASC;
SELECT * FROM stu ORDER BY age;

-- 2、查询学生信息，按照数学成绩降序排列
SELECT * FROM stu ORDER BY math DESC;

-- 3、查询学生信息，按照数学成绩降序排列，如果数学成绩一样，在按照英语成绩升序排列,如果英语成绩一样，按照年龄升序排列
SELECT * FROM stu ORDER BY math DESC , english ASC , age ASC;

```



## 5、分组查询

### 5.1 聚合函数

> 1、概念：

* 将一列数据作为一个整体，进行纵向计算。

> 2、聚合函数分类

| 函数名      | 功能                             |
| ----------- | -------------------------------- |
| count(列名) | 统计数量（一般选用不为null的列） |
| max(列名)   | 最大值                           |
| min(列名)   | 最小值                           |
| sum(列名)   | 求和                             |
| avg(列名)   | 平均值                           |

> 3、聚合函数语法：

* SELECT 聚合函数名（列名）FROM 表
* 注意：null值不参与所有聚合函数运算

> 代码实现：

```mysql
-- 聚合函数 =================

SELECT * FROM stu;
-- 1、统计班级一共多少个学生
SELECT COUNT(id) FROM stu; -- 列名不能为null
SELECT COUNT(english) FROM stu;
SELECT COUNT(*) FROM stu;

-- 2、查询数学成绩的最高分
SELECT MAX(math) FROM stu;

-- 3、查询数学成绩的最低分
SELECT MIN(math) FROM stu;

-- 4、查询数学成绩的总分
SELECT SUM(math) FROM stu;

-- 5、查询数学成绩的平均分
SELECT AVG(math) FROM stu;

-- 6、查询英语成绩的最低分
SELECT MIN(english) FROM stu;
```

### 5.2 分组查询

> 1、分组查询语法

* SELECT 字段列表 FROM 表名 [WHERE 分组前条件限定] GROUP BY分组字段名 [HAVING 分组后条件过滤]；

* 注意：分组之后，查询的字段为聚合函数和分组字段，查询其他字段无任何意义

> where 和 having 区别

* 执行时机不一样：where是分组之前进行限定，不满足where:条件，则不参与分组，而having是分组之后对结果进行过滤。
* 可判断的条件不一样：where不能对聚合函数进行判断，having可以。

* 执行顺序：where > 聚合函数 > having

> 代码实现：

```mysql
-- 分组查询 ====================

SELECT * FROM stu;

-- 1、查询男同学和女同学各自的数学平均分
SELECT sex,AVG(math) FROM stu GROUP BY sex;

-- 2、查询男同学和女同学各自的数学平均分，以及各自人数
SELECT sex,AVG(math),COUNT(*) FROM stu GROUP BY sex; 

-- 3、查询男同学和女同学各自的数学平均分，以及各自人数，要求：分数低于70分的不参与分组
SELECT sex,AVG(math),count(*) FROM stu WHERE math > 70 GROUP BY sex;

-- 4、查询男同学和女同学各自的数学平均分，以及各自人数，要求：分数低于70分的不参与分组，分组之后人数大于2

SELECT sex,AVG(math),count(*) FROM stu WHERE math > 70 GROUP BY sex HAVING count(*) > 2;


```



## 6、分页查询

### 6.1 分页查询语法

* SELECT  字段列表  FROM  表名  LIMIT  起始索引，查询条目数；

* 起始索引：从0开始
* 计算公式：起始索引 =（当前页码-1）* 每页显示的条数

> tips：

* 分页查询limit是MySQL数据库的方言
* Oracle分页查询使用rownumber
* SQL Server分页查询使用top

### 6.2 代码实现：

```mysql
-- 分页查询 ==============

SELECT * FROM stu;
-- 1、从0开始查询，查询三条数据
SELECT * FROM stu LIMIT 0 , 3;
-- 2、每页显示3条数据，查询第1页数据
SELECT * FROM stu LIMIT 0 , 3;
-- 3、每页显示3条数据，查询第2页数据
SELECT * FROM stu LIMIT 3 , 3;
-- 4、每页显示3条数据，查询第3页数据
SELECT * FROM stu LIMIT 6 , 3;

```

