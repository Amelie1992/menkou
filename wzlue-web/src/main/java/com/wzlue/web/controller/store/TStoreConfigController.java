
package com.wzlue.web.controller.store;

import com.alibaba.druid.util.StringUtils;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.store.entity.TStoreConfigEntity;
import com.wzlue.store.service.TStoreConfigService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;


/**
 * 门店配置表
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 09:44:21
 */
@RestController
@RequestMapping("/store/tstoreconfig")
public class TStoreConfigController extends AbstractController {
    @Autowired
    private TStoreConfigService tStoreConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        String configType = org.apache.commons.lang3.StringUtils.EMPTY;
        //查询列表数据
        Query query = new Query(params);


        List<TStoreConfigEntity> tStoreConfigList = tStoreConfigService.queryList(query);
        int total = tStoreConfigService.queryTotal(query);
        return R.page(tStoreConfigList, total);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{configId}")
    @RequiresPermissions("store:tstoreconfig:info")
    public R info(@PathVariable("configId") Long configId) {
        TStoreConfigEntity tStoreConfig = tStoreConfigService.queryObject(configId);

        return R.ok().put("tStoreConfig", tStoreConfig);
    }

    /**
     * 保存
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("store:tstoreconfig:save")
    public R save(@RequestBody TStoreConfigEntity tStoreConfig) {
        tStoreConfigService.save(tStoreConfig);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("store:tstoreconfig:update")
    public R update(@RequestBody TStoreConfigEntity tStoreConfig) {
        tStoreConfigService.update(tStoreConfig);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("store:tstoreconfig:delete")
    public R delete(@RequestBody Long[] configIds) {
        tStoreConfigService.deleteBatch(configIds);

        return R.ok();
    }

    /**
     * 查全部
     */
    @RequestMapping("/all")
    public Map<String,  List<TStoreConfigEntity>> all(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("sidx", "config_sort");
        query.put("order", "asc");
        query.remove("limit");  //不分页
        query.remove("offset");

//            {'value': 1, 'label': '标签'},
//            {'value': 2, 'label': '待遇'},
//            {'value': 3, 'label': '规模'},
//            {'value': 4, 'label': '企业年限'},
//            {'value': 5, 'label': '返费金额'},
//            {'value': 6, 'label': '岗位类型'}
        query.put("configType", "1");
        List<TStoreConfigEntity> labels = tStoreConfigService.queryList(query);

        query.put("configType", "2");
        List<TStoreConfigEntity> treatments = tStoreConfigService.queryList(query);

        query.put("configType", "3");
        List<TStoreConfigEntity> scales = tStoreConfigService.queryList(query);

        query.put("configType", "4");
        List<TStoreConfigEntity> years = tStoreConfigService.queryList(query);

        query.put("configType", "5");
        List<TStoreConfigEntity> feeReturns = tStoreConfigService.queryList(query);

        query.put("configType", "6");
        List<TStoreConfigEntity> jobs = tStoreConfigService.queryList(query);


        Map<String,  List<TStoreConfigEntity>> map = new HashMap<>();
        map.put("labels", labels);
        map.put("scales", scales);
        map.put("treatments", treatments);
        map.put("feeReturns", feeReturns);
        map.put("years", years);
        map.put("jobs", jobs);
        return map;
    }

}
