package com.wzlue.app.controller.wechat;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.R;
import com.wzlue.common.utils.Constant;
import com.wzlue.store.entity.TStoreConfigEntity;
import com.wzlue.store.entity.WxAppJobTypeEntity;
import com.wzlue.store.entity.WxAppThemeColorEntity;
import com.wzlue.store.service.TStoreConfigService;
import com.wzlue.store.service.WxAppJobTypeService;
import com.wzlue.store.service.WxAppThemeColorService;
import com.wzlue.sys.entity.SysUserEntity;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.service.WxAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 微信应用
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-22 11:11:41
 */
@Slf4j
@RestController
@RequestMapping("/app/wechat/wxapp")
public class ApiWxAppController {
    @Autowired
    private WxAppService wxAppService;
    @Autowired
    private WxAppJobTypeService wxAppJobTypeService;
    @Autowired
    private TStoreConfigService tStoreConfigService;
    @Autowired
    private WxAppThemeColorService wxAppThemeColorService;


    /**
     * 获取微信公众号  资质
     */
    @GetMapping("/description/{id}")
    public R info(@PathVariable("id") String id) {
        WxAppEntity wxApp = wxAppService.queryObject(id);

        return R.ok().put("description", wxApp.getDescription());
    }

    /**
     * 通过appId(id)获取微信公众号的  名称 & 头像
     */
    @GetMapping("/name")
    public R name(String appId) {
        WxAppEntity wxApp = wxAppService.queryObject(appId);

        return R.ok().put("name", wxApp.getName()).put("logo", wxApp.getHeadLogo());
    }


    /**
     * 获取当前门店的自定义   岗位种类
     */
    @GetMapping("/custom")
    public R custom(String appId) {

        List<TStoreConfigEntity> tStoreConfigList = new ArrayList<>();

        if (StrUtil.isNotBlank(appId)) {

            WxAppJobTypeEntity wxAppJobType = wxAppJobTypeService.queryObject(appId);

            if (null != wxAppJobType) {
                tStoreConfigList.add(tStoreConfigService.queryObject(wxAppJobType.getButton1()));
                tStoreConfigList.add(tStoreConfigService.queryObject(wxAppJobType.getButton2()));
                tStoreConfigList.add(tStoreConfigService.queryObject(wxAppJobType.getButton3()));
                tStoreConfigList.add(tStoreConfigService.queryObject(wxAppJobType.getButton4()));

                if (wxAppJobType.getNumber() == 4) {

                } else if (wxAppJobType.getNumber() == 5) {
                    tStoreConfigList.add(tStoreConfigService.queryObject(wxAppJobType.getButton5()));

                } else if (wxAppJobType.getNumber() == 8) {
                    tStoreConfigList.add(tStoreConfigService.queryObject(wxAppJobType.getButton5()));
                    tStoreConfigList.add(tStoreConfigService.queryObject(wxAppJobType.getButton6()));
                    tStoreConfigList.add(tStoreConfigService.queryObject(wxAppJobType.getButton7()));
                    tStoreConfigList.add(tStoreConfigService.queryObject(wxAppJobType.getButton8()));

                }
            }

        }
        if (tStoreConfigList.size() > 0) {
            for (TStoreConfigEntity config:tStoreConfigList) {
                //标记：1推荐岗位；2返费岗位；3灵活用工；4少数民族；5学生工；6大龄工；7一手单
                //后缀：1推荐岗位；2返费岗位；3灵活用工；4全部岗位；5少数民族；6学生工；7大龄工；8一手单
                if (config.getConfigId()==121){
                    config.setLabel(1);
                    config.setSuffix(1);
                }else if (config.getConfigId()==123){
                    config.setLabel(2);
                    config.setSuffix(2);
                }else if (config.getConfigId()==122){
                    config.setLabel(3);
                    config.setSuffix(3);
                }else if (config.getConfigId()==124) {
                    config.setSuffix(4);
                }else if (config.getConfigId()==117){
                    config.setLabel(4);
                    config.setSuffix(5);
                }else if (config.getConfigId()==118){
                    config.setLabel(5);
                    config.setSuffix(6);
                }else if (config.getConfigId()==119){
                    config.setLabel(6);
                    config.setSuffix(7);
                }else if (config.getConfigId()==120){
                    config.setLabel(7);
                    config.setSuffix(8);
                }
            }
        }

        return R.page(tStoreConfigList, tStoreConfigList.size());
    }



    /**
     * 获取当前门店的自定义   主题色
     */
    @GetMapping("/color")
    public R color(String appId) {

        WxAppThemeColorEntity wxAppThemeColor = wxAppThemeColorService.queryObject(appId);

        return R.ok().put("color", wxAppThemeColor);
    }

}
