package com.linda.common.mybatis.generator;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.linda.common.mybatis.generator.bean.StuCourse;
import com.linda.common.mybatis.generator.dao.StuCourseDao;
import com.linda.framework.log.util.JsonUtil;

public class StuCourseDaoTest extends AbstractTestCase{
	
	public void addCourse(){
		StuCourseDao courseDao = factory.openSession(true).getMapper(StuCourseDao.class);
		Random random = new Random();
		for(int i=0;i<1000;i++){
			StuCourse course = new StuCourse();
			course.setAddTime(System.currentTimeMillis());
			long courseId = 1005;//random.nextInt(10000)+1000;
			long stuid = random.nextInt(50000)+1000;
			course.setCourseId(courseId);
			course.setStuId(stuid);
			int score = random.nextInt(50)+50;
			course.setScore(score);
			courseDao.addStuCourse(course);
		}
	}
	
	public void testId(){
		long id = 1;
		StuCourseDao courseDao = factory.openSession(true).getMapper(StuCourseDao.class);
		StuCourse course = courseDao.getById(id);
		System.out.println("id get:"+JsonUtil.toJson(course));
		course.setScore(100);
		course.setAddTime(System.currentTimeMillis());
		course = courseDao.getById(id);
		System.out.println("update get:"+JsonUtil.toJson(course));
		courseDao.deleteById(id);
		course = courseDao.getById(id);
		System.out.println("delete get:"+JsonUtil.toJson(course));
	}
	
	public void testUnique(){
		long userId=49732;
		long courseId=10388;
		StuCourseDao courseDao = factory.openSession(true).getMapper(StuCourseDao.class);
		StuCourse course = courseDao.getByUserAndCourse(userId, courseId);
		System.out.println("stu_course get:"+JsonUtil.toJson(course));
		
		course.setScore(100);
		course.setAddTime(System.currentTimeMillis());
		courseDao.updateByUserAndCourse(course);
		course = courseDao.getByUserAndCourse(userId, courseId);
		System.out.println("stu_course update:"+JsonUtil.toJson(course));
		
		courseDao.deleteByUserAndCourse(userId, courseId);
		course = courseDao.getByUserAndCourse(userId, courseId);
		System.out.println("stu_course delete:"+JsonUtil.toJson(course));
	}
	
	@Test
	public void testLimitOffset(){
		long courseId=1005;
		StuCourseDao courseDao = factory.openSession(true).getMapper(StuCourseDao.class);
		long count = courseDao.getCountByCourseAndTime(courseId, 0, System.currentTimeMillis());
		System.out.println("count:"+count);
		List<StuCourse> courses = courseDao.getListByCourseAndTime(courseId, 0, System.currentTimeMillis(), 10, 0);
		System.out.println("stu_course list:"+JsonUtil.toJson(courses));
	}

}
