
package com.wzlue.web.controller.jobApplication;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;

import java.math.BigDecimal;
import java.util.*;

import com.wzlue.common.utils.AjaxResult;
import com.wzlue.common.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.jobApplication.entity.JobApplicationEntity;
import com.wzlue.jobApplication.service.JobApplicationService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;


/**
 * 用户报名表（求职/应聘）
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 17:10:18
 */
@Slf4j
@RestController
@RequestMapping("/jobApplication/jobapplication")
public class JobApplicationController extends AbstractController {
    @Autowired
    private JobApplicationService jobApplicationService;

    @Value("${fileupload.filepath}")
    String filePath;

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
        List<JobApplicationEntity> jobApplicationList = jobApplicationService.queryList(query);
        int total = jobApplicationService.queryTotal(query);
        Object appIdObj = query.get("appId");
        if (appIdObj != null) {
            String appId = appIdObj.toString();
            log.info("appId:{}",appId);
            if (StrUtil.isNotBlank(appId)){
                for (JobApplicationEntity job:jobApplicationList) {
                    if (job.getShopRecruitmentEntity()!=null && !job.getShopRecruitmentEntity().getAppId().equals(appId)){//招聘的appid不是当前登录门店，招聘信息不展示
                        job.setShopRecruitmentEntity(null);
                    }
                }
            }
        }

        return R.page(jobApplicationList, total);
    }


    @RequiresPermissions("jobApplication:jobapplication:export")
    @PostMapping("/export/{type}")
    @ResponseBody
    public AjaxResult export(@PathVariable("type") String type, @RequestBody Map<String, Object> params)
    {

        List<JobApplicationEntity> list = null;
        if("0".equals(type)) {
            //查询列表数据
            Query query = new Query(params);
            if(isStore(query)){
                list = jobApplicationService.queryListAll(query);
            }
        } else {
            List ids = (ArrayList) params.get("ids");
            Object[] idObjs = ids.toArray() ;
            list = jobApplicationService.queryListByIds(idObjs);
        }
        ExcelUtil<JobApplicationEntity> util = new ExcelUtil<JobApplicationEntity>(JobApplicationEntity.class);
        return util.exportExcel(list, "应聘数据",filePath);
    }


    /**
     * 平台分配的报名列表   & 平台报名（belongTo=2）
     */
    @RequestMapping("/list2")
    public R list2(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<JobApplicationEntity> jobApplicationList = jobApplicationService.queryList(query);
        int total = jobApplicationService.queryTotal(query);

        return R.page(jobApplicationList, total);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("jobApplication:jobapplication:info")
    public R info(@PathVariable("id") Long id) {
        JobApplicationEntity jobApplication = jobApplicationService.queryObject(id);

        return R.ok().put("jobApplication", jobApplication);
    }

    /**
     * 平台分配的报名 & 是否关联本门店发布的平台招聘
     */
    @RequestMapping("/info2/{id}")
    @RequiresPermissions("jobApplication:jobapplication:info")
    public R info2(@PathVariable("id") Long id) {
        JobApplicationEntity jobApplication = jobApplicationService.queryObject(id);
        Boolean flag = false;
        if (null != getUser() && StringUtils.isNotBlank(getUser().getAppId()) && null != jobApplication
                && null != jobApplication.getShopRecruitmentEntity() && StringUtils.isNotBlank(jobApplication.getShopRecruitmentEntity().getAppId())) {
            if (jobApplication.getShopRecruitmentEntity().getAppId().equals(getUser().getAppId())) {
                flag = true;
            }
        }

        return R.ok().put("jobApplication", jobApplication).put("flag", flag);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("jobApplication:jobapplication:save")
    public R save(@RequestBody JobApplicationEntity jobApplication) {
        jobApplicationService.save(jobApplication);

        return R.ok();
    }

    /**
     * 余额提现
     */
    @SysLog("余额提现")
    @RequestMapping("/withdraw")
    @RequiresPermissions("jobApplication:jobapplication:update")
    public R withdraw(Long id, BigDecimal amount) {
        JobApplicationEntity jobApplication = jobApplicationService.queryObject(id);
        jobApplicationService.updateBalance(jobApplication, amount);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("jobApplication:jobapplication:update")
    public R update(@RequestBody JobApplicationEntity jobApplication) {
        jobApplicationService.update(jobApplication);

        return R.ok();
    }

    /**
     * 门店反馈
     * @param jobApplication
     * @return
     */
    @RequestMapping("/shopFeedback")
    public R shopFeedback(JobApplicationEntity jobApplication) {
        //接收
        if (jobApplication.getShopFeedback()==1){
            //修改门店的数据状态
            jobApplicationService.update(jobApplication);
            return R.ok();
        } else {//拒收
            //修改门店的数据
            /*jobApplication.setShopFeedback(jobApplication.getShopFeedback());//门店反馈
            jobApplication.setShopRemark(jobApplication.getShopRemark());//门店反馈备注*/
            jobApplicationService.update(jobApplication);
            //jobApplicationService.delete(jobApplication.getId());//删除

            //门店应聘信息
            JobApplicationEntity jobApplication1 = jobApplicationService.queryObject(jobApplication.getId());

            //修改平台的数据
            JobApplicationEntity jobApplication2 = jobApplicationService.queryObject(jobApplication1.getPlatformJobId());
            jobApplication2.setBelongTo(2);
            jobApplication2.setDistributionStatus(0);
            jobApplication2.setLatestAppId("");
            jobApplicationService.update(jobApplication2);
            return R.ok();
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("jobApplication:jobapplication:delete")
    public R delete(@RequestBody Long[] ids) {
        jobApplicationService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 反馈
     */
    @RequestMapping("/feedback")
    @RequiresPermissions("jobApplication:jobapplication:update")
    public R feedback(@RequestBody JobApplicationEntity jobApplication) {
        jobApplicationService.feedback(jobApplication);

        return R.ok();
    }


    /**
     * 分配
     */
    @RequestMapping("/distribution")
    @RequiresPermissions("jobApplication:jobapplication:update")
    public R distribution(@RequestBody JobApplicationEntity jobApplication) {
        jobApplicationService.distribution(jobApplication);

        return R.ok();
    }


    /**
     * 列表
     */
    @RequestMapping("/listByPlatform")
    public R listByPlatform(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);
        }

        List<JobApplicationEntity> jobApplicationList = jobApplicationService.queryListByPlatform(query);
        int total = jobApplicationService.queryTotalByPlatform(query);

        return R.page(jobApplicationList, total);
    }


    /**
     * 检查该用户是否已入职门店的其他招聘岗位
     */
    @RequestMapping("/check/{id}")
    @RequiresPermissions("jobApplication:jobapplication:info")
    public R check(@PathVariable("id") Long id) {
        JobApplicationEntity jobApplication = jobApplicationService.queryObject(id);
        Boolean check = true;
        Map<String, Object> map = new HashMap<>();
        map.put("openid",jobApplication.getOpenid());
        map.put("stateFeedback",4); //已入职
        map.put("self",id);//排除本条报名的其他报名状态
        int result = jobApplicationService.queryTotal(map);
        if (result > 0) {
            check = false;
        }
        return R.ok().put("check", check);
    }


    /**
     *该条招聘下有入职员工 返费不可修改
     */
    @RequestMapping("/entry")
    @RequiresPermissions("jobApplication:jobapplication:info")
    public R entry(Long shopRecruitmentId) {
        Boolean entry = true;
        Map<String, Object> map = new HashMap<>();
        map.put("shopRecruitmentId",shopRecruitmentId);
        map.put("stateFeedback",4); //已入职
        int result = jobApplicationService.queryTotal(map);
        if (result > 0) {
            entry = false;
        }
        return R.ok().put("entry", entry);
    }






}
