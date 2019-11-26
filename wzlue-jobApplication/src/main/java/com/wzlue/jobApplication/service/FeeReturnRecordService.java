package com.wzlue.jobApplication.service;


import com.wzlue.jobApplication.entity.FeeReturnRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 返费记录表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-05 09:59:54
 */
public interface FeeReturnRecordService {
	
	FeeReturnRecordEntity queryObject(Long id);
	
	List<FeeReturnRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(FeeReturnRecordEntity feeReturnRecord);
	
	void update(FeeReturnRecordEntity feeReturnRecord);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	/**
	 * 用户所有返费list
	 * @param map
	 * @return
	 */
	List<FeeReturnRecordEntity> userFeeReturn(Map<String, Object> map);
}
