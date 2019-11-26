package com.wzlue.web.controller.auditDistribution;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.common.utils.Constant;
import com.wzlue.jobApplication.entity.JobApplicationEntity;
import com.wzlue.jobApplication.service.JobApplicationService;
import com.wzlue.sys.entity.SysUserEntity;
import com.wzlue.web.controller.sys.AbstractController;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 门店审核，应聘（报名）分发门店
 */
@Slf4j
@RestController
@RequestMapping("/audit/auditDistribution")
public class AuditDistributionController extends AbstractController {

    @Autowired
    private JobApplicationService jobApplicationService;


    /**
     * 应聘列表
     */
    @GetMapping("/jobAppList")
    @RequiresPermissions("audit:auditDistribution:list")
    public R jobAppList(@RequestParam Map<String, Object> params) {

        //查询列表数据
        Query query = new Query(params);

        SysUserEntity user = getUser();

        // 不是超管
        if (user.getUserId() != Constant.SUPER_ADMIN && (StrUtil.isNotBlank(user.getAppId()) || StrUtil.equals(user.getStore(), "1"))) {

            return R.page(null, 0);
        }

        List<JobApplicationEntity> jobApplicationList = jobApplicationService.queryList(query);
        int total = jobApplicationService.queryTotal(query);

        return R.page(jobApplicationList, total);
    }
}
