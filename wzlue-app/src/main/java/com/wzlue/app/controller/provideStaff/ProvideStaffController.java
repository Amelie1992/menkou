
package com.wzlue.app.controller.provideStaff;

import cn.hutool.core.util.StrUtil;
import com.wzlue.app.common.annotation.Login;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.provideStaff.entity.ProvideStaffEntity;
import com.wzlue.provideStaff.service.ProvideStaffService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 供人信息
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-24 16:17:25
 */
@RestController
@RequestMapping("/app/providestaff")
public class ProvideStaffController {
    @Autowired
    private ProvideStaffService provideStaffService;

    /**
     * 我的供人 列表
     */
    @GetMapping("/provideList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", dataType = "string", value = "应用Id", paramType = "query", required = true),
            @ApiImplicitParam(name = "state", dataType = "int", value = "状态：1待审核；2通过；3拒绝", paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "int", value = "页码", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", dataType = "int", value = "一页几条", paramType = "query", defaultValue = "10")
    })
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<ProvideStaffEntity> provideStaffList = provideStaffService.queryList(query);
        int total = provideStaffService.queryTotal(query);

        return R.page(provideStaffList, total);
    }


    /**
     * 门店供人审核 列表
     */
    @GetMapping("/examineList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", dataType = "string", value = "应用Id", paramType = "query", required = true),
            @ApiImplicitParam(name = "state", dataType = "int", value = "状态：1待审核；2通过；3拒绝", paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "int", value = "页码", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", dataType = "int", value = "一页几条", paramType = "query", defaultValue = "10")
    })
    public R examineList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<ProvideStaffEntity> provideStaffList = provideStaffService.examineList(query);
        if (null != provideStaffList && provideStaffList.size() > 0) {
            for (ProvideStaffEntity provideStaff : provideStaffList) {
                if (provideStaff.getState() == 1 || provideStaff.getState() == 3) {//通过才展示供人方联系方式
                    provideStaff.setContacts(null);
                    provideStaff.setPhone(null);
                }
            }
        }
        int total = provideStaffService.examineTotal(query);

        return R.page(provideStaffList, total);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        ProvideStaffEntity provideStaff = provideStaffService.queryObject(id);

        return R.ok().put("provideStaff", provideStaff);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ProvideStaffEntity provideStaff) {
        provideStaffService.save(provideStaff);

        return R.ok();
    }

//    /**
//     * 审核通过
//     */
//    @PostMapping("/auditOk")
//    public R auditOk(@RequestBody Long id) {
//        Long[] ids = {id};
//        provideStaffService.auditOk(ids);
//
//        return R.ok();
//    }
//
//   /**
//     * 审核失败
//     */
//    @PostMapping("/auditNo")
//    public R auditNo(@RequestBody Long id) {
//        HashMap<String, Object> params = new HashMap<>();
//        Long[] ids = {id};
//        params.put("ids",ids);
//        provideStaffService.auditNo(params);
//
//        return R.ok();
//    }

    /**
     * 审核
     */
    @PostMapping("/audit")
    public R audit(@RequestBody Map<String, Object> params) {
        if (null != params) {
            if (null != params.get("id")) {
                Long id = Long.valueOf(params.get("id").toString());
                Long[] ids = {id};
                if (null != params.get("state")) {
                    Integer state = Integer.valueOf(params.get("state").toString());
                    if (state == 2) {
                        provideStaffService.auditOk(ids);
                    } else if (state == 3) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("ids", ids);
                        provideStaffService.auditNo(map);
                    }
                }
            }
        }

        return R.ok();
    }

}
