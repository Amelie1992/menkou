
package com.wzlue.web.controller.doorway;

import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;

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

import com.wzlue.doorway.entity.TDoorwayAboutUsEntity;
import com.wzlue.doorway.service.TDoorwayAboutUsService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;

import static com.wzlue.common.utils.EscapeUtil.unescape;


/**
 * 门口公司简介
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-09-28 19:08:21
 */
@RestController
@RequestMapping("/doorway/tdoorwayaboutus")
public class TDoorwayAboutUsController extends AbstractController {
    @Autowired
    private TDoorwayAboutUsService tDoorwayAboutUsService;

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
        query.put("limit", 1);
        List<TDoorwayAboutUsEntity> tDoorwayAboutUsList = tDoorwayAboutUsService.queryList(query);
        int total = tDoorwayAboutUsService.queryTotal(query);
        if (null != tDoorwayAboutUsList && tDoorwayAboutUsList.size() > 0) {
            for (TDoorwayAboutUsEntity aboutUs : tDoorwayAboutUsList) {
                if (null != aboutUs && null != aboutUs.getProfile()) {
		            aboutUs.setProfile(unescape(aboutUs.getProfile()));
                }
            }
        }

        return R.page(tDoorwayAboutUsList, total);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("doorway:tdoorwayaboutus:info")
    public R info(@PathVariable("id") Long id) {
        TDoorwayAboutUsEntity tDoorwayAboutUs = tDoorwayAboutUsService.queryObject(id);

        return R.ok().put("tDoorwayAboutUs", tDoorwayAboutUs);
    }

    /**
     * 保存
     */
    @SysLog("保存平台公司简介")
    @RequestMapping("/save")
    @RequiresPermissions("doorway:tdoorwayaboutus:save")
    public R save(@RequestBody TDoorwayAboutUsEntity tDoorwayAboutUs) {
        int i = tDoorwayAboutUsService.queryTotal(null);
        if (i > 0) {
            return R.error("只能保存一条公司简介");
        }
        tDoorwayAboutUs.setCreateId(getUserId().toString());
        tDoorwayAboutUsService.save(tDoorwayAboutUs);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改公司简介")
    @RequestMapping("/update")
    @RequiresPermissions("doorway:tdoorwayaboutus:update")
    public R update(@RequestBody TDoorwayAboutUsEntity tDoorwayAboutUs) {
        tDoorwayAboutUs.setUpdateId(getUserId().toString());
        tDoorwayAboutUsService.update(tDoorwayAboutUs);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("doorway:tdoorwayaboutus:delete")
    public R delete(@RequestBody Long[] ids) {
        tDoorwayAboutUsService.deleteBatch(ids);

        return R.ok();
    }

}
