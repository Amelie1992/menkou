
package com.wzlue.web.controller.draw;

import com.wzlue.common.base.R;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.draw.entity.WxAppIntegralDrawEntity;
import com.wzlue.draw.service.WxAppIntegralDrawService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;


/**
 * 门店（自定义）积分抽奖活动   （付费开通）
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
@RestController
@RequestMapping("/draw/wxappintegraldraw")
public class WxAppIntegralDrawController extends AbstractController {
    @Autowired
    private WxAppIntegralDrawService wxAppIntegralDrawService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<WxAppIntegralDrawEntity> wxAppIntegralDrawList = wxAppIntegralDrawService.queryList(query);
        int total = wxAppIntegralDrawService.queryTotal(query);

        return R.page(wxAppIntegralDrawList, total);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{appId}")
    @RequiresPermissions("draw:wxappintegraldraw:info")
    public R info(@PathVariable("appId") String appId) {
        WxAppIntegralDrawEntity wxAppIntegralDraw = wxAppIntegralDrawService.queryObject(appId);

        return R.ok().put("wxAppIntegralDraw", wxAppIntegralDraw);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("draw:wxappintegraldraw:save")
    public R save(@RequestBody WxAppIntegralDrawEntity wxAppIntegralDraw) {
        wxAppIntegralDrawService.save(wxAppIntegralDraw);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("draw:wxappintegraldraw:update")
    public R update(@RequestBody WxAppIntegralDrawEntity wxAppIntegralDraw) {
        wxAppIntegralDraw.setUpdateBy(getUserId());
        wxAppIntegralDraw.setUpdateTime(new Date());
        wxAppIntegralDrawService.update(wxAppIntegralDraw);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("draw:wxappintegraldraw:delete")
    public R delete(@RequestBody String[] appIds) {
        wxAppIntegralDrawService.deleteBatch(appIds);

        return R.ok();
    }

    /**
     * 公众号 列表
     */
    @RequestMapping("/appList")
    public R appList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);
        }

        List<WxAppIntegralDrawEntity> wxAppIntegralDrawList = wxAppIntegralDrawService.queryAppList(query);
        int total = wxAppIntegralDrawService.queryAppTotal(query);

        return R.page(wxAppIntegralDrawList, total);
    }

    /**
     * 开通 积分抽奖功能
     */
    @RequestMapping("/open")
    @RequiresPermissions("draw:wxappintegraldraw:save")
    public R open(@RequestBody String[] appIds) {
        for (String appId : appIds) {
            WxAppIntegralDrawEntity wxAppIntegralDrawEntity = new WxAppIntegralDrawEntity();
            wxAppIntegralDrawEntity.setAppId(appId);
            wxAppIntegralDrawService.save(wxAppIntegralDrawEntity);
        }
        return R.ok();
    }

    /**
     * 启用 积分抽奖功能
     */
    @RequestMapping("/enable")
    @RequiresPermissions("draw:wxappintegraldraw:update")
    public R enable(@RequestBody String[] appIds) {
        for (String appId : appIds) {
            WxAppIntegralDrawEntity wxAppIntegralDrawEntity = new WxAppIntegralDrawEntity();
            wxAppIntegralDrawEntity.setAppId(appId);
            wxAppIntegralDrawEntity.setState(1);
            wxAppIntegralDrawEntity.setUpdateTime(new Date());
            wxAppIntegralDrawService.update(wxAppIntegralDrawEntity);
        }
        return R.ok();
    }

    /**
     * 禁用 积分抽奖功能
     */
    @RequestMapping("/forbid")
    @RequiresPermissions("draw:wxappintegraldraw:update")
    public R forbid(@RequestBody String[] appIds) {
        for (String appId : appIds) {
            WxAppIntegralDrawEntity wxAppIntegralDrawEntity = new WxAppIntegralDrawEntity();
            wxAppIntegralDrawEntity.setAppId(appId);
            wxAppIntegralDrawEntity.setState(2);
            wxAppIntegralDrawEntity.setUpdateTime(new Date());
            wxAppIntegralDrawService.update(wxAppIntegralDrawEntity);
        }
        return R.ok();
    }
}
