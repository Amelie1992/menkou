
package com.wzlue.app.controller.clock;

import com.wzlue.app.common.annotation.Login;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.LogSource;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.jobApplication.entity.CMemberClockEntity;
import com.wzlue.jobApplication.entity.ClockInEntity;
import com.wzlue.jobApplication.entity.JobApplicationEntity;
import com.wzlue.jobApplication.service.CMemberClockService;
import com.wzlue.jobApplication.service.ClockInService;
import com.wzlue.jobApplication.service.JobApplicationService;
import com.wzlue.recruitment.entity.FeeReturnEntity;
import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import com.wzlue.recruitment.service.FeeReturnService;
import com.wzlue.recruitment.service.ShopRecruitmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 入职用户打卡api
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 09:33:48
 */
@RestController
@RequestMapping("/app/cMemberClock")
public class ApiCMemberClockController{
	@Autowired
	private CMemberClockService cMemberClockService;
	@Autowired
	private ClockInService clockInService;
	@Autowired
	private JobApplicationService jobApplicationService;
	@Autowired
	private ShopRecruitmentService shopRecruitmentService;
	@Autowired
	private FeeReturnService feeReturnService;
	
	/**
	 * 个人打卡记录列表
	 * time 年月（如：201908）
	 * userNo 员工入职id
	 */
	@Login
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){

		//查询列表数据
		List<CMemberClockEntity> cMemberClockList = cMemberClockService.queryMemberClock(params);
		int total = cMemberClockService.queryMemberClockTotal(params);
		
		return R.page(cMemberClockList,total);
	}

	/**
	 * 单日打卡信息
	 * 表名
	 * 日期
	 * userNo
	 */
	@Login
	@RequestMapping("/info")
	public R info(@RequestParam Map<String, Object> params) {
		CMemberClockEntity cMemberClock = cMemberClockService.apiDayClock(params);
		return R.ok().put("cMemberClock", cMemberClock);
	}

	/**
	 * 判断用户是否在职
	 * @RequestAttribute("userId")
	 * @return
	 */
	@Login
	@RequestMapping("/isEntry")
	public R isEntry(@RequestAttribute("userId")String userId) {
		R r = new R();
		//所有返费余额的和
		BigDecimal rewardAll = jobApplicationService.rewardAll(userId);
		//查看是否在职
		//是否存在返费
		Boolean feeIsExist = false;
		Map<String, Object> params = new HashMap<>();
		params.put("openid",userId);//存微信用户表ID
		params.put("stateFeedback",4);
		List<JobApplicationEntity> jobApplicationEntities = jobApplicationService.queryList(params);//在职的入职信息
		String title = "";//入职标题
		ClockInEntity jobClock = new ClockInEntity();
		if (jobApplicationEntities.size()>0){
			for (JobApplicationEntity job:jobApplicationEntities) {
				//查看招聘信息
				if (job.getShopRecruitmentId()!=null){
					ShopRecruitmentEntity shopRecruitmentEntity = shopRecruitmentService.queryObject(job.getShopRecruitmentId());
					title = shopRecruitmentEntity.getTitle();
					List<FeeReturnEntity> feeReturnEntities = feeReturnService.queryByRecruitmentId(shopRecruitmentEntity.getId());
					if (feeReturnEntities.size()>0){
						feeIsExist = true;
					}
				}
				//查看打卡信息
				jobClock = clockInService.queryJobClock(job.getId());
			}
		}
		r.put("jobApplicationEntities",jobApplicationEntities);//在职信息
		r.put("jobClock",jobClock);//打卡位置信息
		r.put("feeIsExist",feeIsExist);//返费信息
		r.put("title",title);//入职标题
		r.put("rewardAll",rewardAll);//返费的和
		return R.ok().put("rows",r);
	}

	/**
	 * 入职用户当天是否打卡
	 * @param userNo 入职用户id
	 * @return
	 */
	@SysLog(value = "isClock",source = LogSource.APP)
	@Login
	@RequestMapping("/isClock")
	public R isClock(Long userNo){
		if (userNo==null){
			return R.error("异常,无入职用户！");
		} else {
			//服务器当前日期
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);//获取年份
			int month = cal.get(Calendar.MONTH)+1;//获取当月月份+1
			cal.set(Calendar.MONTH, month - 1);
			cal.set(Calendar.DATE, 1);
			cal.roll(Calendar.DATE, -1);
			String monthStr=String.valueOf(month);
			if(monthStr.length()<=1){
				monthStr= new String("0")+monthStr;
			}
			//当前年月表名
			String time =  year + monthStr;//eg:201908

			CMemberClockEntity cMemberClockEntity = cMemberClockService.apiDetail(time,userNo,dateFormat.format(date));
			int type = cMemberClockEntity.getClockType();//当天打卡状态

			//入职id查打卡设置信息
			ClockInEntity jobUserClock = clockInService.queryJobClock(userNo);
			return R.ok().put("type",type).put("jobUserClock",jobUserClock);
		}
	}

	/**
	 * 打卡
	 * 打卡入职员工的id user_no
	 */
	@SysLog(value = "打卡",source = LogSource.APP)
	@Login
	@PostMapping("/save")
	public R save(@RequestParam("userNo") Long userNo,@RequestParam("clockAddress") String clockAddress){
		//查看当天打卡情况
		//服务器当前日期
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//获取年份
		int month = cal.get(Calendar.MONTH)+1;//获取当月月份+1
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, 1);
		cal.roll(Calendar.DATE, -1);
		String monthStr=String.valueOf(month);
		if(monthStr.length()<=1){
			monthStr= new String("0")+monthStr;
		}
		//当前年月表名
		String time =  year + monthStr;//eg:201908

		CMemberClockEntity cMemberClockEntity = cMemberClockService.apiDetail(time,userNo,dateFormat.format(date));
		if (cMemberClockEntity.getClockType()==2){//未打卡
			cMemberClockEntity.setTableName(time);
			cMemberClockEntity.setClockAddress(clockAddress);
			cMemberClockService.updateApi(cMemberClockEntity);
			return R.ok();
		} else {
			return R.error("当天已打过卡！");
		}

	}


}
