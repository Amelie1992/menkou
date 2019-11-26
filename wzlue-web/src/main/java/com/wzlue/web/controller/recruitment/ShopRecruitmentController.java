
package com.wzlue.web.controller.recruitment;

import com.wzlue.common.annotation.RepeatSubmit;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.common.utils.AjaxResult;
import com.wzlue.common.utils.ExcelUtil;
import com.wzlue.jobApplication.entity.ClockInEntity;
import com.wzlue.jobApplication.service.ClockInService;
import com.wzlue.jobApplication.service.JobApplicationService;
import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import com.wzlue.recruitment.service.FeeReturnService;
import com.wzlue.recruitment.service.ShopRecruitmentService;
import com.wzlue.web.controller.sys.AbstractController;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 门店招聘
 *
 * @author wzlue
 * @email wzlue.com/exist
 * @date 2019-07-30 10:49:06
 */
@RestController
@RequestMapping("/recruitment/shoprecruitment")
public class ShopRecruitmentController extends AbstractController {
    @Autowired
    private ShopRecruitmentService shopRecruitmentService;
    @Autowired
    private JobApplicationService jobApplicationService;
    @Autowired
    private ClockInService clockInService;
    @Autowired
    private FeeReturnService feeReturnService;



    @Value("${fileupload.filepath}")
    String filePath;
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("delFlag", 2);
        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);
        }
        List<ShopRecruitmentEntity> shopRecruitmentList = shopRecruitmentService.queryList(query);
        int total = shopRecruitmentService.queryTotal(query);

        return R.page(shopRecruitmentList, total);
    }

    //平台招聘（belongTo=2 包括审核通过不通过的）
    @RequestMapping("/list2")
    public R list2(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("delFlag", 2);

        List<ShopRecruitmentEntity> shopRecruitmentList = shopRecruitmentService.queryList(query);
        int total = shopRecruitmentService.queryTotal(query);

        return R.page(shopRecruitmentList, total);
    }


    @RequiresPermissions("recruitment:shoprecruitment:export")
    @PostMapping("/export/{type}")
    @ResponseBody
    public AjaxResult export(@PathVariable("type") String type, @RequestBody Map<String, Object> params)
    {

        List<ShopRecruitmentEntity> list = null;
        if("0".equals(type)) {
            //查询列表数据
            Query query = new Query(params);
            if(isStore(query)){
                list = shopRecruitmentService.queryListAll(query);
            }
        } else {
            List ids = (ArrayList) params.get("ids");
            Object[] idObjs = ids.toArray() ;
            list = shopRecruitmentService.queryListByIds(idObjs);
        }
        ExcelUtil<ShopRecruitmentEntity> util = new ExcelUtil<ShopRecruitmentEntity>(ShopRecruitmentEntity.class);
        return util.exportExcel(list, "招聘数据",filePath);
    }


    /**
     * 平台招聘列表
     */
    @RequestMapping("/platFormlist")
    public R platFormlist(@RequestParam Map<String, Object> params) {
        //查询平台招聘的列表数据
        Query query = new Query(params);

        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);
        }
        //平台不包含当前登录门店的所有审核通过的招聘信息
        List<ShopRecruitmentEntity> shopRecruitmentList = shopRecruitmentService.queryPlatFormList(query);
        int total = shopRecruitmentService.queryPlatFormTotal(query);

        return R.page(shopRecruitmentList, total);
    }

    /**
     * 列表关联打卡
     */
    @RequestMapping("/colockList")
    public R colockList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        //门店数据权限
        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);
        }
        query.put("belongTo", 1);//归属门店
        query.put("delFlag", 2);//未删除
        //query.put("flag",1);
        List<ShopRecruitmentEntity> shopRecruitmentList = shopRecruitmentService.queryList(query);//招聘列表
        for (ShopRecruitmentEntity shopRecruitment : shopRecruitmentList) {
            Map<String, Object> map = new HashMap<>();
            map.put("shopRecruitmentId", shopRecruitment.getId());
            ClockInEntity clockIn = clockInService.queryRecruitClock(shopRecruitment.getId());//招聘打卡是否存在
            if (clockIn == null || clockIn.getId() == null) {
                shopRecruitment.setClockInFlag(1);//不存在打卡设置
            } else {
                shopRecruitment.setClockInFlag(2);//存在打卡设置
            }
            //根据招聘id查出招聘已入职员工的数量
            int num = jobApplicationService.fourEntry(map);
            shopRecruitment.setEntryNum(num);
        }
        int total = shopRecruitmentService.queryTotal(query);
        return R.page(shopRecruitmentList, total);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("recruitment:shoprecruitment:info")
    public R info(@PathVariable("id") Long id) {
        ShopRecruitmentEntity shopRecruitment = shopRecruitmentService.queryObject(id);

        return R.ok().put("shopRecruitment", shopRecruitment);
    }

    /**
     * 保存
     */

    @RequestMapping("/save")
    @RequiresPermissions("recruitment:shoprecruitment:save")
    @RepeatSubmit
    public R save(@RequestBody ShopRecruitmentEntity shopRecruitment) {
        if (null != getUser() && StringUtils.isBlank(getUser().getAppId())) {
            return R.error("请先配置您的门店信息，再尝试发布招聘！");
        }
        shopRecruitment.setAppId(getUser().getAppId());
        shopRecruitment.setCreateId(getUserId().toString());
        shopRecruitmentService.save(shopRecruitment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("recruitment:shoprecruitment:update")
    public R update(@RequestBody ShopRecruitmentEntity shopRecruitment) {
        shopRecruitment.setUpdateId(getUserId().toString());
        shopRecruitmentService.update(shopRecruitment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("recruitment:shoprecruitment:delete")
    public R delete(@RequestBody Long[] ids) {
        shopRecruitmentService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 开启
     */
    @SysLog("start")
    @RequestMapping("/start/{belongto}")
    @RequiresPermissions("recruitment:shoprecruitment:update")
    public R start(@RequestBody Long[] ids, @PathVariable("belongto") Integer belongto) {
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        if (null != belongto && belongto == 1) {    //门店
            map.put("shopSuspendFlag", 1);
        } else if (null != belongto && belongto == 2) { //平台
            map.put("platformSuspendFlag", 1);
        }
        shopRecruitmentService.updateShopSuspendFlag(map);

        return R.ok();
    }

    /**
     * 暂停
     */
    @SysLog("end")
    @RequestMapping("/end/{belongto}")
    @RequiresPermissions("recruitment:shoprecruitment:update")
    public R end(@RequestBody Long[] ids, @PathVariable("belongto") Integer belongto) {
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        if (null != belongto && belongto == 1) {    //门店
            map.put("shopSuspendFlag", 2);
        } else if (null != belongto && belongto == 2) { //平台
            map.put("platformSuspendFlag", 2);
        }
        shopRecruitmentService.updateShopSuspendFlag(map);

        return R.ok();
    }


    /**
     * 审核通过批量
     */
    @SysLog("审核通过批量")
    @RequestMapping("/toExamineSuccessPl")
    @RequiresPermissions("recruitment:shoprecruitment:toExamineSuccessPl")
    public R toExamineSuccessPl(@RequestBody Long[] ids) {
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("reviewState", 2);
        map.put("stateFlag", 1);
        int i = shopRecruitmentService.queryTotalByIds(map);
        if (i < 1) {
            return R.error(1, "选择至少一条状态为待审核的数据");
        }
        shopRecruitmentService.toExamineSuccessPl(map);
        return R.ok();
    }

    /**
     * 审核不通过批量
     */
    @SysLog("审核不通过批量")
    @RequestMapping("/toExamineFailedPl")
    @RequiresPermissions("recruitment:shoprecruitment:toExamineFailedPl")
    public R toExamineFailedPl(@RequestBody Map<String, Object> map) {


        map.put("reviewState", 3);
        map.put("stateFlag", 1);
        int i = shopRecruitmentService.queryTotalByIds(map);
        if (i < 1) {
            return R.error(1, "选择至少一条状态为待审核的数据");
        }
        shopRecruitmentService.toExamineFailedPl(map);
        return R.ok();
    }


    /**
     * 检验是否重复转发
     */
    @RequestMapping("/exist/{id}")
    @RequiresPermissions("recruitment:shoprecruitment:info")
    public R exist(@PathVariable("id") Long id) {
        Integer result = shopRecruitmentService.exist(id);

        return R.ok().put("result", result);
    }


    /**
     * 列表
     */
    @RequestMapping("/listByPlatform")
    public R listByPlatform(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        query.put("delFlag", 2);
        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);
        }
        List<ShopRecruitmentEntity> shopRecruitmentList = shopRecruitmentService.queryListByPlatform(query);
        int total = shopRecruitmentService.queryTotalByPlatform(query);

        return R.page(shopRecruitmentList, total);
    }


    /**
     * 保证金
     *
     * @param
     * @return
     */
    @SysLog("earnest")
    @RequestMapping("/earnest")
    @RequiresPermissions("recruitment:shoprecruitment:earnest")
    public R earnest(@RequestBody Map<String, Object> map) {

        ShopRecruitmentEntity entity = new ShopRecruitmentEntity();

        entity.setId(Long.valueOf(map.get("id").toString()));
        entity.setIsEarnest(map.get("type").toString());

        ShopRecruitmentEntity entity1 = shopRecruitmentService.queryObject(entity.getId());

        if (null == entity.getId() && null == entity.getHasEarnest())
            return R.error("保证金更新错误");

        // 不是缴纳保证金，不能修改保证金
        if (entity1.getHasEarnest() != 1)
            return R.error("不是缴纳保证金，不能修改保证金");

        shopRecruitmentService.updateaErnest(entity);

        return R.ok();
    }
}
