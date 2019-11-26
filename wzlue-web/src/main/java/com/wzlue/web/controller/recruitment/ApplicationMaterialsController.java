
package com.wzlue.web.controller.recruitment;

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

import com.wzlue.recruitment.entity.ApplicationMaterialsEntity;
import com.wzlue.recruitment.service.ApplicationMaterialsService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 申请材料（上平台招聘的申请材料）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-09 16:51:59
 */
@RestController
@RequestMapping("/recruitment/applicationmaterials")
public class ApplicationMaterialsController extends AbstractController{
	@Autowired
	private ApplicationMaterialsService applicationMaterialsService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ApplicationMaterialsEntity> applicationMaterialsList = applicationMaterialsService.queryList(query);
		int total = applicationMaterialsService.queryTotal(query);
		
		return R.page(applicationMaterialsList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recruitment:applicationmaterials:info")
	public R info(@PathVariable("id") Long id){
		ApplicationMaterialsEntity applicationMaterials = applicationMaterialsService.queryObject(id);
		
		return R.ok().put("applicationMaterials", applicationMaterials);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recruitment:applicationmaterials:save")
	public R save(@RequestBody ApplicationMaterialsEntity applicationMaterials){
		applicationMaterialsService.save(applicationMaterials);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recruitment:applicationmaterials:update")
	public R update(@RequestBody ApplicationMaterialsEntity applicationMaterials){
		applicationMaterialsService.update(applicationMaterials);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recruitment:applicationmaterials:delete")
	public R delete(@RequestBody Long[] ids){
		applicationMaterialsService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
