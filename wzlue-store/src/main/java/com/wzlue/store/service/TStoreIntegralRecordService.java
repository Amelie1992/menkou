package com.wzlue.store.service;

import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreIntegralRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店积分记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
public interface TStoreIntegralRecordService {
	
	TStoreIntegralRecordEntity queryObject(Long id);
	
	List<TStoreIntegralRecordEntity> queryList(Map<String, Object> map);

	List<TStoreIntegralRecordEntity> queryListByDate(Map<String, Object> map);



	int queryTotal(Map<String, Object> map);

	int queryTotalByDate(Map<String, Object> map);

	void save(TStoreIntegralRecordEntity tStoreIntegralRecord);
	
	void update(TStoreIntegralRecordEntity tStoreIntegralRecord);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    R doNewbieTask(String userId);

	void validateOfdoNewbieTask(String userId);


    R cancelNewbieTask(String userId,String cancelFlag);

	R checkNewbieTask(String userId);
}
