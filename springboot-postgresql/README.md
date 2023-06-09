## Springboot操作PostgreSQL

### 一.  概述

### 二.  技术术语

1. 表空间（Tablespace）
   表空间是在数据库中存储数据文件的位置。它定义了数据在物理存储介质上的布局。使用表空间可以将数据文件分布在不同的磁盘上，以提高性能、管理存储空间和实现备份和恢复策略。默认情况下，PostgreSQL 使用一个名为 "pg_default" 的表空间。

2. 模式（Schema）
   模式是用于组织和命名数据库对象的命名空间。它可以将数据库对象划分为逻辑上独立的组，提供更好的对象管理和隔离。每个模式都有自己的名称空间，因此可以在同一数据库中创建多个具有相同名称的对象，只要它们在不同的模式中。

   默认情况下，每个数据库有一个名为 "public" 的模式。可以在 "public" 模式中创建表、视图和其他对象。除了 "public" 模式外，你还可以创建自定义模式，并在其中创建对象。

### 三.  开发说明

睿阳RSP研发过程

1. 创建表空间

   ```bash
   -- 创建目录
   mkdir /var/lib/postgresql/data/pgdata/risun_rsp
   chown postgres:postgres /var/lib/postgresql/data/pgdata/risun_rsp
   -- 创建表空间SQL
   create tablespace risun_rsp LOCATION '/var/lib/postgresql/data/pgdata/risun_rsp';
   ```

2. 创建数据库

   ```sql
   create database risun_project tablespace risun_rsp;
   comment on database risun_project is '睿阳RSP框架开发数据库';
   ```

3. 创建用户并授权

   ```sql
   create user root with password 'iamdante';
   grant all privileges on database risun_project to root;
   ```

### 四. OpenGauss

```sql
-- 创建用户
create user root identified by 'risun@321#!';
-- 创建表空间（OpenGauss 表空间不能位于data目录）
mkdir /var/lib/opengauss/risun_rsp
chown postgres:postgres /var/lib/opengauss/risun_rsp
create tablespace risun_rsp LOCATION '/var/lib/opengauss/risun_rsp';
-- 授权表空间（没有创建数据库权限）
grant create on tablespace risun_rsp to root;

-- 超级管理员创建数据库并授权
create database risun_project tablespace risun_rsp;
grant all privileges on database risun_project to root;
grant all privileges on schema public to root;
```

**问题：**

- ##### none of the server's SASL authentication mechanisms are supported

  出现这个问题的话说明你的用户密码加密方式不支持远程连接，修改 `postgresql.conf`，重启服务

  ```ini
  ## 取消注释
  password_encryption_type = 1 ## 密码格式sha256 MD5 都可用
  ```



### 七. 使用技巧

#### 1. 递归查询

```sql
-- 创建数据库并初始化数据
DROP TABLE IF EXISTS category;
CREATE TABLE category (
  id SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  parent_id INT,
  CONSTRAINT fk_category
    FOREIGN KEY(parent_id) REFERENCES category(id)
);

INSERT INTO category (id, name, parent_id)
VALUES
  (1, 'ROOT', NULL),
  (2, 'Baby', 1),
  (3, 'Home And Kitchen', 1),
  (4, 'Baby Care', 2),
  (5, 'Feeding', 2),
  (6, 'Gifts', 2),
  (7, 'Safety', 2),
  (8, 'Bedding', 3),
  (9, 'Bath', 3),
  (10, 'Furniture', 3),
  (11, 'Grooming', 4),
  (12, 'Hair Care', 4),
  (13, 'Baby Foods', 5),
  (14, 'Food Mills', 5),
  (15, 'Solid Feeding', 5),
  (16, 'Bed Pillows', 8),
  (17, 'Bed Skirts', 8);
  
 
 
 -- 查询 id 为 2 的分类以及它的所有的下级分类
 WITH RECURSIVE cte_categories AS (
  SELECT
    id,
    name,
    parent_id
  FROM category
  WHERE id = 2
  UNION
  SELECT
    c.id,
    c.name,
    c.parent_id
  FROM category c, cte_categories cs
  WHERE cs.id = c.parent_id
)
SELECT *
FROM cte_categories;
```

#### 2. Fetch分页

```sql
select * from category offset 0 FETCH FIRST 5 ROWS ONLY;
```

#### 3. ALL运算符

将一个值与一个子查询返回的所有的值进行比较，如果值列表中的所有值满足了指定的条件，表达式就返回真，否则表达式返回假。

```sql
-- 数组 [1, 2, 3] 中的所有元素是否都等于 2
select 2 = ALL(ARRAY[1, 2, 3]);

-- 数组 [1, 2, 3] 中的所有元素是否都大于等于 1
select 1 <= ALL(ARRAY[1, 2, 3]);

-- 数组 [1, 2, 3] 中的所有元素是否都不等于 0
select 0 <> ALL(ARRAY[1, 2, 3]);

-- 判断一个值 20 是否比所有的种类Id的都要高
select 20 > ALL(select id from category);
```

#### 4. ANY运算符

用于将一个值与一个值列表进行比较，只要值列表中有一个值满足指定的条件就返回真，否则返回假。在PG中，可以使用 `SOME` 关键字代替 `ANY`。

```sql
-- 数组 [1, 2, 3] 是否包含一个值为 2 的元素
select 2 = ANY(ARRAY[1, 2, 3]);

-- 数组 [1, 2, 3] 是否包含一个值大于 2 的元素
select 2 > ANY(ARRAY[1, 2, 3]);

-- 数组 [1, 2, 3] 是否包含一个值不等于 3 的元素
select 3 <> ANY(ARRAY[1, 2, 3]);

-- 是否存在主键高于15种类
select 15 > ANY(select id from category);
```

#### 5. GROUPING SETS

`GROUPING SETS` 是 `GROUP BY` 子句的参数，允许您在一次查询中生成多个维度的报表。

 比如，要想在一个销售报表中即有每个年份销售额的行，又有每个部门销售额的行，您可以在 `GROUP BY` 子句中使用 `GROUPING SETS` 实现它。

为了应对更加复杂的需求， PostgreSQL 还提供了 [`ROLLUP`](https://www.sjkjc.com/postgresql/rollup/) 和 [`CUBE`](https://www.sjkjc.com/postgresql/cube/) 以简化 `GROUPING SETS`。

```sql
-- 从 film 表中查找每个影片评级的影片数量
SELECT rating, count(*)
FROM film
GROUP BY rating
ORDER BY rating;

-- 从 film 表中查找每个租金的影片数量
SELECT rental_rate, count(*)
FROM film
GROUP BY rental_rate
ORDER BY rental_rate;

-- 在一个报表中包含上面的两个报表
-- 使用 UNION ALL 
SELECT rating, rental_rate, count(*)
FROM film
GROUP BY rating

UNION ALL

SELECT rating, rental_rate, count(*)
FROM film
GROUP BY rental_rate
ORDER BY rating, rental_rate;

-- 使用 GROUPING SETS，并且使用空的分组表达式 ()，添加一行以显示总影片数量
SELECT rating, rental_rate, count(*)
FROM film
GROUP BY GROUPING SETS ((rating), (rental_rate), ())
ORDER BY rating, rental_rate;
```

- ROLLUP 在某些特定的场景下，可以简化 `GROUPING SETS`

  - `ROLLUP(a, b)` 等效于 `GROUPING SETS((a,b), (a), ())`
  - `ROLLUP(a, b, c)` 等效于 `GROUPING SETS((a,b,c), (a,b), (a), ())`

  ```sql
  SELECT rating, rental_rate, count(*)
  FROM film
  GROUP BY rollup  (rating, rental_rate)
  ORDER BY rating, rental_rate;
  ```

- CUBE 在某些特定的场景下，可以简化 `GROUPING SETS`

  - `CUBE(a, b)` 等效于 `GROUPING SETS((a,b), (a), (b), ())`
  - `CUBE(a, b, c)` 等效于 `GROUPING SETS((a,b,c), (a,b), (a,c), (a), (b,c), (b), (c), ())`

#### 6. UNION、INTERSECT、EXCEPT

```sql
-- 交集
SELECT generate_series(1, 5)
INTERSECT
SELECT generate_series(3, 6)
ORDER BY generate_series;

-- 并集，删除重复的行
SELECT generate_series(1, 5)
union
SELECT generate_series(3, 6)
ORDER BY generate_series;

-- 从一个结果集中减去另一个结果集
SELECT generate_series(1, 5)
EXCEPT
SELECT generate_series(3, 6)
ORDER BY generate_series;

SELECT generate_series(3, 6)
EXCEPT
SELECT generate_series(1, 5)
ORDER BY generate_series;
```



### 八.  参考资料 

- https://www.sjkjc.com/postgresql

