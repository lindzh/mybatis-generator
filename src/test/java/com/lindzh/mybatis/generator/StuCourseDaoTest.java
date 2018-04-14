package com.lindzh.mybatis.generator;

import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.lindzh.mybatis.generator.bean.StuCourse;
import com.lindzh.mybatis.generator.dao.StuCourseDao;

public class StuCourseDaoTest extends AbstractTestCase{


	@Test
	public void addCourse(){
		StuCourseDao courseDao = factory.openSession(true).getMapper(StuCourseDao.class);
		Random random = new Random();
		for(int i=0;i<10;i++){
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

	@Test
	public void testId(){
		long id = 100;
		StuCourseDao courseDao = factory.openSession(true).getMapper(StuCourseDao.class);
		StuCourse course1 = new StuCourse();
		course1.setAddTime(System.currentTimeMillis());
		long courseId = 1005;
		course1.setCourseId(courseId);
		course1.setStuId(10098);
		course1.setScore(88);
		courseDao.addStuCourse(course1);
		id = course1.getId();

		StuCourse course = courseDao.getById(id);
		System.out.println("id get:"+JSONUtils.toJSON(course));
		course.setScore(100);
		course.setAddTime(System.currentTimeMillis());
		course = courseDao.getById(id);
		System.out.println("update get:"+JSONUtils.toJSON(course));
		courseDao.deleteById(id);
		course = courseDao.getById(id);
		System.out.println("delete get:"+course);
	}

	@Test
	public void testUnique(){
		long userId=49732;
		long courseId=10388;
		StuCourseDao courseDao = factory.openSession(true).getMapper(StuCourseDao.class);

		StuCourse course1 = new StuCourse();
		course1.setAddTime(System.currentTimeMillis());
		course1.setCourseId(courseId);
		course1.setStuId(userId);
		course1.setScore(88);
		courseDao.addStuCourse(course1);

		StuCourse course = courseDao.getByUserAndCourse(userId, courseId);
		System.out.println("stu_course get:"+JSONUtils.toJSON(course));
		
		course.setScore(100);
		course.setAddTime(System.currentTimeMillis());
		courseDao.updateByUserAndCourse(course);
		course = courseDao.getByUserAndCourse(userId, courseId);
		System.out.println("stu_course update:"+JSONUtils.toJSON(course));
		
		courseDao.deleteByUserAndCourse(userId, courseId);
		course = courseDao.getByUserAndCourse(userId, courseId);
		System.out.println("stu_course delete:"+JSONUtils.toJSON(course));
	}
	
	@Test
	public void testLimitOffset(){
		long courseId=1005;
		StuCourseDao courseDao = factory.openSession(true).getMapper(StuCourseDao.class);

		Random random = new Random();
		for(int i=0;i<10;i++){
			StuCourse course = new StuCourse();
			course.setAddTime(System.currentTimeMillis()-random.nextInt(100000));
			long stuid = random.nextInt(50000)+1000;
			course.setCourseId(courseId);
			course.setStuId(stuid);
			int score = random.nextInt(50)+50;
			course.setScore(score);
			courseDao.addStuCourse(course);
		}

		long count = courseDao.getCountByCourseAndTime(courseId, 0, System.currentTimeMillis());
		System.out.println("count:"+count);
		List<StuCourse> courses = courseDao.getListByCourseAndTime(courseId, 0, System.currentTimeMillis(), 10, 0);
		System.out.println("stu_course list:"+JSONUtils.toJSON(courses));
	}

}
