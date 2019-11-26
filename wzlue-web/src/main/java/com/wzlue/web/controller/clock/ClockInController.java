
package com.wzlue.web.controller.clock;

import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzlue.jobApplication.entity.ClockInEntity;
import com.wzlue.jobApplication.entity.JobApplicationEntity;
import com.wzlue.jobApplication.service.ClockInService;

import com.wzlue.jobApplication.service.JobApplicationService;
import com.wzlue.recruitment.entity.FeeReturnEntity;
import com.wzlue.recruitment.service.FeeReturnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 打卡地址设置
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-29 18:44:41
 */
@RestController
@RequestMapping("/clock/clockIn")
public class ClockInController extends AbstractController{
	@Autowired
	private ClockInService clockInService;
	@Autowired
	private JobApplicationService jobApplicationService;
	@Autowired
	private FeeReturnService feeReturnService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

        //门店数据权限
		if(!isStore(query)){
			return R.page(new Object[]{},0);
		}
		//大于入职状态的打卡设置列表
		query.put("belongTo", 1);//归属门店
		//query.put("flag",1);
		List<JobApplicationEntity> jobApplicationEntities = jobApplicationService.queryEntryList(query);
		int total = jobApplicationService.queryEntryTotal(query);
		return R.page(jobApplicationEntities,total);
	}


	/**
	 * 入职打卡信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("clock:clockIn:info")
	public R info(@PathVariable("id") String id){
		ClockInEntity clockIn = clockInService.queryObject(id);

		return R.ok().put("clockIn", clockIn);
	}

	/**
	 * 招聘的打卡信息
	 */
	@RequestMapping("/infoClockIn/{recruitId}")
	@RequiresPermissions("clock:clockIn:info")
	public R infoClockIn(@PathVariable("recruitId") Long recruitId){
		ClockInEntity clockIn = clockInService.queryRecruitClock(recruitId);
		return R.ok().put("clockIn", clockIn);
	}

	/**
	 * 单个入职打卡设置
	 */
	@RequestMapping("/save")
	@RequiresPermissions("clock:clockIn:save")
	public R save(@RequestBody ClockInEntity clockIn){
		JobApplicationEntity jobApp = jobApplicationService.queryObject(clockIn.getMemberId());
		clockIn.setRecruitId(jobApp.getShopRecruitmentId());
		clockIn.setAppId(getUser().getAppId());//当前登录人appid
		clockIn.setCreateId(getUser().getUserId().toString());//登录人id
		clockIn.setAscription(1);//归属入职员工
		clockInService.save(clockIn);
		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("clock:clockIn:update")
	public R update(@RequestBody ClockInEntity clockIn){
		clockIn.setUpdateId(getUser().getUserId().toString());
		clockInService.update(clockIn);
		return R.ok();
	}

	/**
	 * 批量设置打卡--招聘
	 */
	@SysLog("clock/clockIn/saveList")
	@RequestMapping("/saveList")
	@RequiresPermissions("clock:clockIn:save")
	public R saveList(@RequestBody ClockInEntity clockIn){
		if (clockIn.getId()==null){//添加招聘打卡
			clockIn.setAppId(getUser().getAppId());//当前登录人appid
			clockIn.setCreateId(getUser().getUserId().toString());//登录人id
			clockIn.setAscription(2);//归属招聘
			clockInService.save(clockIn);
		} else {//修改招聘打卡
			clockIn.setUpdateId(getUser().getUserId().toString());
			clockInService.update(clockIn);
		}
		//查出该应聘id已入职的员工
		Map<String, Object> params = new HashMap<>();
		params.put("shopRecruitmentId",clockIn.getRecruitId());
		List<JobApplicationEntity> jobApplicationEntities = jobApplicationService.queryEntryList(params);
		for (JobApplicationEntity job:jobApplicationEntities) {
			ClockInEntity clockInEntity = job.getClockInEntity();
			//循环查看打卡信息，有则改无则加
			if (clockInEntity.getId()==null){
				//批量添加
				clockIn.setId(null);
				clockIn.setMemberId(job.getId());//给报名用户id赋值
				clockIn.setAppId(getUser().getAppId());//登录人appId
				clockIn.setCreateId(getUser().getUserId().toString());
				clockIn.setAscription(1);//归属入职员工
				clockInService.save(clockIn);
			} else {
				clockIn.setId(clockInEntity.getId());
				clockIn.setMemberId(job.getId());
				//批量修改
				clockInService.update(clockIn);
			}
		}
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("clock:clockIn:delete")
	public R delete(@RequestBody String[] ids){
		clockInService.deleteBatch(ids);

		return R.ok();
	}
	
}
