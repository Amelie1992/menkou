
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

import com.wzlue.store.entity.TStoreIntegralRecordEntity;
import com.wzlue.store.service.TStoreIntegralRecordService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 门店积分记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
@RestController
@RequestMapping("/store/tstoreintegralrecord")
public class TStoreIntegralRecordController extends AbstractController{
	@Autowired
	private TStoreIntegralRecordService tStoreIntegralRecordService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("store:tstoreintegralrecord:list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		if(!isStore(query)){
			return R.page(new Object[]{},0);
		}
		List<TStoreIntegralRecordEntity> tStoreIntegralRecordList = tStoreIntegralRecordService.queryList(query);
		int total = tStoreIntegralRecordService.queryTotal(query);
		
		return R.page(tStoreIntegralRecordList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("store:tstoreintegralrecord:info")
	public R info(@PathVariable("id") Long id){
		TStoreIntegralRecordEntity tStoreIntegralRecord = tStoreIntegralRecordService.queryObject(id);
		
		return R.ok().put("tStoreIntegralRecord", tStoreIntegralRecord);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("store:tstoreintegralrecord:save")
	public R save(@RequestBody TStoreIntegralRecordEntity tStoreIntegralRecord){
		tStoreIntegralRecordService.save(tStoreIntegralRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("store:tstoreintegralrecord:update")
	public R update(@RequestBody TStoreIntegralRecordEntity tStoreIntegralRecord){
		tStoreIntegralRecordService.update(tStoreIntegralRecord);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("store:tstoreintegralrecord:delete")
	public R delete(@RequestBody Long[] ids){
		tStoreIntegralRecordService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
