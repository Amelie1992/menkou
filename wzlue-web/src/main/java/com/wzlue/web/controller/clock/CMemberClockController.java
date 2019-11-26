
package com.wzlue.web.controller.clock;

import com.wzlue.common.base.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wzlue.jobApplication.entity.CMemberClockEntity;
import com.wzlue.jobApplication.service.CMemberClockService;
import com.wzlue.recruitment.entity.FeeReturnEntity;
import com.wzlue.recruitment.service.FeeReturnService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.common.base.Query;


/**
 * 入职用户打卡表
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 09:33:48
 */
@RestController
@RequestMapping("/clock/cMemberClock")
public class CMemberClockController extends AbstractController {
	@Autowired
	private CMemberClockService cMemberClockService;

	@Autowired
	private FeeReturnService feeReturnService;

	@Value("${table_schema}")
	private String tableSchema;//数据库名

	/**
	 * 列表
	 * 参数：年月--表名+其他
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		//查询列表数据
		Query query = new Query(params);

		//门店数据权限
		if(!isStore(query)){
			return R.page(new Object[]{},0);
		}

		//当月表是否存在
		int count = cMemberClockService.isExistTable(tableSchema,"c_member_clock_"+params.get("time"));
		if (count>0){
			List<CMemberClockEntity> cMemberClockList = cMemberClockService.queryMemberClock(query);
			for (CMemberClockEntity cmem:cMemberClockList) {
				//toMend
				Date today = cmem.getCreateDate();//应打卡时间
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				//系统明天
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, 1);//-1.昨天时间 0.当前时间 1.明天时间 *以此类推
				String nextDay = formatter.format(c.getTime());
				Date nextDate = null;
				try {
					nextDate = formatter.parse(nextDay);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				boolean before = today.before(nextDate);
				if (before==true){//小于明天的可以补卡
					cmem.setToMend(1);//是否可以补卡(1是2否)
				} else {
					cmem.setToMend(2);
				}

				//未设置打卡信息的 不可以补卡

			}
			int total = cMemberClockService.queryMemberClockTotal(query);
			return R.page(cMemberClockList, total);
		} else {
			return R.page(null, 0);
		}
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info")
	//@RequiresPermissions("clock:cMemberClock:info")
	public R info(String time, Long id) {
		CMemberClockEntity cMemberClock = cMemberClockService.detail(time, id);
		//返费规则
		List<FeeReturnEntity> feeReturnEntities = feeReturnService.queryByRecruitmentId(cMemberClock.getShopRecruitmentId());
		if (feeReturnEntities.size()<=0){
			return R.error("不存在返费，不给补卡");//不存在返费，不给补卡
		}
		return R.ok().put("cMemberClock", cMemberClock);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("clock:cMemberClock:save")
	public R save(@RequestBody CMemberClockEntity cMemberClock) {
		cMemberClockService.save(cMemberClock);

		return R.ok();
	}

	/**
	 * 修改(补卡)
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("clock:cMemberClock:update")
	public R update(@RequestBody CMemberClockEntity cMemberClock) throws ParseException {
		cMemberClockService.update(cMemberClock);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions("clock:cMemberClock:delete")
	public R delete(@RequestBody Long[] ids,@RequestParam String tableName) {
		cMemberClockService.deleteBatch(ids,tableName);
		return R.ok();
	}

}
