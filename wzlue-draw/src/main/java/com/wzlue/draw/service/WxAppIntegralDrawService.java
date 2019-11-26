package com.wzlue.draw.service;

import com.wzlue.draw.entity.WxAppIntegralDrawEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店（自定义）积分抽奖活动   （付费开通） 
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
public interface WxAppIntegralDrawService {
	
	WxAppIntegralDrawEntity queryObject(String appId);
	
	List<WxAppIntegralDrawEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WxAppIntegralDrawEntity wxAppIntegralDraw);
	
	void update(WxAppIntegralDrawEntity wxAppIntegralDraw);
	
	void delete(String appId);
	
	void deleteBatch(String[] appIds);

	List<WxAppIntegralDrawEntity> queryAppList(Map<String, Object> map);

	int queryAppTotal(Map<String, Object> map);
}
