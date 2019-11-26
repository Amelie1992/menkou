package com.wzlue.store.service;

import com.wzlue.store.entity.WxAppJobTypeEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店（自定义）岗位种类
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-04 15:38:28
 */
public interface WxAppJobTypeService {
	
	WxAppJobTypeEntity queryObject(String appId);
	
	List<WxAppJobTypeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WxAppJobTypeEntity wxAppJobType);
	
	void update(WxAppJobTypeEntity wxAppJobType);
	
	void delete(String appId);
	
	void deleteBatch(String[] appIds);
}
