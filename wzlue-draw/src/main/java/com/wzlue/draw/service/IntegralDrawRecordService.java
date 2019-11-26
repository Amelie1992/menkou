package com.wzlue.draw.service;

import com.wzlue.draw.entity.IntegralDrawRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 积分抽奖记录（我的奖品）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
public interface IntegralDrawRecordService {
	
	IntegralDrawRecordEntity queryObject(Long id);
	
	List<IntegralDrawRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(IntegralDrawRecordEntity integralDrawRecord);
	
	void update(IntegralDrawRecordEntity integralDrawRecord);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	void exchangeBatch(Long[] ids);
}
