
package com.wzlue.web.controller.sys;

import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.sys.entity.SysLogisticsEntity;
import com.wzlue.sys.service.SysLogisticsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * 物流表
 * 
 * @author YJ
 * @email wzlue.com
 * @date 2018-06-29 10:01:23
 */
@RestController
@RequestMapping("/sys/logistics")
public class SysLogisticsController extends AbstractController{
	@Autowired
	private SysLogisticsService sysLogisticsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysLogisticsEntity> sysLogisticsList = sysLogisticsService.queryList(query);
		int total = sysLogisticsService.queryTotal(query);
		
		return R.page(sysLogisticsList,total);
	}


	/**
	 * 全部信息
	 */
	@RequestMapping("/all")
	public R all(){
		List<SysLogisticsEntity> orderLogisticsList = sysLogisticsService.queryList(new HashMap<>());

		return R.ok().put("logistics",orderLogisticsList);
	}

	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("activity:syslogistics:info")
	public R info(@PathVariable("id") Long id){
		SysLogisticsEntity sysLogistics = sysLogisticsService.queryObject(id);
		
		return R.ok().put("sysLogistics", sysLogistics);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("activity:syslogistics:save")
	public R save(@RequestBody SysLogisticsEntity sysLogistics){
		sysLogisticsService.save(sysLogistics);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("activity:syslogistics:update")
	public R update(@RequestBody SysLogisticsEntity sysLogistics){
		sysLogisticsService.update(sysLogistics);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("activity:syslogistics:delete")
	public R delete(@RequestBody Long[] ids){
		sysLogisticsService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
