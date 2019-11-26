package com.wzlue.store.service;


import com.wzlue.common.base.R;
import com.wzlue.wechat.entity.WxUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店微信用户
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 16:10:28
 */
public interface TStoreWxUserService {



    WxUserEntity queryObjectByUserId(String userId);

    List<WxUserEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void update(WxUserEntity wxUser);


    R editIntegral(String id, int integral);


    List<WxUserEntity> queryToExport(Map<String, Object> map);
    List<WxUserEntity> queryToExportByIds(Object[] ids);


}
