package com.wzlue.web.controller.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzlue.common.config.SMSConfig;
import com.wzlue.common.exception.RRException;
import com.wzlue.common.utils.ConfigConstant;
import com.wzlue.common.utils.WxReturnCode;
import com.wzlue.smsCode.entity.WxAppSmsAccountEntity;
import com.wzlue.smsCode.service.WxAppSmsAccountService;
import com.wzlue.sys.AgentDemo.ApiDemo4Java;
import com.wzlue.sys.dao.SysImageDao;
import com.wzlue.sys.entity.SysImageEntity;
import com.wzlue.sys.entity.SysUserEntity;
import com.wzlue.wechat.config.WxMpConfiguration;
import com.wzlue.wechat.entity.SysImageEntity2;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.service.WxAppService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;

import static com.wzlue.sys.AgentDemo.ApiDemo4Java.ACCOUNT_APIKEY;
import static com.wzlue.sys.AgentDemo.ApiDemo4Java.ACCOUNT_SID;


/**
 * 微信应用
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-22 11:11:41
 */
@Slf4j
@RestController
@RequestMapping("/wechat/wxapp")
public class WxAppController extends AbstractController {
    @Autowired
    private WxAppService wxAppService;
    @Autowired
    private SysImageDao sysImageDao;
    @Autowired
    private WxAppSmsAccountService wxAppSmsAccountService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);

        }
        List<WxAppEntity> wxAppList = wxAppService.queryList(query);
        int total = wxAppService.queryTotal(query);

        return R.page(wxAppList, total);
    }

    /**
     * 下拉菜单 列表 开启的
     */
    @GetMapping("/selectlist")
    public R selectlist(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);

        }

        List<WxAppEntity> wxAppList = wxAppService.selectlist(query);

        return R.ok().put("wxAppList", wxAppList);
    }

    /**
     * 下拉菜单 列表
     */
    @GetMapping("/selectAll")
    public R selectAll(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);

        }
        query.put("enable", "ALL");
        List<WxAppEntity> wxAppList = wxAppService.selectlist(query);

        return R.ok().put("wxAppList", wxAppList);
    }

    /**
     * 下拉菜单 列表 没有绑定过的
     */
    @GetMapping("/selectlistNotApp")
    public R selectlistNotApp() {
        //查询列表数据
        List<WxAppEntity> wxAppList = wxAppService.selectlistNotApp();

        return R.ok().put("wxAppList", wxAppList);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("wechat:wxapp:info")
    public R info(@PathVariable("id") String id) {
        WxAppEntity wxApp = wxAppService.queryObject(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", "wx_app");
        map.put("remark", wxApp.getId());
        List<SysImageEntity> sysImageEntities = sysImageDao.queryList(map);

        return R.ok().put("wxApp", wxApp).put("checkFile", sysImageEntities);
    }

    /**
     * 保存
     */
    @SysLog("保存微信应用")
    @PostMapping("/save")
    @RequiresPermissions("wechat:wxapp:save")
    public R save(@RequestBody WxAppEntity wxApp) {
        wxApp.setAppType(ConfigConstant.WX_APP_TYPE_2);
        try {
            WxMpQrcodeService wxMpQrcodeService = WxMpConfiguration.getMpService(wxApp).getQrcodeService();
            String sceneStr = "1";
            WxMpQrCodeTicket wxMpQrCodeTicket = wxMpQrcodeService.qrCodeCreateLastTicket(sceneStr);
            wxApp.setQrCode(wxMpQrCodeTicket.getUrl());
            wxApp.setDelFlag("0");
            wxApp.setCreateId(getUserId().toString());
            wxApp.setState("1");
            wxApp.setQualified("0");
            wxAppService.save(wxApp);

            //校验文件
            List<SysImageEntity2> checkFile = wxApp.getCheckFile();
            if (null != checkFile && checkFile.size() > 0) {
                for (SysImageEntity2 image : checkFile) {
                    SysImageEntity imageEntity = new SysImageEntity();
                    imageEntity.setPicUrl(image.getPicUrl());
                    imageEntity.setType("wx_app");
                    imageEntity.setRemark(wxApp.getId());
                    sysImageDao.save(imageEntity);
                }
            }

        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("新增微信账号配置失败appID:" + wxApp.getId() + ":" + e.getMessage());
            throw new RRException("新增微信账号配置失败appID:" + wxApp.getId());
        }
        return R.ok();
    }

    /**
     * 修改微信账号配置
     *
     * @param wxApp 微信账号配置
     * @return R
     */
    @SysLog("修改微信账号配置")
    @RequiresPermissions("wechat:wxapp:update")
    @PostMapping("/update")
    public R updateById(@RequestBody WxAppEntity wxApp) {
        try {
            WxMpQrcodeService wxMpQrcodeService = WxMpConfiguration.getMpService(wxApp).getQrcodeService();
            String sceneStr = "1";
            WxMpQrCodeTicket wxMpQrCodeTicket = wxMpQrcodeService.qrCodeCreateLastTicket(sceneStr);
            wxApp.setQrCode(wxMpQrCodeTicket.getUrl());
            wxApp.setUpdateId(getUserId().toString());
            wxAppService.update(wxApp);

            //校验文件
            List<SysImageEntity2> checkFile = wxApp.getCheckFile();
            if (null != checkFile && checkFile.size() > 0) {
                sysImageDao.deleteByTypeAndRemark("wx_app", wxApp.getId());
                for (SysImageEntity2 image : checkFile) {
                    SysImageEntity imageEntity = new SysImageEntity();
                    imageEntity.setPicUrl(image.getPicUrl());
                    imageEntity.setType("wx_app");
                    imageEntity.setRemark(wxApp.getId());
                    sysImageDao.save(imageEntity);
                }
            }

        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("修改微信账号配置失败appID:" + wxApp.getId() + ":" + e.getMessage());
        }
        return R.ok();
    }

    @SysLog("生成公众号二维码")
    @PostMapping("/qrCode")
    @RequiresPermissions("wechat:wxapp:update")
    public R createQrCode(@RequestBody Map<String, String> param) {
        try {
            String id = param.get("id");
            String sceneStr = param.get("sceneStr");
            WxMpQrcodeService wxMpQrcodeService = WxMpConfiguration.getMpService(id).getQrcodeService();
            WxMpQrCodeTicket wxMpQrCodeTicket = wxMpQrcodeService.qrCodeCreateLastTicket(sceneStr);
            WxAppEntity wxApp = new WxAppEntity();
            wxApp.setId(id);
            wxApp.setQrCode(wxMpQrCodeTicket.getUrl());
            wxAppService.update(wxApp);
            return R.ok();
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("生成公众号二维码失败appID:" + param.get("id") + ":" + e.getMessage());
            return WxReturnCode.wxErrorExceptionHandler(e);
        }
    }


    /**
     * 删除
     */
    @SysLog("删除微信应用")
    @DeleteMapping("/delete")
    @RequiresPermissions("wechat:wxapp:delete")
    public R delete(@RequestBody String[] ids) {
        wxAppService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 暂停
     */
    @SysLog("暂停/启用 微信应用")
    @PostMapping("/enabled")
    @RequiresPermissions("wechat:wxapp:enabled")
    public R disabled(@RequestBody String[] ids) {

        wxAppService.updateEnabled(ids);

        return R.ok();
    }

    /**
     * 暂停
     */
    @SysLog("资质开启/关闭")
    @PostMapping("/qualified")
    @RequiresPermissions("wechat:wxapp:qualified")
    public R qualified(@RequestBody String[] ids) {

        wxAppService.updateQualified(ids);
        return R.ok();
    }

    /**
     * 暂停
     */
    @SysLog("编辑门店资质")
    @PostMapping("/editQualified")
    @RequiresPermissions("wechat:wxapp:editQualified")
    public R editQualified(@RequestBody WxAppEntity wxApp) {
        wxApp.setUpdateId(getUserId().toString());
        wxAppService.editQualified(wxApp);

        return R.ok();
    }


    /**
     * 短信余量 列表
     */
    @GetMapping("/smsRemainder")
    public R smsRemainder(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);

        }
        if (null != query.get("appId") && query.get("appId").toString().equals("")) {
            query.put("appId", null);
        }
        List<WxAppEntity> wxAppList = wxAppService.queryList(query);
        int total = wxAppService.queryTotal(query);

        List<Map<String, Object>> list = new ArrayList<>();
        if (null != wxAppList && wxAppList.size() > 0) {
            for (WxAppEntity wxApp : wxAppList) {
                Map<String, Object> map = new HashMap<>();
                map.put("appname", wxApp.getName());
                String balance = "";

//                String mendian = SMSConfig.getConfig(wxApp.getId());
//                String sid = SMSConfig.getConfig(mendian + ACCOUNT_SID);
//                String apikey = SMSConfig.getConfig(mendian + ACCOUNT_APIKEY);
                WxAppSmsAccountEntity smsAccount = wxAppSmsAccountService.queryObject(wxApp.getId());

                if (null != smsAccount) {
//                    String resultJson = ApiDemo4Java.queryUser(sid, apikey);
                    String resultJson = ApiDemo4Java.queryUser(smsAccount.getAccountSid(), smsAccount.getAccountApikey());
                    Map resultMap = JSON.parseObject(resultJson, Map.class);
                    String code = resultMap.get("code").toString();
                    if (code.equals("0")) {
                        Map user = (Map) resultMap.get("user");
                        balance = user.get("balance").toString();
                    }
                }


                map.put("remainder", balance);
                list.add(map);
            }
        }

        return R.page(list, total);
    }
}
