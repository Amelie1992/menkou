package com.wzlue.smsCode.dao;

import com.wzlue.smsCode.entity.WxAppSmsAccountEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 门店短信账户
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-22 13:49:45
 */
@Mapper
public interface WxAppSmsAccountDao extends BaseDao<WxAppSmsAccountEntity> {
	
}
