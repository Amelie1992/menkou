package com.wzlue.wechat.service;

import com.wzlue.wechat.entity.WxAutoReplyEntity;

import java.util.List;
import java.util.Map;

/**
 * 微信自动回复
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-24 09:18:53
 */
public interface WxAutoReplyService {

    WxAutoReplyEntity queryObject(String id);

    List<WxAutoReplyEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(WxAutoReplyEntity wxAutreply);

    void update(WxAutoReplyEntity wxAutreply);

    void delete(String id);

    void deleteBatch(String[] ids);

    List<WxAutoReplyEntity> findList(String appId, String type, String repMate, String reqKey, String reqType);

    List<WxAutoReplyEntity> queryDefault(String appId);
}
