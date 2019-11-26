package com.wzlue.store.service;

import com.wzlue.store.entity.TStoreGloryEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店光荣榜表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 09:44:21
 */
public interface TStoreGloryService {
	
	TStoreGloryEntity queryObject(Long gloryId);
	
	List<TStoreGloryEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TStoreGloryEntity tStoreGlory);
	
	void update(TStoreGloryEntity tStoreGlory);
	
	void delete(Long gloryId);
	
	void deleteBatch(Long[] gloryIds);


	//根据参数查询列表
	List<TStoreGloryEntity> queryListByParam(Map<String, Object> map);

	List<TStoreGloryEntity> queryListByIds(Object[] ids);
	List<TStoreGloryEntity> queryListAll(Map<String, Object> map);
	//根据参数查询列表个数
	int queryTotalByParam(Map<String, Object> map);
}
