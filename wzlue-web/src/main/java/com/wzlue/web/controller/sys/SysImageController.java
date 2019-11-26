
package com.wzlue.web.controller.sys;

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

import com.wzlue.sys.entity.SysImageEntity;
import com.wzlue.sys.service.SysImageService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 图片表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-09 16:53:50
 */
@RestController
@RequestMapping("/sys/sysimage")
public class SysImageController extends AbstractController{
	@Autowired
	private SysImageService sysImageService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysImageEntity> sysImageList = sysImageService.queryList(query);
		int total = sysImageService.queryTotal(query);
		
		return R.page(sysImageList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("sys:sysimage:info")
	public R info(@PathVariable("id") Long id){
		SysImageEntity sysImage = sysImageService.queryObject(id);
		
		return R.ok().put("sysImage", sysImage);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:sysimage:save")
	public R save(@RequestBody SysImageEntity sysImage){
		sysImageService.save(sysImage);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:sysimage:update")
	public R update(@RequestBody SysImageEntity sysImage){
		sysImageService.update(sysImage);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:sysimage:delete")
	public R delete(@RequestBody Long[] ids){
		sysImageService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
