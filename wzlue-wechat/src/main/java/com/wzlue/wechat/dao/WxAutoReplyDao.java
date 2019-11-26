package com.wzlue.wechat.dao;

import com.wzlue.wechat.entity.WxAutoReplyEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信自动回复
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-24 09:18:53
 */
@Mapper
public interface WxAutoReplyDao extends BaseDao<WxAutoReplyEntity> {

    List<WxAutoReplyEntity> findList(@Param("appId") String appId, @Param("type") String type, @Param("repMate") String repMate, @Param("reqKey") String reqKey,   @Param("reqType") String reqType);

    List<WxAutoReplyEntity> queryDefault(@Param("appId") String appId);
}
