package com.wzlue.wechat.service;


import com.wzlue.common.base.Query;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.entity.WxMsgEntity;
import com.wzlue.wechat.entity.WxMsgEntityVO;

import java.util.List;
import java.util.Map;

/**
 * 微信消息
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-23 15:11:41
 */
public interface WxMsgService {
    WxMsgEntity queryObject(String id);

    List<WxMsgEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(WxMsgEntity wxApp);

    void update(WxMsgEntity wxApp);

    void delete(String id);

    void deleteBatch(String[] ids);

    /**
     * 获取分组后的消息列表
     *
     * @param params
     * @return
     */
    List<WxMsgEntityVO> listWxMsgMapGroup(Map<String, Object> params);

    int listWxMsgMapGroupCount(Query query);

    List<WxMsgEntity> findListByUserIdFlagAppid(Query query);

    int findListByUserIdFlagAppidCount(Query query);
}
