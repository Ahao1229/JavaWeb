# 一、约束

## 1、约束的概述

### 1.1 约束的概念

* 约束是作用于表中列上的规则，用于限制加入表的数据
* 约束的存在保证了数据库中数据的正确性、有效性和完整性

### 1.2 约束的分类

| 约束名称 | 描述                                                         | 关键字      |
| -------- | ------------------------------------------------------------ | ----------- |
| 非空约束 | 保证列中所有数据不能有null值                                 | NOT NULL    |
| 唯一约束 | 保证列中所有数据各不相同                                     | UNIQUE      |
| 主键约束 | 主键是一行数据的唯一标识，要求非空目唯一                     | PRIMARY KEY |
| 检查约束 | 保证列中的值满足某一条件                                     | CHECK       |
| 默认约束 | 保存数据时，未指定值则采用默认值                             | DEFAULT     |
| 外键约束 | 外键用来让两个表的数据之间建立链接，保证数据的一致性和完整性 | FOREIGN KEY |

> tips：MySQL不支持检查约束



## 2、约束案例

### 2.1 根据需求，为表添加约束

![](https://pic1.imgdb.cn/item/6364e9f516f2c2beb1a613b9.jpg)

### 2.2 代码实现：

```mysql
DROP TABLE IF EXISTS emp;

-- 员工表
CREATE TABLE emp(
	id INT PRIMARY KEY auto_increment, -- 员工id，主键且自增长
	ename VARCHAR(50) NOT NULL UNIQUE, -- 员工姓名，非空且唯一
	joindate DATE NOT NULL, -- 入职日期，非空
	salary DOUBLE(7,2) NOT NULL, -- 工资，非空
	bonus DOUBLE(7,2) DEFAULT 0 -- 奖金，如果没有默认为0
	);

INSERT INTO emp(id,ename,joindate,salary,bonus) VALUES(1,'张三','1999-11-11',8800,5000);

-- 演示主键约束：非空且唯一
INSERT INTO emp(id,ename,joindate,salary,bonus) VALUES(NULL,'张三','1999-11-11',8800,5000);

INSERT INTO emp(id,ename,joindate,salary,bonus) VALUES(1,'张三','1999-11-11',8800,5000);

INSERT INTO emp(id,ename,joindate,salary,bonus) VALUES(2,'李四','1999-11-11',8800,5000);

-- 演示非空约束

INSERT INTO emp(id,ename,joindate,salary,bonus) VALUES(3,NULL,'1999-11-11',8800,5000);

-- 演示唯一约束
INSERT INTO emp(id,ename,joindate,salary,bonus) VALUES(3,'李四','1999-11-11',8800,5000);

-- 演示默认约束
INSERT INTO emp(id,ename,joindate,salary) VALUES(3,'王五','1999-11-11',8800);

INSERT INTO emp(id,ename,joindate,salary,bonus) VALUES(4,'赵六','1999-11-11',8800,NULL);


-- 演示自动增长：auto_increment：当列是数字类型，并且唯一约束
INSERT INTO emp(ename,joindate,salary,bonus) VALUES('熊大','1999-11-11',8800,5000);

INSERT INTO emp(id,ename,joindate,salary,bonus) VALUES(NULL,'熊二','1999-11-11',8800,0);


SELECT * FROM emp;
```

### 2.3 外键约束

> 1、概念

* 外键用来让两个表的数据之间建立链接，保证数据的一致性和完整性

> 2、语法

* 添加约束

  * 创建表时添加外键约束

    CREATE TABLE表名(

    列名 数据类型，

    ...

    [CONSTRAINT] [外键名称] FOREIGN KEY (外键列名) REFERENCES 主表（主表列名）

    );

  * 建完表后添加外键约束

    ALTER TABLE 表名 ADD CONSTRAINT 外键名称 FOREIGN KEY (外键字段名称) REFERENCES 主表名称（主表列名称）;

* 删除约束

  * ALTER TABLE 表名 DROP FOREIGN KEY 外键名称；

> 代码实现：

```mysql
DROP TABLE IF EXISTS dept;
DROP TABLE IF EXISTS emp;

-- 部门表
CREATE TABLE dept(
	id int PRIMARY KEY auto_increment,
	dept_name VARCHAR(20),
	addr VARCHAR(20)
);

-- 员工表
CREATE TABLE emp(
	id int PRIMARY KEY auto_increment,
	name VARCHAR(20),
	age int,
	dept_id int,
	-- 添加外键 dept_id，关联dept表的id主键
	CONSTRAINT fk1 FOREIGN KEY(dept_id) REFERENCES dept(id)
);

-- 添加2个部门
INSERT into dept(dept_name,addr) VALUES('研发部','深圳'),('销售部','广州');

-- 添加员工
INSERT INTO emp(name,age,dept_id) VALUES
('张三',20,1),
('李四',20,1),
('王五',20,1),
('赵六',20,2),
('孙七',20,2),
('周八',20,2);

SELECT * FROM dept;
SELECT * FROM emp;

-- 删除外键
ALTER TABLE emp DROP FOREIGN KEY fk1;
-- 建完表之后添加外键约束
ALTER TABLE emp add CONSTRAINT fk_emp_dept FOREIGN KEY(dept_id) REFERENCES dept(id);
```



# 二、数据库设计

## 1、数据库设计简介

### 1.1 软件的研发步骤

![](https://pic1.imgdb.cn/item/6366092416f2c2beb167dab7.jpg)

### 1.2 数据库设计概念

* 数据库设计就是根据业务系统的具体需求，结合我们所选用的DBMS,为这个业务系统构造出最优的数据存储模型。
* 建立数据库中的**表结构**以及**表与表之间的关联关系**的过程。
* 有哪些表？表里有哪些字段？表和表之间有什么关系？

### 1.3 数据库设计的步骤

* ① 需求分析（数据是什么？数据具有哪些属性？数据与属性的特点是什么）
* ② 逻辑分析（通过ER图对数据库进行逻辑建模，不需要考虑我们所选用的数据库管理系统）
* ③ 物理设计（根据数据库自身的特点把逻辑设计转换为物理设计）
* ④ 维护设计(1对新的需求进行建表；2表优化)

### 1.4 表关系

> **一对一：**

* 如：用户 和 用户详情
* 一对一关系多用于表的拆分，将一个实体中经常使用的字段放一张表，不经常使用的字段放另一张表，用于提升查询性能

> **一对多（多对一）：**

* 如：部门 和 员工
* 一个部门对于多个员工，一个员工对应一个部门

> **多对多：**

* 如：商品 和 订单
* 一个商品对应多个订单，一个订单包含多个商品

### 1.5 总结

* 1、数据库设计设计什么？
  * 有哪些表
  * 表里有哪些字段
  * 表和表之间是什么关系

* 2、表关系有哪几种？
  * 一对一
  * 一对多（多对一）
  * 多对多



## 2、多表关系实现

### 2.1 一对多

* 一对多

  * 如：部门 和 员工

  * 一个部门对于多个员工，一个员工对应一个部门

* 实现方式：
  * 在多的一方建立外键，指向一的一方的主键

### 2.2 多对多

* 多对多：
  * 商品 和 订单
  * 一个商品对应多个订单，一个订单包含多个商品
* 实现方式：
  * 建立第三张**中间表**，中间表至少包含**两个外键**，分别管理**两方主键**

![](https://pic1.imgdb.cn/item/63660d1516f2c2beb187ac8c.jpg)

* 代码实现

```mysql
-- 删除表
DROP TABLE IF EXISTS tb_order_goods;
DROP TABLE IF EXISTS tb_order;
DROP TABLE IF EXISTS tb_goods;

-- 订单表
CREATE TABLE tb_order(
	id int primary key auto_increment,
	payment DOUBLE(10,2),
	payment_type TINYINT,
	status TINYINT
)ENGINE = INNODB;

-- 商品表
CREATE TABLE tb_goods(
	id int PRIMARY key auto_increment,
	title VARCHAR(100),
	price DOUBLE(10,2)
)ENGINE = INNODB;

-- 订单商品中间表
CREATE TABLE tb_order_goods(
	id int PRIMARY key auto_increment,
	order_id int,
	goods_id int,
	count INT
)ENGINE = INNODB;

-- 建完表之后，添加外键
alter table tb_order_goods add CONSTRAINT fk_order_id FOREIGN KEY(order_id) REFERENCES tb_order(id);

alter table tb_order_goods add CONSTRAINT fk_goods_id FOREIGN KEY(goods_id) REFERENCES tb_goods(id);
```

### 2.3 一对一

* 一对一：
  * 如：用户 和 用户详情
  * 一对一关系多用于表的拆分，将一个实体中经常使用的字段放一张表，不经常使用的字段放另一张表，用于提升查询性能
* 实现方式：在任意一方加入外键，并且设置外键为**唯一（UNIQUE）**

![](https://pic1.imgdb.cn/item/636612c516f2c2beb1b0e0db.jpg)

* 代码实现：

```mysql
-- 删除表
DROP TABLE IF EXISTS tb_user;
DROP TABLE IF EXISTS tb_user_desc;

-- 创建用户表
CREATE TABLE tb_user(
	id int PRIMARY KEY auto_increment,
	nickname VARCHAR(10),
	age int,
	gender VARCHAR(4),
	desc_id int UNIQUE
) ENGINE = INNODB;

-- 创建用户详情表
CREATE TABLE tb_user_desc(
	id int PRIMARY KEY auto_increment,
	city VARCHAR(15),
	edu VARCHAR(4),
	income DOUBLE(9,2),
	status VARCHAR(2)
) ENGINE = INNODB;

-- 建完表之后，添加外键
ALTER TABLE tb_user	ADD CONSTRAINT fk_user_desc FOREIGN KEY(desc_id) REFERENCES tb_user_desc(id);
```

### 2.4 总结

* 1、一对多实现方式
  * 在多的一方建立外键关联一的一方主键
* 2、多对多实现方式
  * 建立第三张中间表
  * 中间表至少包含2个外键，分别关联双方主键
* 3、一对一实现方式
  * 在任意一方建立外键，关联对方主键，并设置外键唯一



## 3、数据库实现案例分析

![](https://pic1.imgdb.cn/item/636617c216f2c2beb1c0d772.jpg)



# 三、多表查询

## 1、多表查询概述

* 笛卡尔积：取A,B集合所有组合情况

* 多表查询：从多张表查询数据

  * 连接查询：

    * 内连接：
      * 相当于查询A B交集数据

    * 外连接：
      * 左外连接：相当于查询A表所有数据和交集部分数据
      * 右外连接：相当于查询B表所有数据和交集部分数据

  * 子查询

> 创建用户和部门表：

```mysql
DROP TABLE IF EXISTS emp;
DROP TABLE IF EXISTS dept;

#创建部门表
CREATE TABLE dept(
	did INT PRIMARY KEY auto_increment,
	dname VARCHAR(20)
) ENGINE = INNODB;

#创建员工表
CREATE TABLE emp(
	id INT PRIMARY KEY auto_increment,
	name VARCHAR(10),
	gender CHAR(1),
	salary DOUBLE,
	join_date date,
	dept_id int,
	FOREIGN key(dept_id) REFERENCES dept(did)
) ENGINE = INNODB;

#添加部门数据
INSERT INTO dept(dname) VALUES ('研发部'),('市场部'),('财务部'),('销售部');

#添加员工数据
INSERT INTO emp(name,gender,salary,join_date,dept_id) VALUES ('孙悟空','男','7200','2013-02-24',1),
('猪八戒','男','3600','2010-12-02',2),
('唐三藏','男','9000','2008-08-08',2),
('白骨精','女','5000','2015-10-07',3),
('沙和尚','男','5000','2008-10-08',3),
('蜘蛛精','女','4500','2011-03-14',1),
('小白龙','男','2500','2011-02-14',NULL);


SELECT * FROM emp;

SELECT * FROM dept;
```



## 2、内连接

### 2.1 内连接查询语法

* 隐式内连接
  * SELECT   字段列表   FROM   表1，表2....   WHERE   条件；
* 显式内连接
  * SELECT   字段列表   FROM   表1   [INNER]   JOIN   表2   ON   条件；

* 内连接相当于查询A   B交集数据

### 2.2 代码实现：

```mysql
-- 多表查询
SELECT * FROM emp , dept;

-- 会产生笛卡尔积：有A,B两个集合，取AB所有组合情况

-- 消除无效数据
-- 隐式内连接
SELECT * FROM emp , dept WHERE emp.dept_id = dept.did;

-- 查询emp的name，gander，dept表的dname；
SELECT emp.name,emp.gender,dept.dname FROM emp,dept WHERE emp.dept_id = dept.did;

-- 给表起别名
SELECT t1.name,t1.gender,t2.dname FROM emp as t1,dept as t2 WHERE t1.dept_id = t2.did;



-- 显式内连接
SELECT * FROM emp INNER JOIN dept on emp.id = dept.did;
SELECT * FROM emp JOIN dept on emp.id = dept.did;
```



## 3、外连接

### 3.1 外连接查询语法

* 左外连接
  * SELECT   字段列表   FROM   表1   LEFT   [OUTER]   JOIN   表2   ON   条件；
* 右外连接
  * SELECT   字段列表   FROM   表1   RIGHT   [OUTER]   JOIN   表2   ON   条件；

* 左外连接：相当于查询A表所有数据和交集部分数据
* 右外连接：相当于查询B表所有数据和交集部分数据

### 3.2 代码实现

```mysql
-- 左外连接
-- 查询emp表所有数据和对应的部门信息
SELECT * FROM emp LEFT JOIN dept on emp.dept_id = dept.did;


-- 右外连接
-- 查询dept表所有数据和对应的部门信息
SELECT * FROM emp RIGHT JOIN dept on emp.dept_id = dept.did;

SELECT * FROM dept LEFT JOIN emp on emp.dept_id = dept.did;
```



## 4、子查询

### 4.1 子查询概述

* 1、子查询概念：
  * 查询中嵌套查询，称嵌套查询为子查询
* 2、子查询根据查询结果不同，作用不同：
  * 单行单列
  * 多行单列
  * 多行多列

### 4.2 子查询查询语法

> 1、子查询根据查询结果不同，作用不同：

* 单行单列：作为条件值，使用=！=><等进行条件判断
  * SELECT   字段列表   FROM   表   WHERE   字段名   = （子查询）；

* 多行单列：作为条件值，使用等关键字进行条件判断
  * SELECT   字段列表   FRON   表   WHERE   字段名   in  （子查询）；

* 多行多列：作为虚拟表
  * SELECT   字段列表   FROM   (子查询)   WHERE   条件；

### 4.3 代码实现：

```mysql
-- 子查询

-- 单行单列
-- 查询工作高于猪八戒的员信息
SELECT * FROM emp WHERE salary > (SELECT salary FROM emp WHERE name = '猪八戒');

-- 多行单列
-- 查询‘财务部’ 和‘市场部’所有的员工信息
SELECT * FROM emp WHERE dept_id in (SELECT did FROM dept WHERE dname = '财务部' or dname = '市场部');

-- 多行多列
-- 查询入职日期是‘2011-11-11’之后的员工信息和部门信息
SELECT * FROM (SELECT * FROM emp WHERE join_date > '2011-11-11') as t1, dept WHERE t1.dept_id = dept.did;
```



## 5、多表查询案例

### 5.1 创建表

> 关系模型：

![](https://pic1.imgdb.cn/item/636638bb16f2c2beb102349b.jpg)

> 代码：

```mysql
DROP TABLE IF EXISTS emp;
DROP TABLE if EXISTS dept;
DROP TABLE if EXISTS job;
DROP TABLE if EXISTS salarygrade;

-- 部门表
CREATE TABLE dept(
	did INT PRIMARY KEY PRIMARY KEY,
	dname VARCHAR(50),
	loc VARCHAR(50) -- 部门所在地
)ENGINE = INNODB;

-- 职务表，职务名称，职务描述
CREATE TABLE job(
	jid int PRIMARY KEY,
	jname VARCHAR(20),
	description VARCHAR(50)
)ENGINE = INNODB;

-- 员工表
CREATE TABLE emp(
	id INT PRIMARY key,
	ename VARCHAR(50),
	job_id INT,	-- 职务id
	mgr INT,	-- 上级领导
	joindate DATE,	-- 入职日期
	salary DECIMAL(7,2),	-- 工资
	bonus DECIMAL(7,2),	-- 奖金
	dept_id INT,
	CONSTRAINT fk_job_emp FOREIGN KEY(job_id) REFERENCES job(jid),
	CONSTRAINT fk_dept_emp FOREIGN KEY(dept_id) REFERENCES dept(did)
)ENGINE = INNODB;

-- 工资等级表
CREATE TABLE salarygrade(
	grade int PRIMARY KEY,	-- 级别
	losalary INT, -- 最低工资
	hisalary INT	-- 最高工资
)ENGINE = INNODB;

-- 添加四个部门
INSERT INTO dept(did,dname,loc) VALUES 
(10,'教研部','北京'),
(20,'学工部','上海'),
(30,'销售部','广州'),
(40,'财务部','深圳');

-- 添加四个职务
INSERT INTO job(jid, jname, description) VALUES 
(1,'董事长','管理整个公司，接单'),
(2,'经理','管理部门员工'),
(3,'销售员','向客人推销产品'),
(4,'文员','使用办公软件');

-- 添加员工
INSERT INTO emp(id,ename,job_id,mgr,joindate,salary,bonus,dept_id) VALUES
(1001,'孙悟空',4,1004,'2000-12-17','8000.00',NULL,20),
(1002,'卢俊义',3,1006,'2001-02-20','16000.00','3000.00',30),
(1003,'林冲',3,1006,'2001-02-22','12500.00','5000.00',30),
(1004,'唐僧',2,1009,'2001-04-02','29750.00',NULL,20),
(1005,'李逵',4,1006,'2001-09-28','12500.00','14000.00',30),
(1006,'宋江',2,1009,'2001-05-01','28500.00',NULL,30),
(1007,'刘备',2,1009,'2001-09-01','24500.00',NULL,10),
(1008,'猪八戒',4,1004,'2007-04-19','30000.00',NULL,20),
(1009,'罗贯中',1,NULL,'2001-11-17','50000.00',NULL,10),
(1010,'吴用',3,1006,'2001-09-08','15000.00','0.00',30),
(1011,'沙僧',4,1004,'2007-05-23','11000.00',NULL,20),
(1012,'李逵',4,1006,'2001-12-03','9500.00',NULL,30),
(1013,'小白龙',4,1004,'2001-12-03','30000.00',NULL,20),
(1014,'关羽',4,1007,'2002-01-23','13000.00',NULL,10);

-- 添加5个工资等级
INSERT INTO salarygrade(grade,losalary,hisalary) VALUES
(1,7000,12000),
(2,12010,14000),
(3,14010,20000),
(4,20010,30000),
(5,30010,99990);
```

### 5.2 需求

* 1、查询所有员工信息。查询员工编号，员工姓名，工资，职务名称，职务描述

* 2、查询员工编号，员工姓名，工资，职务名称，职务描述，部门名称，部门位置
* 3、查询员工姓名，工资，工资等级

* 4、查询员工姓名，工资，职务名称，职务描述，部门名称，部门位置，工资等级

* 5、查询出部门编号、部门名称、部门位置、部门人数

### 5.3 代码实现

```mysql
-- 需求

-- 1、查询所有员工信息。查询员工编号，员工姓名，工资，职务名称，职务描述
/*
	分析：
		1、员工编号，员工姓名，工资 在 emp员工表中
		2、职务名称，职务描述 在 job职务表中
		3、job职务表 和 emp员工表：一对多 emp.job_id = job.jid
*/

-- 隐式内连接
SELECT
	emp.id,
	emp.ename,
	emp.salary,
	job.jname,
	job.description 
FROM
	emp,
	job 
WHERE
	emp.job_id = job.jid;-- 显式内连接
SELECT
	emp.id,
	emp.ename,
	emp.salary,
	job.jname,
	job.description 
FROM
	emp
	INNER JOIN job ON emp.job_id = job.jid;




-- 2、查询员工编号，员工姓名，工资，职务名称，职务描述，部门名称，部门位置

/*
	分析：
		1、员工编号，员工姓名，工资 在 emp员工表中
		2、职务名称，职务描述 在 job职务表中
		3、job职务表 和 emp员工表：一对多 emp.job_id = job.jid
		4、部门名称和部门位置 来自于 部门表dept
		5、dept 和 emp 一对多关系 dept.did = emp.dept_id
*/
-- 隐式内连接
SELECT
	emp.id,
	emp.ename,
	emp.salary,
	job.jname,
	job.description,
	dept.dname,
	dept.loc 
FROM
	emp,
	job,
	dept 
WHERE
	emp.job_id = job.jid 
	AND dept.did = emp.dept_id;-- 显式内连接
SELECT
	emp.id,
	emp.ename,
	emp.salary,
	job.jname,
	job.description,
	dept.dname,
	dept.loc 
FROM
	emp
	INNER JOIN job ON emp.job_id = job.jid
	INNER JOIN dept ON dept.did = emp.dept_id;




-- 3、查询员工姓名，工资，工资等级
/*
	分析：
		1、员工姓名，工资 在 emp员工表中
		2、工资等级 在 salarygrade工资等级表中
		3、emp.salary >= salarygrade.losalary and emp.salary <= salarygrade.hisalary
*/
SELECT
	emp.ename,
	emp.salary,
	t2.grade 
FROM
	emp,
	salarygrade t2 
WHERE
	emp.salary BETWEEN t2.losalary 
	AND t2.hisalary;



-- 4、查询员工姓名，工资，职务名称，职务描述，部门名称，部门位置，工资等级
/*
	分析：
		1、员工编号，员工姓名，工资 在 emp员工表中
		2、职务名称，职务描述 在 job职务表中
		3、job职务表 和 emp员工表：一对多 emp.job_id = job.jid
		4、部门名称和部门位置 来自于 部门表dept
		5、dept 和 emp 一对多关系 dept.did = emp.dept_id
		6、工资等级 在 salarygrade工资等级表中
		7、emp.salary >= salarygrade.losalary and emp.salary <= salarygrade.hisalary
*/
-- 显式内连接
SELECT
	emp.id,
	emp.ename,
	emp.salary,
	job.jname,
	job.description,
	dept.dname,
	dept.loc,
	t2.grade
FROM
	emp
	INNER JOIN job ON emp.job_id = job.jid
	INNER JOIN dept ON dept.did = emp.dept_id
	INNER JOIN salarygrade t2 ON emp.salary BETWEEN t2.losalary AND t2.hisalary;



-- 5、查询出部门编号、部门名称、部门位置、部门人数
/*
	分析：
		1、部门编号、部门名称、部门位置 在 dept部门表
		2、部门人数：在emp表中按照dept_id 进行分组，然后按照count(*)统计数量
		3、使用子查询，让部门表和分组之后的表进行内连接
*/
SELECT
	* 
FROM
	dept;
SELECT
	dept_id,
	count(*) 
FROM
	emp 
GROUP BY
	dept_id;
SELECT
	dept.did,
	dept.dname,
	dept.loc,
	t1.count 
FROM
	dept,(
	SELECT
		dept_id,
		Count(*) count 
	FROM
		emp 
	GROUP BY
		dept_id 
	) t1 
WHERE
	dept.did = t1.dept_id;
```



# 四、事务

## 1、事务简介

* 数据库的**事务**(Transaction)是一种机制、一个操作序列，包含了**一组数据库操作命令**
* 事务把所有的命令作为一个整体一起向系统提交或撤销操作请求，即这一组数据库命令**要么同时成功，要么同时失败**
* 事务是一个不可分割的工作逻辑单元

> 事务语句

* 开启事务
  * START TRANSACTION;
  * 或者BEGIN
* 提交事务
  * COMMIT;
* 回滚事务
  * ROLLBACK;

> 代码实现：

```mysql
DROP TABLE
IF
	EXISTS account;
	
	-- 创建账户表
CREATE TABLE account ( id INT PRIMARY KEY auto_increment, NAME VARCHAR ( 10 ), money DOUBLE ( 10, 2 ) )ENGINE = INNODB;

-- 添加数据
INSERT INTO account ( NAME, money )
VALUES
	( '张三', 1000 ),(
		'李四',
		1000 
);


UPDATE account set money = 1000;
SELECT
	* 
FROM
	account;

-- 转账操作
-- 开启事务

START TRANSACTION;
-- 1、查询李四金额
SELECT * FROM account WHERE name = '李四';

-- 2、李四金额 -500
UPDATE account set money = money - 500 WHERE name = '李四';

-- 出错了...

-- 3、张三金额 +500
UPDATE account set money = money + 500 WHERE name = '张三';

-- 回滚事务
ROLLBACK;

-- 提交事务
COMMIT;



SELECT * FROM account;
```



## 2、事务的四大特征

* 原子性(**A**tomicity):事务是不可分割的最小操作单位，要么同时成功，要么同时失败
* 一致性(**C**onsistency):事务完成时，必须使所有的数据都保持一致状态
* 隔离性(**I**solation):多个事务之间，操作的可见性
* 持久性(**D**urability):事务一旦提交或▣滚，它对数据库中的数据的改变就是永久的

> MySQL事务默认自动提交

![](https://pic1.imgdb.cn/item/6366548516f2c2beb135772d.jpg)
