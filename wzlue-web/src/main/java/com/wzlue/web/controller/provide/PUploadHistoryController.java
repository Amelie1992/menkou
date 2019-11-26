
package com.wzlue.web.controller.provide;

import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzlue.recruitment.entity.PUploadHistoryEntity;
import com.wzlue.recruitment.entity.ProvidePersonnelEntity;
import com.wzlue.recruitment.service.PUploadHistoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.common.base.Query;

/**
 * 供人历史清单表
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 16:13:50
 */
@RestController
@RequestMapping("/provide/pUploadHistory")
public class PUploadHistoryController extends AbstractController {
    @Autowired
    private PUploadHistoryService pUploadHistoryService;

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
        //历史清单列表--当前登录人的appId
        List<PUploadHistoryEntity> pUploadHistoryList = pUploadHistoryService.queryList(query);
        for (PUploadHistoryEntity pu:pUploadHistoryList) {
            List<ProvidePersonnelEntity> providePersonnel = pu.getProvidePersonnel();
            for (ProvidePersonnelEntity pro:providePersonnel) {
                    if (pro.getSex()==1){
                        pro.setGenderName("男");
                    } else {
                        pro.setGenderName("女");
                    }
            }
        }
        int total = pUploadHistoryService.queryTotal(query);

        return R.page(pUploadHistoryList, total);
    }

    /**
     * 各门店向我供人（平台审核通过的供人）
     */
    @RequestMapping("/list2")
    public R list2(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<PUploadHistoryEntity> pUploadHistoryList = pUploadHistoryService.queryList(query);
        int total = pUploadHistoryService.queryTotal(query);

        return R.page(pUploadHistoryList, total);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("provide:pUploadHistory:info")
    public R info(@PathVariable("id") Long id) {
        PUploadHistoryEntity pUploadHistory = pUploadHistoryService.queryObject(id);

        return R.ok().put("pUploadHistory", pUploadHistory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("provide:pUploadHistory:save")
    public R save(@RequestBody PUploadHistoryEntity pUploadHistory) {
        pUploadHistoryService.save(pUploadHistory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
//    @RequiresPermissions("provide:pUploadHistory:update")
    public R update(@RequestBody PUploadHistoryEntity pUploadHistory) {
        pUploadHistoryService.update(pUploadHistory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("provide:pUploadHistory:delete")
    public R delete(@RequestBody Long[] ids) {
        pUploadHistoryService.deleteBatch(ids);

        return R.ok();
    }


    /**
     * 审核通过
     */
    @SysLog("auditOk")
    @RequestMapping("/auditOk")
    @RequiresPermissions("provide:pUploadHistory:audit")
    public R auditOk(@RequestBody Long[] ids) {

        pUploadHistoryService.auditOk(ids);

        return R.ok();
    }

    /**
     * 审核失败
     */
    @SysLog("auditNo")
    @RequestMapping("/auditNo")
    @RequiresPermissions("provide:pUploadHistory:audit")
    public R auditNo(@RequestBody Map<String, Object> params) {

        pUploadHistoryService.auditNo(params);

        return R.ok();
    }

    /**
     * 门店确认/拒绝供人列表
     */
    @RequestMapping("/confirm")
    @RequiresPermissions("recruitment:shoprecruitment:update")
    public R confirm(@RequestBody Map<String, Object> refuseMap) {

        pUploadHistoryService.confirm(refuseMap);

        return R.ok();
    }
}
