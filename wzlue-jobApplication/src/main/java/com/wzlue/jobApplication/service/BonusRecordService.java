package com.wzlue.jobApplication.service;

import com.wzlue.jobApplication.entity.BonusRecordEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 推荐奖励金记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-23 15:54:07
 */
public interface BonusRecordService {
	
	BonusRecordEntity queryObject(Long id);
	
	List<BonusRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(BonusRecordEntity bonusRecord);
	
	void update(BonusRecordEntity bonusRecord);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	void withdraw(String openid, BigDecimal amount);
}
