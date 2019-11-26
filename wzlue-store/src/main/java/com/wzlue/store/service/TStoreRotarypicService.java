package com.wzlue.store.service;

import com.wzlue.store.entity.TStoreRotarypicEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店轮播图
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-05 11:01:10
 */
public interface TStoreRotarypicService {
	
	TStoreRotarypicEntity queryObject(Long id);
	
	List<TStoreRotarypicEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TStoreRotarypicEntity tStoreRotarypic);
	
	void update(TStoreRotarypicEntity tStoreRotarypic);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);


	//根据参数查询列表
    List<TStoreRotarypicEntity> queryListByParam(Map<String, Object> map);
	//根据参数查询列表个数
	int queryTotalByParam(Map<String, Object> map);
}
