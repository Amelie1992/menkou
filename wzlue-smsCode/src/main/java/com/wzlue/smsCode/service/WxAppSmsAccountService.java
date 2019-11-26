package com.wzlue.smsCode.service;

import com.wzlue.smsCode.entity.WxAppSmsAccountEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店短信账户
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-22 13:49:45
 */
public interface WxAppSmsAccountService {
	
	WxAppSmsAccountEntity queryObject(String appId);
	
	List<WxAppSmsAccountEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WxAppSmsAccountEntity wxAppSmsAccount);
	
	void update(WxAppSmsAccountEntity wxAppSmsAccount);
	
	void delete(String appId);
	
	void deleteBatch(String[] appIds);
}
