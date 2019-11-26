package com.wzlue.wechat.dao;

import com.wzlue.wechat.entity.WxUserEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信用户
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-24 11:14:41
 */
@Mapper
public interface WxUserDao extends BaseDao<WxUserEntity> {

    WxUserEntity getByAppIdOpenId(@Param("appId") String appId, @Param("openId") String openId);

    void updateByAppidSub(@Param("wxUser") WxUserEntity wxUser, @Param("appId") String appId, @Param("subscribe") String subscribe);

    void saveOrUpdateBatch(List<WxUserEntity> listWxUser);

    WxUserEntity queryByOpenid(String openid);

    WxUserEntity getById(WxUserEntity userEntity);
}
