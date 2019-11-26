package com.wzlue.store.service;

import com.wzlue.store.entity.TStoreConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店配置表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 09:44:21
 */
public interface TStoreConfigService {
	
	TStoreConfigEntity queryObject(Long configId);
	
	List<TStoreConfigEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TStoreConfigEntity tStoreConfig);
	
	void update(TStoreConfigEntity tStoreConfig);
	
	void delete(Long configId);
	
	void deleteBatch(Long[] configIds);

	//根据参数查询列表
	List<TStoreConfigEntity> queryListByParam(Map<String, Object> map);
	//根据参数查询列表个数
	int queryTotalByParam(Map<String, Object> map);

}
