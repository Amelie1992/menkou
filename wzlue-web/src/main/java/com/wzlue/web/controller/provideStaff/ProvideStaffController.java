
package com.wzlue.web.controller.provideStaff;

import cn.hutool.core.util.StrUtil;
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

import com.wzlue.provideStaff.entity.ProvideStaffEntity;
import com.wzlue.provideStaff.service.ProvideStaffService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;


/**
 * 供人信息
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-24 16:17:25
 */
@RestController
@RequestMapping("/provideStaff/providestaff")
public class ProvideStaffController extends AbstractController {
    @Autowired
    private ProvideStaffService provideStaffService;

    /**
     * 我的供人 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if(!isStore(query)){
            return R.page(new Object[]{},0);
        }
        List<ProvideStaffEntity> provideStaffList = provideStaffService.queryList(query);
        int total = provideStaffService.queryTotal(query);

        return R.page(provideStaffList, total);
    }


    /**
     * 门店供人审核 列表
     */
    @RequestMapping("/examineList")
    public R examineList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if(!isStore(query)){
            return R.page(new Object[]{},0);
        }
        List<ProvideStaffEntity> provideStaffList = provideStaffService.examineList(query);
        int total = provideStaffService.examineTotal(query);

        return R.page(provideStaffList, total);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("provideStaff:providestaff:info")
    public R info(@PathVariable("id") Long id) {
        ProvideStaffEntity provideStaff = provideStaffService.queryObject(id);

        return R.ok().put("provideStaff", provideStaff);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("provideStaff:providestaff:save")
    public R save(@RequestBody ProvideStaffEntity provideStaff) {
        String appId = getUser().getAppId();
        if (StrUtil.isBlank(appId)) {
            return R.error("非门店不具备供人权限");
        }
        provideStaff.setAppId(appId);
        provideStaffService.save(provideStaff);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("provideStaff:providestaff:update")
    public R update(@RequestBody ProvideStaffEntity provideStaff) {
        provideStaffService.update(provideStaff);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("provideStaff:providestaff:delete")
    public R delete(@RequestBody Long[] ids) {
        provideStaffService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 审核通过
     */
    @RequestMapping("/auditOk")
    @RequiresPermissions("provideStaff:providestaff:update")
    public R auditOk(@RequestBody Long[] ids) {

        provideStaffService.auditOk(ids);

        return R.ok();
    }

    /**
     * 审核失败
     */
    @RequestMapping("/auditNo")
    @RequiresPermissions("provideStaff:providestaff:update")
    public R auditNo(@RequestBody Map<String, Object> params) {

        provideStaffService.auditNo(params);

        return R.ok();
    }

}
