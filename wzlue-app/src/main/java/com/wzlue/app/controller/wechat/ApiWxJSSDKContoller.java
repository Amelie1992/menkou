package com.wzlue.app.controller.wechat;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.LogSource;
import com.wzlue.common.base.R;
import com.wzlue.wechat.config.WxMpConfiguration;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.service.WxAppService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信JS-SDK
 */
@Slf4j
@RestController
@RequestMapping("/app/wechat/jssdk")
public class ApiWxJSSDKContoller {

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WxAppService appService;

    /**
     * * 获取配置
     *
     * @param appId 公众号appid
     * @param url   当前网页的URL，不包含#及其后面部分
     * @return <p>
     * appId: '', // 必填，公众号的唯一标识
     * timestamp: , // 必填，生成签名的时间戳
     * nonceStr: '', // 必填，生成签名的随机串
     * signature: '',// 必填，签名
     * jsApiList: [] // 必填，需要使用的JS接口列表  前端写
     * </p>
     */
    @SysLog(value = "getWxConfig", source = LogSource.APP)
    @GetMapping("/getWxConfig")
    public R getWxConfig(String appId, String url) {

        try {
            WxAppEntity appEntity = appService.getById(appId);

            if (null == appEntity && StrUtil.isBlank(url))
                return R.error(-1, "");

            wxMpService = WxMpConfiguration.getMpService(appId);

            WxJsapiSignature jsapiSignature = wxMpService.createJsapiSignature(url);


            return R.ok().put("wxConfig", jsapiSignature);

        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("getWxConfig", e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getWxConfig", e);
        }

        return R.error(-1, "");
    }
}
