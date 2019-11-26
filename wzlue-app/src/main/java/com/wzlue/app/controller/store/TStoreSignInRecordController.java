
package com.wzlue.app.controller.store;

import com.wzlue.app.common.annotation.Login;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.LogSource;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreSignInRecordEntity;
import com.wzlue.store.service.TStoreSignInRecordService;
import com.wzlue.app.controller.sys.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 门店签到记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
@RestController
@RequestMapping("/app/tstoresigninrecord")
public class TStoreSignInRecordController extends AbstractController{
	@Autowired
	private TStoreSignInRecordService tStoreSignInRecordService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);


		List<TStoreSignInRecordEntity> tStoreSignInRecordList = tStoreSignInRecordService.queryList(query);
		int total = tStoreSignInRecordService.queryTotal(query);
		
		return R.page(tStoreSignInRecordList,total);
	}


	/**
     * 签到
	 * @param params
     * @return
     */
	@SysLog(value = "签到",source = LogSource.APP)
	@Login
	@RequestMapping("/signIn")
	public R signIn(@RequestParam Map<String, Object> params,@RequestAttribute("userId") String userId){
		params.put("userId",userId);
		return tStoreSignInRecordService.signIn(params);
	}



	/**
	 * 检查是否签到
	 * @param params
	 * @return
	 */
	@SysLog(value = "检查是否签到",source = LogSource.APP)
	@Login
	@RequestMapping("/checkSignIn")
	public R checkSignIn(@RequestParam Map<String, Object> params,@RequestAttribute("userId") String userId){
		params.put("userId",userId);
		return tStoreSignInRecordService.checkSignIn(params);
	}


	/**
	 * 获取年列表
	 * @return
	 */
	@GetMapping("/getYear")
	public R getYear() {
		int start = 2018;
		int year = LocalDate.now().getYear();
		List<Integer> list = Stream.iterate(start, item -> item + 1).limit(year - start + 1).collect(Collectors.toList());
		return R.ok().put("year", list);
	}












	
}
