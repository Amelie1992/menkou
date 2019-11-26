package com.wzlue.store.service;

import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreSignInRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店签到记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
public interface TStoreSignInRecordService {
	
	TStoreSignInRecordEntity queryObject(Long id);
	
	List<TStoreSignInRecordEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TStoreSignInRecordEntity tStoreSignInRecord);
	
	void update(TStoreSignInRecordEntity tStoreSignInRecord);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    R signIn(Map<String, Object> map);

	R checkSignIn(Map<String, Object> map);

}
