package com.wzlue.smsCode.service;

import com.wzlue.smsCode.entity.SmsCodeEntity;

import java.util.List;
import java.util.Map;

/**
 * 短信验证码（含过期时间）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-01 10:47:38
 */
public interface SmsCodeService {
	
	SmsCodeEntity queryObject(Long id);
	
	List<SmsCodeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SmsCodeEntity smsCode);
	
	void update(SmsCodeEntity smsCode);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	void invalid(SmsCodeEntity smsCode);

	void deleteBeforeToday(SmsCodeEntity smsCode);


}
