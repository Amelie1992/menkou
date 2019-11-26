package com.wzlue.wechat.config;

import com.google.common.collect.Maps;
import com.wzlue.common.utils.ConfigConstant;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.service.WxAppService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.EventType;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * 公众号Configuration
 */
@Slf4j
@Configuration
public class WxMpConfiguration {

    /**
     * 全局缓存WxMpService
     */
    private static Map<String, WxMpService> mpServices = Maps.newHashMap();

    /**
     * 全局缓存WxMpMessageRouter
     */
    private static Map<String, WxMpMessageRouter> routers = Maps.newHashMap();

    private static RedisTemplate redisTemplate;
    private static WxAppService wxAppService;
    @Autowired
    public WxMpConfiguration( RedisTemplate redisTemplate, WxAppService wxAppService) {
        this.redisTemplate = redisTemplate;
        this.wxAppService = wxAppService;
    }

    /**
     * 获取WxMpService并缓存，会强制更新全局缓存
     *
     * @param wxApp
     * @return
     */
    public static WxMpService getMpService(WxAppEntity wxApp) {
        WxMpInRedisConfigStorage configStorage = new WxMpInRedisConfigStorage(redisTemplate);
        configStorage.setAppId(wxApp.getId());
        configStorage.setSecret(wxApp.getSecret());
        configStorage.setToken(wxApp.getToken());
        configStorage.setAesKey(wxApp.getAesKey());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        mpServices.put(wxApp.getId(), wxMpService);
        return wxMpService;
    }

    /**
     * 获取全局缓存WxMpService
     *
     * @param appId
     * @return
     */
    public static WxMpService getMpService(String appId) {
        WxMpService wxMpService = mpServices.get(appId);
        if (wxMpService == null) {
            WxAppEntity wxApp = wxAppService.getById(appId);
            if (wxApp != null) {
                if (ConfigConstant.YES.equals(wxApp.getIsComponent())) {//第三方授权账号

                } else {
                    WxMpInRedisConfigStorage configStorage = new WxMpInRedisConfigStorage(redisTemplate);
                    configStorage.setAppId(wxApp.getId());
                    configStorage.setSecret(wxApp.getSecret());
                    configStorage.setToken(wxApp.getToken());
                    configStorage.setAesKey(wxApp.getAesKey());
                    wxMpService = new WxMpServiceImpl();
                    wxMpService.setWxMpConfigStorage(configStorage);
                    mpServices.put(appId, wxMpService);
                }
            }
        }
        return wxMpService;
    }
}