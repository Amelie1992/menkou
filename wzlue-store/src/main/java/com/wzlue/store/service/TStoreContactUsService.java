package com.wzlue.store.service;

import com.wzlue.store.entity.TStoreContactUsEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店联系我们
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-09-28 17:14:06
 */
public interface TStoreContactUsService {
	
	TStoreContactUsEntity queryObject(Long id);
	
	List<TStoreContactUsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TStoreContactUsEntity tStoreContactUs);
	
	void update(TStoreContactUsEntity tStoreContactUs);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    List<TStoreContactUsEntity> queryListByParam(Map<String, Object> map);

    int queryTotalByParam(Map<String, Object> map);
}
