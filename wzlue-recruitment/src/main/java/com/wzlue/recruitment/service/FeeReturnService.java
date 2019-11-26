package com.wzlue.recruitment.service;

import com.wzlue.recruitment.entity.FeeReturnEntity;

import java.util.List;
import java.util.Map;

/**
 * （招聘）返费
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 17:12:55
 */
public interface FeeReturnService {
	
	FeeReturnEntity queryObject(Long id);
	
	List<FeeReturnEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(FeeReturnEntity feeReturn);
	
	void update(FeeReturnEntity feeReturn);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	List<FeeReturnEntity> queryByRecruitmentId(Long recruitmentId);
}
