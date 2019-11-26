package com.wzlue.store.service;

import com.wzlue.store.entity.WxAppThemeColorEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店（自定义）主题色
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-06 15:40:32
 */
public interface WxAppThemeColorService {
	
	WxAppThemeColorEntity queryObject(String appId);
	
	List<WxAppThemeColorEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WxAppThemeColorEntity wxAppThemeColor);
	
	void update(WxAppThemeColorEntity wxAppThemeColor);
	
	void delete(String appId);
	
	void deleteBatch(String[] appIds);
}
