
package com.wzlue.app.controller.jobApplication;

import cn.hutool.core.util.StrUtil;
import com.wzlue.AppApplication;
import com.wzlue.app.common.annotation.Login;
import com.wzlue.app.common.annotation.LoginUser;
import com.wzlue.app.controller.sys.AbstractController;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.common.exception.RRException;
import com.wzlue.jobApplication.entity.JobApplicationEntity;
import com.wzlue.jobApplication.service.JobApplicationService;
import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import com.wzlue.wechat.entity.WxUserEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户报名表（求职/应聘）
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 17:10:18
 */
@RestController
@RequestMapping("/app/jobapplication")
public class JobApplicationController extends AbstractController {
    @Autowired
    private JobApplicationService jobApplicationService;

    /**
     * 门店报名列表
     */
    @Login
    @GetMapping("/shopList")
    @ApiImplicitParams({
           /* @ApiImplicitParam(name = "openid", dataType = "string", value = "微信用户openid", paramType = "query"),
            @ApiImplicitParam(name = "appId", dataType = "string", value = "应用Id", paramType = "query"),*/
            @ApiImplicitParam(name = "belongTo", dataType = "int", value = "归属：1门店；2平台", paramType = "query"),
            @ApiImplicitParam(name = "type", dataType = "int", value = "报名类型：1主动报名（求职）；2招聘报名（应聘）", paramType = "query"),
            @ApiImplicitParam(name = "stateFeedback", dataType = "int", value = "1已报名；2已面试；3未录用；4已入职；5已离职；6其他", paramType = "query"),
            @ApiImplicitParam(name = "timeFrame", dataType = "int", value = "时间范围：1最近一周；2最近一个月；3最近三个月；4最近一年", paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "int", value = "页码", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", dataType = "int", value = "一页几条", paramType = "query", defaultValue = "10")
    })
    public R list(@RequestParam Map<String, Object> params, @LoginUser WxUserEntity wxUser) {
        //查询列表数据
        Query query = new Query(params);
//        query.put("belongTo", 1);
        if (null != wxUser) {
            query.put("openid", wxUser.getId());
            query.put("appId", wxUser.getAppId());
        }
        List<JobApplicationEntity> jobApplicationList = jobApplicationService.queryList(query);
        if (null != jobApplicationList && jobApplicationList.size() > 0) {
            for (JobApplicationEntity jobApplication : jobApplicationList) {
                if (jobApplication.getType() == 2) {
                    ShopRecruitmentEntity recruitment = jobApplication.getShopRecruitmentEntity();
                    if (null != recruitment) {
                        //月薪
                        if (StrUtil.isNotBlank(recruitment.getSalaryStr())) {
                            //例如：2000~4000元   去掉元
                            int index = recruitment.getSalaryStr().indexOf("元");
                            if (index != -1) {
                                String str = recruitment.getSalaryStr().substring(0, index) + recruitment.getSalaryStr().substring(index + 1);
                                recruitment.setSalary(str);
                            } else {
                                recruitment.setSalary(recruitment.getSalaryStr());
                            }
                        }
                    }
                }
            }
        }
        int total = jobApplicationService.queryTotal(query);

        return R.page(jobApplicationList, total);
    }


    /**
     * 报名详情
     */
    @GetMapping("/shopInfo/{id}")
//	@RequiresPermissions("jobApplication:jobapplication:info")
    public R info(@PathVariable("id") Long id) {
        JobApplicationEntity jobApplication = jobApplicationService.queryObject(id);

        return R.ok().put("jobApplication", jobApplication);
    }

    /**
     * 门店报名新增
     */
    @Login
    @PostMapping("/save")
//	@RequiresPermissions("jobApplication:jobapplication:save")
    public R save(@RequestBody JobApplicationEntity jobApplication, @LoginUser WxUserEntity wxUser) {
//        jobApplication.setBelongTo(1);

//        if (jobApplication.getAge() == null) {
//            throw new RRException("年龄不能为空");
//        }
        if (StrUtil.isNotBlank(jobApplication.getRecommenderOpenid()) && jobApplication.getRecommenderOpenid().equals(wxUser.getId())) {
            //自己推荐自己 没有奖励金
            jobApplication.setRecommenderOpenid(null);
        }

        jobApplication.setNickName(wxUser.getNickName());
        jobApplication.setOpenid(wxUser.getId());
        jobApplication.setAppId(wxUser.getAppId());
        jobApplicationService.save(jobApplication);

        return R.ok();
    }

    //用户当天同一个招聘是否已报名

    /**
     * 当日该条招聘报名个数
     *
     * @param token  openid shopRecruitmentId time
     * @param wxUser
     * @return @LoginUser WxUserEntity wxUser
     */
    @Login
    @GetMapping("/isRepeat")
    public R isRepeat(@RequestParam Long shopRecruitmentId, @LoginUser WxUserEntity wxUser) {
        //查询列表数据
        Map<String, Object> query = new HashMap<>();
        if (null != wxUser) {
            query.put("openid", wxUser.getId());
        }
        query.put("shopRecruitmentId", shopRecruitmentId);
        int num = jobApplicationService.isEnroll(query);//当日同一条招聘报名的数量
        return R.ok().put("num", num);//num大于零当天不可重复报名
    }

}


