package com.wzlue.jobApplication.service;

import com.wzlue.jobApplication.entity.ClockInEntity;

import java.util.List;
import java.util.Map;

/**
 * 打卡地址设置
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-29 18:44:41
 */
public interface ClockInService {
	
	ClockInEntity queryObject(String id);
	
	List<ClockInEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ClockInEntity clockIn);
	
	void update(ClockInEntity clockIn);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);

	/**
	 * 根据招聘id查询打卡地址
	 * @param
	 * @return
	 */
	ClockInEntity queryRecruitClock(Long recruitId);

	/**
	 * 根据入职id查询打卡地址
	 * @param
	 * @return
	 */
	ClockInEntity queryJobClock(Long memberId);

	/**
	 * 入职员工打卡设置列表（1对1）
	 * @param map
	 * @return
	 */
	List<ClockInEntity> queryEntryList(Map<String, Object> map);

	/**
	 * 软删除
	 * @param clockInEntity
	 * @return
	 */
	int updateDel(ClockInEntity clockInEntity);
}
