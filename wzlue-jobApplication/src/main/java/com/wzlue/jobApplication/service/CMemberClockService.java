package com.wzlue.jobApplication.service;

import com.wzlue.jobApplication.entity.CMemberClockEntity;
import org.apache.ibatis.annotations.Param;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 入职用户打卡表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 09:33:48
 */
public interface CMemberClockService {
	
	CMemberClockEntity queryObject(Long id);
	
	List<CMemberClockEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CMemberClockEntity cMemberClock);
	
	void update(CMemberClockEntity cMemberClock) throws ParseException;
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids,String tableName);

	//生成新的考勤表
	void createTablesByYearMonth(String date);

	/**
	 * 查询考勤表是否存在
	 * @param tableSchema
	 * @param tableName
	 * @return
	 */
	int isExistTable(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

	/**
	 * 传年月查员工打卡记录
	 * @param map
	 * @return
	 */
	List<CMemberClockEntity> queryMemberClock(Map<String, Object> map);

	int queryMemberClockTotal(Map<String, Object> map);

	/**
	 * 每月向生成的表中插入每个入职员工数据
	 * @param cMemberClockEntity
	 */
	void saveList(@Param("cMemberClockEntity") CMemberClockEntity cMemberClockEntity);

	/**
	 * 详情 表名(年月)+id
	 * @param time
	 * @param id
	 * @return
	 */
	CMemberClockEntity detail(@Param("time") String time,@Param("id") Long id);

	CMemberClockEntity apiDetail(@Param("time") String time, @Param("userNo") Long userNo, @Param("createDate") String createDate);

	/**
	 * api打卡
	 * @param cMemberClock
	 */
	void updateApi(CMemberClockEntity cMemberClock);

	/**
	 * api查用户具体某天的数据
	 * @param
	 * @return
	 */
	CMemberClockEntity apiDayClock(Map<String, Object> map);
}
