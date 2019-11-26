
package com.wzlue.web.controller.store;

import com.wzlue.common.base.R;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.store.entity.TStoreSignInRecordEntity;
import com.wzlue.store.service.TStoreSignInRecordService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 门店签到记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
@RestController
@RequestMapping("/store/tstoresigninrecord")
public class TStoreSignInRecordController extends AbstractController{
	@Autowired
	private TStoreSignInRecordService tStoreSignInRecordService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("store:tstoresigninrecord:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		if(!isStore(query)){
			return R.page(new Object[]{},0);
		}
		List<TStoreSignInRecordEntity> tStoreSignInRecordList = tStoreSignInRecordService.queryList(query);
		int total = tStoreSignInRecordService.queryTotal(query);
		
		return R.page(tStoreSignInRecordList,total);
	}


	/**
	 * 签到
	 * @param params
	 * @return
	 */
	//@Login
	@RequestMapping("/signin")
	public R signIn(@RequestBody Map<String, Object> params){
		return tStoreSignInRecordService.signIn(params);
	}






	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("store:tstoresigninrecord:info")
	public R info(@PathVariable("id") Long id){
		TStoreSignInRecordEntity tStoreSignInRecord = tStoreSignInRecordService.queryObject(id);
		
		return R.ok().put("tStoreSignInRecord", tStoreSignInRecord);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("store:tstoresigninrecord:save")
	public R save(@RequestBody TStoreSignInRecordEntity tStoreSignInRecord){
		tStoreSignInRecordService.save(tStoreSignInRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("store:tstoresigninrecord:update")
	public R update(@RequestBody TStoreSignInRecordEntity tStoreSignInRecord){
		tStoreSignInRecordService.update(tStoreSignInRecord);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("store:tstoresigninrecord:delete")
	public R delete(@RequestBody Long[] ids){
		tStoreSignInRecordService.deleteBatch(ids);
		
		return R.ok();
	}





	
}
