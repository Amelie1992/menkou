package com.wzlue.notice.service;

import com.wzlue.notice.entity.NoticeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-28 14:34:50
 */
public interface NoticeService {
	
	NoticeEntity queryObject(Long id);
	
	List<NoticeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(NoticeEntity notice);
	
	void update(NoticeEntity notice);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
