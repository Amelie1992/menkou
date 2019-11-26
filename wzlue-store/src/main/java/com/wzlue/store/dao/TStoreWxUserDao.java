package com.wzlue.store.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.wechat.entity.WxUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门店微信用户
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
@Mapper
public interface TStoreWxUserDao extends BaseDao<WxUserEntity> {

    WxUserEntity queryObjectByUserId(String userId);

    int checkUserOfWxAppActive(Map<String, Object> map);

    List<WxUserEntity> queryToExport(Map<String, Object> map);
    List<WxUserEntity> queryToExportByIds(Object[] ids);

}
