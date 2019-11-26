package com.wzlue.jobApplication.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.jobApplication.entity.ClockInEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 打卡地址设置
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-29 18:44:41
 */
@Mapper
public interface ClockInDao extends BaseDao<ClockInEntity> {

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
