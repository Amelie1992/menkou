
package com.wzlue.app.controller.store;

import com.wzlue.app.common.annotation.Login;
import com.wzlue.app.controller.sys.AbstractController;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.LogSource;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreIntegralRecordEntity;
import com.wzlue.store.service.TStoreIntegralRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 门店积分记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
@RestController
@RequestMapping("/app/tstoreintegralrecord")
public class TStoreIntegralRecordController extends AbstractController {
	@Autowired
	private TStoreIntegralRecordService tStoreIntegralRecordService;
	
	/**
	 * 列表
	 */
	@Login
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params,@RequestAttribute("userId") String userId){
		params.put("userId",userId);
		//查询列表数据
//        Query query = new Query(params);//不分页

		List<TStoreIntegralRecordEntity> tStoreIntegralRecordList = tStoreIntegralRecordService.queryListByDate(params);
		int total = tStoreIntegralRecordService.queryTotalByDate(params);
		
		return R.page(tStoreIntegralRecordList,total);
	}




	/**
	 * 完成新手任务得积分
	 * @param userId
	 * @return
	 */
	@SysLog(value = "完成新手任务得积分",source = LogSource.APP)
	@Login
	@RequestMapping("/doNewbieTask")
	public R doNewbieTask(@RequestAttribute("userId") String userId){
		//验证新手任务得积分前提条件
		tStoreIntegralRecordService.validateOfdoNewbieTask(userId);
		return tStoreIntegralRecordService.doNewbieTask(userId);
	}



	/**
	 * 取消新手任务
	 * @param
	 * @return
	 */
	@SysLog(value = "取消新手任务",source = LogSource.APP)
	@Login
	@RequestMapping("/cancelNewbieTask")
	public R cancelNewbieTask(@RequestParam("cancelFlag") String cancelFlag,@RequestAttribute("userId") String userId){
		return tStoreIntegralRecordService.cancelNewbieTask(userId,cancelFlag);
	}

	/**
	 * 检查该用户是否完成新手任务
	 * @param
	 * @return
	 */
	@SysLog(value = "检查该用户是否完成新手任务",source = LogSource.APP)
	@Login
	@RequestMapping("/checkNewbieTask")
	public R checkNewbieTask(@RequestAttribute("userId") String userId){

		return tStoreIntegralRecordService.checkNewbieTask(userId);
	}
	

	
}
