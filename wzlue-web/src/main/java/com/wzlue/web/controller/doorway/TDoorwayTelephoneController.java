
package com.wzlue.web.controller.doorway;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;

import java.util.HashMap;
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

import com.wzlue.doorway.entity.TDoorwayTelephoneEntity;
import com.wzlue.doorway.service.TDoorwayTelephoneService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;


/**
 * 门口电话表
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 16:08:30
 */
@RestController
@RequestMapping("/doorway/tdoorwaytelephone")
public class TDoorwayTelephoneController extends AbstractController {
    @Autowired
    private TDoorwayTelephoneService tDoorwayTelephoneService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        if(!isStore(query)){
            return R.page(new Object[]{},0);
        }
//        query.put("limit",1);
        List<TDoorwayTelephoneEntity> tDoorwayTelephoneList = tDoorwayTelephoneService.queryListByParam(query);
        int total = tDoorwayTelephoneService.queryTotalByParam(query);

        return R.page(tDoorwayTelephoneList, total);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("doorway:tdoorwaytelephone:info")
    public R info(@PathVariable("id") Long id) {
        TDoorwayTelephoneEntity tDoorwayTelephone = tDoorwayTelephoneService.queryObject(id);

        return R.ok().put("tDoorwayTelephone", tDoorwayTelephone);
    }

    /**
     * 保存
     */
    @SysLog("保存平台电话")
    @RequestMapping("/save")
    @RequiresPermissions("doorway:tdoorwaytelephone:save")
    public R save(@RequestBody TDoorwayTelephoneEntity tDoorwayTelephone) {


//        int i = tDoorwayTelephoneService.queryTotalByParam(null);
//        if (i > 0) {
//            return R.error("只能保存一条电话信息");
//        }


        tDoorwayTelephone.setCreateId(getUserId().toString());
        tDoorwayTelephoneService.save(tDoorwayTelephone);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改平台电话")
    @RequestMapping("/update")
    @RequiresPermissions("doorway:tdoorwaytelephone:update")
    public R update(@RequestBody TDoorwayTelephoneEntity tDoorwayTelephone) {
        tDoorwayTelephone.setUpdateId(getUserId().toString());
        tDoorwayTelephoneService.update(tDoorwayTelephone);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除平台电话")
    @RequestMapping("/delete")
    @RequiresPermissions("doorway:tdoorwaytelephone:delete")
    public R delete(@RequestBody Long[] ids) {
        tDoorwayTelephoneService.deleteBatch(ids);

        return R.ok();
    }

}
