package com.linda.common.mybatis.generator;

import java.util.List;

import org.junit.Test;

import com.linda.framework.log.util.JsonUtil;

/**
 * 
 * @author lindezhi
 * 2015年11月15日 上午11:14:41
 */
public class UserDaoTest extends AbstractTestCase{
	
	@Test
	public void testAddUser(){
		UserInfoDao infoDao = factory.openSession(true).getMapper(UserInfoDao.class);
		UserInfo info = new UserInfo();
		info.setAddTime(System.currentTimeMillis());
		info.setMobile("15743128976");
		info.setSex("f");
		info.setOther("hahah");
		int addUserInfo = infoDao.addUserInfo(info);
	}
	
	/**
	 * ok
	 */
	public void testGetById(){
		long id = 10001;
		UserInfoDao infoDao = factory.openSession(true).getMapper(UserInfoDao.class);
		UserInfo info = infoDao.getById(id);
		System.out.println(JsonUtil.toJson(info));
	}
	
	/**
	 * ok
	 */
	public void testUpdateById(){
		long id = 10002;
		UserInfoDao infoDao = factory.openSession(true).getMapper(UserInfoDao.class);
		UserInfo info = infoDao.getById(id);
		info.setMobile("13178946732");
		info.setAddTime(System.currentTimeMillis());
		infoDao.updateById(info);
		System.out.println("update");
	}
	
	/**
	 * ok
	 */
	public void testDeleteById(){
		long id = 10001;
		UserInfoDao infoDao = factory.openSession(true).getMapper(UserInfoDao.class);
		infoDao.deleteById(id);
		System.out.println("delete:"+id);
	}
	
	public void testGetByMobile(){
		String mobile = "15743128976";
		UserInfoDao infoDao = factory.openSession(true).getMapper(UserInfoDao.class);
		UserInfo info = infoDao.getByMobile(mobile);
		System.out.println("getByMobile:"+JsonUtil.toJson(info));
	}
	
	public void testUpdateByMobile(){
		String mobile = "15743128976";
		UserInfoDao infoDao = factory.openSession(true).getMapper(UserInfoDao.class);
		UserInfo info = infoDao.getByMobile(mobile);
		System.out.println("getByMobile:"+JsonUtil.toJson(info));
		info.setAddTime(System.currentTimeMillis());
		info.setSex("m");
		infoDao.updateByMobile(info);
		System.out.println("updateByMobile:"+JsonUtil.toJson(info));
	}
	
	public void testDeleteByMobile(){
		String mobile = "15743128976";
		UserInfoDao infoDao = factory.openSession(true).getMapper(UserInfoDao.class);
		UserInfo info = infoDao.getByMobile(mobile);
		System.out.println("getByMobile:"+JsonUtil.toJson(info));
		infoDao.deleteByMobile(mobile);
		System.out.println("delete By mobile ok");
	}
	
	@Test
	public void testLimit(){
		UserInfoDao infoDao = factory.openSession(true).getMapper(UserInfoDao.class);
		List<UserInfo> list = infoDao.getListBySex("f", 10, 0);
		System.out.println("getByList:"+JsonUtil.toJson(list));
	}
	
	
	@Test
	public void testCount(){
		UserInfoDao infoDao = factory.openSession(true).getMapper(UserInfoDao.class);
		long countBySex = infoDao.getCountBySex("f");
		System.out.println("count:"+countBySex);
	}
	
	@Test
	public void testSelectOne(){
		UserInfoDao infoDao = factory.openSession(true).getMapper(UserInfoDao.class);
		UserInfo info = infoDao.getOneBySex("f");
		System.out.println("one:"+JsonUtil.toJson(info));
	}

}
