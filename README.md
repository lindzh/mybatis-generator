# mybatis-generator [![Build Status](https://travis-ci.org/lindzh/mybatis-generator.svg?branch=master)](https://travis-ci.org/lindzh/mybatis-generator)    [![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)   [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.lindzh/mybatis-generator/badge.svg)](http://search.maven.org/#search%7Cga%7C1%7Ccom.lindzh)

定义Java bean填写表和查询相关注解，自动生成增删改查，获取列表Dao和xml文件

## 使用

使用maven引入jar包

```
<dependency>
	<groupId>com.lindzh</groupId>
	<artifactId>mybatis-generator</artifactId>
	<version>1.0.3</version>
<dependency>
```

```java
@Table(name="user_info",autoGeneratePrimaryKey=true)
public class UserInfo {
	
	@PrimaryKey
	private long id;
	
	@UniqueKey(name="Mobile")
	@Column(column="mobile")
	private String mobile;
	
	@Index(name="Sex",count=true,limitOffset=true,selectOne=true)
	@Column(column="sex")
	private String sex;
	
	@Column(column="add_time")
	private long addTime;
	
	private String other;

}
```
## @Table
@Table(name="user_info",autoGeneratePrimaryKey=true)
指定数据库中表名，autoGeneratePrimaryKey=true表示主键由数据库自动生成，每个bean都会生成insertSQLxml和Dao
如果主键由数据库自动生成,会生成useGeneratedKeys="true" keyProperty="@PrimaryKeyField"，如下
insert生成如下
```java
	public int addUserInfo(@Param("obj")UserInfo obj);
```
insert xml如下
```xml
<insert id="addUserInfo" useGeneratedKeys="true" keyProperty="id" parameterType="com.lindzh.mybatis.generator.UserInfo">
	insert into user_info(mobile,sex,add_time)
	values(#{obj.mobile},#{obj.sex},#{obj.addTime})
</insert>
```
@Column(column="add_time")
指定数据库字段，如果列名未指定，则和该字段名称一样
如果一个字段没有添加@Column注解，则不会加入表字段映射
@PrimaryKey主键字段映射，如果一个字段被指定为PrimaryKey，再指定为@Column是无效的


## @PrimaryKey
@PrimaryKey
private long id;
数据库主键映射，每个table必须有唯一一个主键

主键字段会自动生成通过主键查询，更新，删除xml SQL和Dao，如:
```java
public UserInfo getById(@Param("id")long id);

public int updateById(@Param("obj")UserInfo obj);

public int deleteById(@Param("id")long id);
```

生成XML如下：
```xml
<select id="getById" resultMap="UserInfoResultMap">
	select * from user_info where id=#{id}
</select>

<update id="updateById" parameterType="com.lindzh.mybatis.generator.UserInfo">
	update user_info set mobile=#{obj.mobile},sex=#{obj.sex},add_time=#{obj.addTime}
	where id=#{obj.id}
</update>

<delete id="deleteById">
	delete from user_info where id=#{id}
</delete>
```

## @UniqueKey
@UniqueKey(name="Mobile")
@Column(column="mobile")
private String mobile;
指定唯一索引，这个地方假设数据库user_info表的mobile是唯一索引
如果多个字段组合是唯一索引则在该唯一索引上添加@UniqueKey，name要求一致
如：学生选课，有学生表，有课程表
现在学生选课添加一张中间表，则stu_id和course_id是唯一索引，此时选课bean加入注解如下
```java
@Table(name="stu_course",autoGeneratePrimaryKey=true)
public class StuCourse {
	
	@PrimaryKey
	private long id;
	
	@UniqueKey(name="UserAndCourse")
	@Column(column="stu_id")
	private long stuId;
	
	@Index(name="CountAndTime")
	@UniqueKey(name="UserAndCourse")
	@Column(column="course_id")
	private long courseId;
	
	@Index(name="CountAndTime")
	@Column(column="add_time")
	private long addTime;
	
	@Column(column="score")
	private int score;
}
```
此时会自动生成如下：
```java
public StuCourse getByUserAndCourse(@Param("stuId")long stuId,@Param("courseId")long courseId);

public int updateByUserAndCourse(@Param("obj")StuCourse obj);

public int deleteByUserAndCourse(@Param("stuId")long stuId,@Param("courseId")long courseId);
```
生成XML如下：
```xml
<select id="getByUserAndCourse" resultMap="StuCourseResultMap">
	select * from stu_course where stu_id=#{stuId} and course_id=#{courseId}
</select>

<update id="updateByUserAndCourse" parameterType="com.lindzh.mybatis.generator.StuCourse">
	update stu_course set add_time=#{obj.addTime},score=#{obj.score}
	where stu_id=#{obj.stuId} and course_id=#{obj.courseId}
</update>

<delete id="deleteByUserAndCourse">
	delete from stu_course where stu_id=#{stuId} and course_id=#{courseId}
</delete>
```
如果不想生成update和delete
@UniqueKey(name="UserAndCourse",select=true,update=false,delete=false)
说明：unique查询，插入不一定要求数据库有该索引，如果没有唯一索引，请确保这些SQL执行不会异常

## @Index 分页查询，数量查询
如用户表
@Index(name="Sex",count=true,limitOffset=true,selectOne=true)
@Column(column="sex")
private String sex;
会生成如下查询：
```java
public UserInfo getOneBySex(@Param("sex")String sex);

public List<UserInfo> getListBySex(@Param("sex")String sex,@Param("limit")int limit,@Param("offset") int offset);

public long getCountBySex(@Param("sex")String sex);
```
xml生成如下：
```xml
<select id="getListBySex" resultMap="UserInfoResultMap">
	select * from user_info where
	sex=#{sex}
	order by id desc limit #{limit} offset #{offset}
</select>

<select id="getCountBySex" resultType="long">
	select count(*) from user_info where 
	sex=#{sex}
</select>

<select id="getOneBySex" resultMap="UserInfoResultMap">
	select * from user_info where
	sex=#{sex}
	order by id desc limit 1
</select>
```

如果有多个条件：
如查询一门课程某个时间段选课的列表和数量
需要定义的注解如下
```java
	@Index(name="CountAndTime")
	@Column(column="course_id")
	private long courseId;
	
	@Index(name="CountAndTime")
	@Column(column="add_time")
	private long addTime;
```
生成Dao如下：
```java
public List<StuCourse> getListByCountAndTime(@Param("courseId")long courseId,@Param("addTime")long addTime,@Param("limit")int limit,@Param("offset") int offset);

public long getCountByCountAndTime(@Param("courseId")long courseId,@Param("addTime")long addTime);
```
生成xml如下：
```xml
	<select id="getListByCountAndTime" resultMap="StuCourseResultMap">
		select * from stu_course where
		course_id=#{courseId} and add_time=#{addTime}
		order by id desc limit #{limit} offset #{offset}
	</select>
	
	<select id="getCountByCountAndTime" resultType="long">
		select count(*) from stu_course where 
		course_id=#{courseId} and add_time=#{addTime}
	</select>
```
某一时间段呢？请直接修改生成的dao和xml完成定制化需求

如：某门课在某一时间段的选课
在生成dao的基础上手动修改dao java文件和xml文件
手动修改dao如下：
```java
public List<StuCourse> getListByCountAndTime(@Param("courseId")long courseId,@Param("startTime")long startTime,@Param("endTime")long endTime,@Param("limit")int limit,@Param("offset") int offset);

public long getCountByCountAndTime(@Param("courseId")long courseId,@Param("startTime")long startTime,@Param("endTime")long endTime);
```
手动修改xml：
```xml
	<select id="getListByCourseAndTime" resultMap="StuCourseResultMap">
		select * from stu_course where
		course_id=#{courseId} and add_time&gt;=#{startTime} and add_time&lt;=#{endTime}
		order by id desc limit #{limit} offset #{offset}
	</select>
	
	<select id="getCountByCourseAndTime" resultType="long">
		select count(*) from stu_course where 
		course_id=#{courseId} and add_time&gt;=#{startTime} and add_time&lt;=#{endTime}
	</select>
```
手动修改即可完成定制化需求
说明：Index只是通过多个字段查询返回列表，数量或者返回第一个，关于排序默认使用id，定制化请手动修改xml
index不要求数据库有相关索引，如果没有索引，查询会很慢

## 自动生成创建表语句SQL

mybatis generater 还可以根据pojo的定义生成创建表的语句，方便开发人员无需编写创建表的语句，直接使用生成的结果既可以完成数据库的操作。

```
create table banner_info (
id bigint primary key auto_increment,
accid varchar(100) default null,
banner_type int default 0,
banner_value_type int default 0,
banner_value varchar(100) default null,
banner_image varchar(100) default null,
banner_title varchar(100) default null,
banner_order int default 0,
add_time bigint default 0,
update_time bigint default 0,
key accountType(accid,banner_type)
);
```



