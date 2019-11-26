package com.wzlue.store.service;

import com.wzlue.store.entity.TStoreSloganEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店标语表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 16:44:29
 */
public interface TStoreSloganService {
	
	TStoreSloganEntity queryObject(Long id);
	
	List<TStoreSloganEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TStoreSloganEntity tStoreSlogan);
	
	void update(TStoreSloganEntity tStoreSlogan);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);


	//根据参数查询列表
	List<TStoreSloganEntity> queryListByParam(Map<String, Object> map);
	//根据参数查询列表个数
	int queryTotalByParam(Map<String, Object> map);
}
