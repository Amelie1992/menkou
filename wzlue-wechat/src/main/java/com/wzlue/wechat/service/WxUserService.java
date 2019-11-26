package com.wzlue.wechat.service;

import com.wzlue.wechat.entity.WxUserEntity;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.List;
import java.util.Map;

/**
 * 微信用户
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-24 11:14:41
 */
public interface WxUserService {
	
	WxUserEntity queryObject(String id);

	List<WxUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WxUserEntity wxUser);
	
	void update(WxUserEntity wxUser);
	
	void delete(String id);

	void deleteBatch(String[] ids);

	void updateById(WxUserEntity wxUser);

	/**
	 * 根据openId获取用户
	 * @param appId
	 * @param openId
	 * @return
	 */
	WxUserEntity getByAppIdOpenId(String appId, String openId);

	/**
	 * 同步微信用户
	 * @param appId
	 * @param userId
	 */
	void synchroWxUser(String appId, Long userId) throws WxErrorException;

	/**
	 * 修改用户备注
	 * @param entity
	 * @return
	 */
	boolean updateRemark(WxUserEntity entity) throws WxErrorException;

	/**
	 * 标签
	 * @param taggingType
	 * @param appId
	 * @param tagId
	 * @param openIds
	 * @throws WxErrorException
	 */
	void tagging(String taggingType,String appId,Long tagId,String[] openIds) throws WxErrorException;

	WxUserEntity getById(String id);

	WxUserEntity queryByOpenid(String openid);

	WxUserEntity getById(WxUserEntity userEntity);
}
