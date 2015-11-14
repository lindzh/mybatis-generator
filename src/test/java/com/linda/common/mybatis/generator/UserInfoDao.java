package com.linda.common.mybatis.generator;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfoDao {

	public int addUserInfo(@Param("obj")UserInfo obj);

	public UserInfo getById(@Param("id")long id);
	
	public int updateById(@Param("obj")UserInfo obj);
	
	public int deleteById(@Param("id")long id);
	
	public UserInfo getByMobile(@Param("mobile")String mobile);
	
	public int updateByMobile(@Param("obj")UserInfo obj);
	
	public int deleteByMobile(@Param("mobile")String mobile);
	
	public UserInfo getOneBySex(@Param("sex")String sex);
	
	public List<UserInfo> getListBySex(@Param("sex")String sex,@Param("limit")int limit,@Param("offset") int offset);
	
	public long getCountBySex(@Param("sex")String sex);
	
}
