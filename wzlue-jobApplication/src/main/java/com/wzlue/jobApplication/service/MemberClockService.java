package com.wzlue.jobApplication.service;


import com.wzlue.jobApplication.entity.MemberClockEntity;

import java.util.List;
import java.util.Map;

/**
 * 入职用户打卡表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-29 18:46:15
 */
public interface MemberClockService {
	
	MemberClockEntity queryObject(String id);
	
	List<MemberClockEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(MemberClockEntity memberClock);
	
	void update(MemberClockEntity memberClock);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
