package com.wzlue.draw.service;

import com.wzlue.draw.entity.IntegralDrawPrizeEntity;

import java.util.List;
import java.util.Map;

/**
 * 积分抽奖奖项
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
public interface IntegralDrawPrizeService {
	
	IntegralDrawPrizeEntity queryObject(Long id);
	
	List<IntegralDrawPrizeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(IntegralDrawPrizeEntity integralDrawPrize);
	
	void update(IntegralDrawPrizeEntity integralDrawPrize);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
