package com.wzlue.wechat.service;

import com.wzlue.wechat.entity.WxMenuEntity;
import me.chanjar.weixin.common.error.WxErrorException;

/**
 * 自定义菜单
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-22 11:11:41
 */
public interface WxMenuService {

    /***
     * 获取WxApp下的菜单
     * @param appId
     * @return
     */
    String getWxMenuButton(String appId);

    /**
     * 保存并发布菜单
     *
     * @param
     */
    void saveAndRelease(String appId, String strWxMenu) throws WxErrorException;

    WxMenuEntity getById(String eventKey);
}
