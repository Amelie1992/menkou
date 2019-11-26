package com.wzlue.store.service;

import com.wzlue.store.entity.TStoreNoticeEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店公告表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 16:44:30
 */
public interface TStoreNoticeService {
	
	TStoreNoticeEntity queryObject(Long noticeId);
	
	List<TStoreNoticeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TStoreNoticeEntity tStoreNotice);
	
	void update(TStoreNoticeEntity tStoreNotice);
	
	void delete(Long noticeId);
	
	void deleteBatch(Long[] noticeIds);


	//根据参数查询列表
	List<TStoreNoticeEntity> queryListByParam(Map<String, Object> map);
	//根据参数查询列表个数
	int queryTotalByParam(Map<String, Object> map);
}
