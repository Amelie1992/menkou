package com.wzlue.jobApplication.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.jobApplication.entity.CMemberClockEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
@Mapper
public interface CMemberClockDao extends BaseDao<CMemberClockEntity> {
	//生成新的考勤表
	void createTablesByYearMonth(String month);

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

	List<CMemberClockEntity> queryMemberClock2(Map<String, Object> map);

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
	CMemberClockEntity detail(@Param("time") String time, @Param("id") Long id);

	CMemberClockEntity apiDetail(@Param("time") String time, @Param("userNo") Long userNo, @Param("createDate")String createDate);
	/**
	 * 软删除
	 * @param
	 * @return
	 */
	int updateAllFlag(Map<String, Object> map);

	/**
	 * 昨天的打卡记录
	 * @param cMemberClockEntity
	 * @return
	 */
	CMemberClockEntity yesterdayClock(CMemberClockEntity cMemberClockEntity);


	/**
	 * 前一天日期
	 * @param
	 * @return
	 */
	String beforeDay(String today);

	/**
	 * 查询所有的打卡记录表
	 * @return
	 */
	String[] cMemBerClockTables();

	/**
	 * api查用户具体某天的数据
	 * @param
	 * @return
	 */
	CMemberClockEntity apiDayClock(Map<String, Object> map);

}
