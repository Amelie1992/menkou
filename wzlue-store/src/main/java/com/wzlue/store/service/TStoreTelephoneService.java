package com.wzlue.store.service;

import com.wzlue.store.entity.TStoreTelephoneEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店电话表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 16:10:28
 */
public interface TStoreTelephoneService {
	
	TStoreTelephoneEntity queryObject(Long id);
	
	List<TStoreTelephoneEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TStoreTelephoneEntity tStoreTelephone);
	
	void update(TStoreTelephoneEntity tStoreTelephone);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);


	/**
	 * 门店电话设置列表（1对1）
	 * @param map
	 * @return
	 */
	List<TStoreTelephoneEntity> queryListByParam(Map<String, Object> map);

	/**
	 * 门店电话列表用数量
	 * @param map
	 * @return
	 */
	int queryTotalByParam(Map<String, Object> map);
}
