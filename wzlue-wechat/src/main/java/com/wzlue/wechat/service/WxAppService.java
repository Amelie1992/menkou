package com.wzlue.wechat.service;

import com.wzlue.common.base.Query;
import com.wzlue.wechat.entity.WxAppEntity;

import java.util.List;
import java.util.Map;

/**
 * 微信应用
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-22 11:11:41
 */
public interface WxAppService {
	
	WxAppEntity queryObject(String id);
	
	List<WxAppEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WxAppEntity wxApp);
	
	void update(WxAppEntity wxApp);

	void delete(String id);
	
	void deleteBatch(String[] ids);


	WxAppEntity getById(String appId);
	/**
	 * 微信原始标识查找
	 * @param toUser
	 * @return
	 */
    WxAppEntity findByWeixinSign(String toUser);

	/**
	 * 下拉列表
	 * @param query
	 * @return
	 */
	List<WxAppEntity> selectlist(Query query);

	/**
	 * 编辑门店资质内容
	 * @param wxApp
	 */
	void editQualified(WxAppEntity wxApp);

	/**
	 * 更新使用状态 使用/停用
	 * @param ids
	 */
	void updateEnabled(String[] ids);

	/**
	 * 更新门店资质 开启/关闭
	 * @param ids
	 */
	void updateQualified(String[] ids);

	/**
	 * 查询没有绑定的微信公众号
	 * @return
	 */
    List<WxAppEntity> selectlistNotApp();
}
