package com.wzlue.wechat.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.common.base.Query;
import com.wzlue.wechat.entity.WxAppEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 微信应用
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-19 14:48:10
 */
@Mapper
public interface WxAppDao extends BaseDao<WxAppEntity> {

    WxAppEntity getById(String appId);

    WxAppEntity findByWeixinSign(String weixinSign);

    List<WxAppEntity> selectlist(Query query);

    int isQualified(WxAppEntity appEntity);

    List<WxAppEntity> getListByAppIds(String[] ids);

    List<WxAppEntity> selectlistNotApp();
}
