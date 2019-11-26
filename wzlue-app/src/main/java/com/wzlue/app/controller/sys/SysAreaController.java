
package com.wzlue.app.controller.sys;

import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.common.exception.RRException;
import com.wzlue.sys.entity.SysAreaEntity;
import com.wzlue.sys.service.SysAreaService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author YJ
 * @email wzlue.com
 * @date 2018-06-11 14:13:34
 */
@RestController
@RequestMapping("/app/sysarea")
public class SysAreaController extends AbstractController{
	@Autowired
	private SysAreaService sysAreaService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysAreaEntity> sysAreaList = sysAreaService.queryList(query);
		int total = sysAreaService.queryTotal(query);
		
		return R.page(sysAreaList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:sysarea:info")
	public R info(@PathVariable("id") Integer id){
		SysAreaEntity sysArea = sysAreaService.queryObject(id);
		
		return R.ok().put("sysArea", sysArea);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:sysarea:save")
	public R save(@RequestBody SysAreaEntity sysArea){
		sysAreaService.save(sysArea);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:sysarea:update")
	public R update(@RequestBody SysAreaEntity sysArea){
		sysAreaService.update(sysArea);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:sysarea:delete")
	public R delete(@RequestBody Integer[] ids){
		sysAreaService.deleteBatch(ids);
		
		return R.ok();
	}


	/**
	 * 列表
	 */
	/*@RequestMapping("/queryProvincesList")
	public R queryProvincesList(@RequestParam Map<String, Object> params){


		String parentid = (String) params.get("parentid");
		String level = (String) params.get("level");

		if (StringUtils.isBlank(parentid) || StringUtils.isBlank(level)) {
			throw new RRException("父级id和区域等级均不能为空！");
		}
		//查询列表数据
		Query query = new Query(params);
		List<SysAreaEntity> sysAreaList = sysAreaService.queryProvincesList(query);
		int total = sysAreaService.queryProvincesListTotal(query);
		return R.page(sysAreaList,total);
	}*/




	/**
	 * 根据parentid查询
	 */
	@RequestMapping("/areaList")
	public R areaList(@RequestParam Map<String, Object> params){
		String parentid = (String) params.get("parentid");
		if (StringUtils.isBlank(parentid) ) {
			throw new RRException("父级id不能为空！");
		}
		//查询列表数据
		Query query = new Query(params);

		List<SysAreaEntity> sysAreaList = sysAreaService.areaList(query);

		return R.ok().put("areaList",sysAreaList);
	}
}
