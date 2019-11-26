
package com.wzlue.web.controller.fee;

import com.wzlue.common.base.R;
import java.util.List;
import java.util.Map;

import com.wzlue.jobApplication.entity.FeeReturnRecordEntity;
import com.wzlue.jobApplication.service.FeeReturnRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.common.base.Query;

/**
 * 返费记录表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-05 09:59:54
 */
@RestController
@RequestMapping("/fee/feeReturnRecord")
public class FeeReturnRecordController extends AbstractController{
	@Autowired
	private FeeReturnRecordService feeReturnRecordService;
	
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

		List<FeeReturnRecordEntity> feeReturnRecordList = feeReturnRecordService.queryList(query);
		int total = feeReturnRecordService.queryTotal(query);
		
		return R.page(feeReturnRecordList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("fee:feeReturnRecord:info")
	public R info(@PathVariable("id") Long id){
		FeeReturnRecordEntity feeReturnRecord = feeReturnRecordService.queryObject(id);
		
		return R.ok().put("feeReturnRecord", feeReturnRecord);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("fee:feeReturnRecord:save")
	public R save(@RequestBody FeeReturnRecordEntity feeReturnRecord){
		feeReturnRecord.setAppId(getUser().getAppId());
		feeReturnRecordService.save(feeReturnRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("fee:feeReturnRecord:update")
	public R update(@RequestBody FeeReturnRecordEntity feeReturnRecord){
		feeReturnRecordService.update(feeReturnRecord);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	//@RequiresPermissions("fee:feeReturnRecord:delete")
	public R delete(@RequestBody Long[] ids){
		feeReturnRecordService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
