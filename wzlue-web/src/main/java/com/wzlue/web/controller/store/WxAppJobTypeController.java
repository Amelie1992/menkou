
package com.wzlue.web.controller.store;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.R;

import java.util.*;

import com.wzlue.common.utils.Constant;
import com.wzlue.store.entity.TStoreConfigEntity;
import com.wzlue.store.service.TStoreConfigService;
import com.wzlue.sys.entity.SysUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.store.entity.WxAppJobTypeEntity;
import com.wzlue.store.service.WxAppJobTypeService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;


/**
 * 门店（自定义）岗位种类
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-04 15:38:28
 */
@RestController
@RequestMapping("/store/wxappjobtype")
public class WxAppJobTypeController extends AbstractController {
    @Autowired
    private WxAppJobTypeService wxAppJobTypeService;
    @Autowired
    private TStoreConfigService tStoreConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);
        }

        List<WxAppJobTypeEntity> wxAppJobTypeList = wxAppJobTypeService.queryList(query);
        int total = wxAppJobTypeService.queryTotal(query);

        return R.page(wxAppJobTypeList, total);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{appId}")
    @RequiresPermissions("store:wxappjobtype:info")
    public R info(@PathVariable("appId") String appId) {
        WxAppJobTypeEntity wxAppJobType = wxAppJobTypeService.queryObject(appId);

        return R.ok().put("wxAppJobType", wxAppJobType);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("store:wxappjobtype:save")
    public R save(@RequestBody WxAppJobTypeEntity wxAppJobType) {
        if (StrUtil.isBlank(wxAppJobType.getAppId())) {
            return R.error("appId不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("appId", wxAppJobType.getAppId());
        int i = wxAppJobTypeService.queryTotal(map);
        if (i > 0) {
            return R.error("门店只能保存一条岗位种类信息");
        }

        wxAppJobTypeService.save(wxAppJobType);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("store:wxappjobtype:update")
    public R update(@RequestBody WxAppJobTypeEntity wxAppJobType) {
        wxAppJobType.setUpdateTime(new Date());
        wxAppJobTypeService.update(wxAppJobType);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("store:wxappjobtype:delete")
    public R delete(@RequestBody String[] appIds) {
        wxAppJobTypeService.deleteBatch(appIds);

        return R.ok();
    }

    /**
     * 获取当前登陆门店的自定义岗位种类
     */
    @RequestMapping("/custom")
    public R custom() {

        List<TStoreConfigEntity> tStoreConfigList = new ArrayList<>();

        SysUserEntity user = getUser();

        if (getUserId() == Constant.SUPER_ADMIN) {  //admin

            Map<String, Object> map = new HashMap<>();
            map.put("configType", 7);
            map.put("sidx", "config_sort");
            map.put("order", "asc");
            map.put("page", 1);
            map.put("offset", 0);
            map.put("limit", 10000);
            tStoreConfigList = tStoreConfigService.queryList(map);

        } else if (StrUtil.isNotBlank(user.getAppId()) && StrUtil.equals(user.getStore(), "1")) {  //门店

            WxAppJobTypeEntity wxAppJobType = wxAppJobTypeService.queryObject(user.getAppId());

            if (null!=wxAppJobType) {
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

        return R.ok().put("jobTypes", tStoreConfigList);
    }


}
