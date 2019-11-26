package com.wzlue.smsCode.dao;

import com.wzlue.smsCode.entity.SmsCodeEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短信验证码（含过期时间）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-01 10:47:38
 */
@Mapper
public interface SmsCodeDao extends BaseDao<SmsCodeEntity> {

    void invalid(SmsCodeEntity smsCode);

    void deleteBeforeToday(SmsCodeEntity smsCode);
	
}
