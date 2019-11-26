package com.wzlue.jobApplication.service;

import com.wzlue.jobApplication.entity.JobApplicationEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户报名表（求职/应聘）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 17:10:18
 */
public interface JobApplicationService {
	
	JobApplicationEntity queryObject(Long id);
	
	List<JobApplicationEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);

	List<JobApplicationEntity> queryListByPlatform(Map<String, Object> map);

	int queryTotalByPlatform(Map<String, Object> map);

	/**
	 * 在职员工数量
	 * @param map
	 * @return
	 */
	int fourEntry(Map<String, Object> map);
	
	void save(JobApplicationEntity jobApplication);
	
	void update(JobApplicationEntity jobApplication);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	/**
	 * 入职员工打卡设置列表（1对1）
	 * @param map
	 * @return
	 */
	List<JobApplicationEntity> queryEntryList(Map<String, Object> map);

	/**
	 * 入职员工打卡列表用数量
	 * @param map
	 * @return
	 */
	int queryEntryTotal(Map<String, Object> map);

	void feedback(JobApplicationEntity jobApplication);


	/**
	 *分配个门店招聘人员
	 * @param jobApplication
	 */
	void distribution(JobApplicationEntity jobApplication);

	/**
	 * 修改余额（提现）
	 * @param jobApplicationEntity，提现金额
	 */
	void updateBalance(JobApplicationEntity jobApplicationEntity, BigDecimal amount);

	/**
	 * 用户返费余额总和
	 * @param openid
	 * @return
	 */
	BigDecimal rewardAll(String openid);

	/**
	 * 用户一天内同一个招聘是否已报名
	 * @param map
	 * @return
	 */
	int isEnroll(Map<String, Object> map);


	List<JobApplicationEntity> queryListByIds(Object[] ids);
	List<JobApplicationEntity> queryListAll(Map<String, Object> map);
}
