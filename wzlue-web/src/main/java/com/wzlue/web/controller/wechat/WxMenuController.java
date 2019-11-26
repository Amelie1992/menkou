package com.wzlue.web.controller.wechat;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;
import com.wzlue.common.utils.WxReturnCode;
import com.wzlue.web.controller.sys.AbstractController;
import com.wzlue.wechat.service.WxMenuService;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 微信菜单
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-24 11:11:41
 */
@Slf4j
@RestController
@RequestMapping("/wechat/wxmenu")
public class WxMenuController extends AbstractController {

    @Autowired
    private WxMenuService wxMenuService;

    /**
     * 通过appId查询自定义菜单
     *
     * @param appId
     * @return R
     */
    @GetMapping("/list")
    @RequiresPermissions("wechat:wxmenu:list")
    public R getWxMenuButton(String appId) {

        // 门店账号
        if (StrUtil.isNotBlank(getUser().getAppId()) && StrUtil.equals(getUser().getStore(), "1")) {
            appId = getUser().getAppId();
        }
        return R.ok(wxMenuService.getWxMenuButton(appId));
    }


    /**
     * 保存并发布菜单
     *
     * @param
     * @return R
     */
    @SysLog("保存并发布菜单")
    @PostMapping("save")
    @RequiresPermissions("wechat:wxmenu:save")
    public R saveAndRelease(@RequestBody String data) {
        JSONObject jSONObject = JSON.parseObject(data);
        String strWxMenu = jSONObject.getString("strWxMenu");
        String appId = jSONObject.getString("appId");

        // 门店账号
        if (StrUtil.isNotBlank(getUser().getAppId()) && StrUtil.equals(getUser().getStore(), "1")) {
            appId = getUser().getAppId();
        }

        try {
            wxMenuService.saveAndRelease(appId, strWxMenu);
            return R.ok();
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("发布自定义菜单失败appID:" + appId + ":" + e.getMessage());
            return WxReturnCode.wxErrorExceptionHandler(e);
        }
    }
}
