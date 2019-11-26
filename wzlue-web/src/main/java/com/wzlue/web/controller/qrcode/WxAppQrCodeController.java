
package com.wzlue.web.controller.qrcode;

import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzlue.common.utils.WxReturnCode;
import com.wzlue.wechat.config.WxMpConfiguration;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpQrcodeService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.qrcode.entity.WxAppQrCodeEntity;
import com.wzlue.qrcode.service.WxAppQrCodeService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;


/**
 * 带参二维码
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-18 11:47:43
 */
@Slf4j
@RestController
@RequestMapping("/qrcode/wxappqrcode")
public class WxAppQrCodeController extends AbstractController {
    @Autowired
    private WxAppQrCodeService wxAppQrCodeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("delFlag", 1);//未删除
        List<WxAppQrCodeEntity> wxAppQrCodeList = wxAppQrCodeService.queryList(query);
        int total = wxAppQrCodeService.queryTotal(query);

        return R.page(wxAppQrCodeList, total);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qrcode:wxappqrcode:info")
    public R info(@PathVariable("id") Long id) {
        WxAppQrCodeEntity wxAppQrCode = wxAppQrCodeService.queryObject(id);

        return R.ok().put("wxAppQrCode", wxAppQrCode);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("qrcode:wxappqrcode:save")
    public R save(@RequestBody WxAppQrCodeEntity wxAppQrCode) {
        wxAppQrCodeService.save(wxAppQrCode);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("qrcode:wxappqrcode:update")
    public R update(@RequestBody WxAppQrCodeEntity wxAppQrCode) {
        wxAppQrCode.setUpdateBy(getUserId());
        wxAppQrCode.setUpdateTime(new Date());
        wxAppQrCodeService.update(wxAppQrCode);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("qrcode:wxappqrcode:delete")
    public R delete(@RequestBody Long[] ids) {
        wxAppQrCodeService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/appList")
    public R appList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);
        }
        query.put("delFlag", 1);//未删除
        List<WxAppQrCodeEntity> wxAppQrCodeList = wxAppQrCodeService.queryAppList(query);
        int total = wxAppQrCodeService.queryAppTotal(query);

        return R.page(wxAppQrCodeList, total);
    }

    /**
     * 生成二维码
     */
    @SysLog("生成带参二维码")
    @PostMapping("/generate")
    @RequiresPermissions("qrcode:wxappqrcode:save")
    public R generate(@RequestBody WxAppQrCodeEntity wxAppQrCode) {
        //参数（1~10万 的场景值）
        Map<String, Object> map = new HashMap<>();
        map.put("appId", wxAppQrCode.getAppId());
        int total = wxAppQrCodeService.queryTotal(map);
        int sceneId = total + 1;

        try {
            //带参二维码
            WxMpQrcodeService wxMpQrcodeService = WxMpConfiguration.getMpService(wxAppQrCode.getAppId()).getQrcodeService();
            WxMpQrCodeTicket wxMpQrCodeTicket = wxMpQrcodeService.qrCodeCreateLastTicket(sceneId);
            String ticket = wxMpQrCodeTicket.getTicket();
            String encodeTicket = URLEncoder.encode(ticket, "GBK");

            wxAppQrCode.setSceneId(sceneId);
            wxAppQrCode.setQrCode(wxMpQrCodeTicket.getUrl());
            wxAppQrCode.setTicket(ticket);
            wxAppQrCode.setUrl(WxAppQrCodeEntity.SHOW_QRCODE_PATH + encodeTicket);
            wxAppQrCode.setCreateBy(getUserId());
            wxAppQrCodeService.save(wxAppQrCode);

        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("生成带参二维码失败appID:" + wxAppQrCode.getAppId() + ":" + e.getMessage());
            return WxReturnCode.wxErrorExceptionHandler(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return R.ok();
    }

}
