
package com.wzlue.web.controller.store;

import com.wzlue.common.annotation.SysLog;
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

import com.wzlue.store.entity.TStoreRotarypicEntity;
import com.wzlue.store.service.TStoreRotarypicService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 门店轮播图
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-05 11:01:10
 */
@RestController
@RequestMapping("/store/tstorerotarypic")
public class TStoreRotarypicController extends AbstractController{
	@Autowired
	private TStoreRotarypicService tStoreRotarypicService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		if(!isStore(query)){
			return R.page(new Object[]{},0);
		}
		List<TStoreRotarypicEntity> tStoreRotarypicList = tStoreRotarypicService.queryListByParam(query);
		int total = tStoreRotarypicService.queryTotalByParam(query);

		
		return R.page(tStoreRotarypicList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("store:tstorerotarypic:info")
	public R info(@PathVariable("id") Long id){
		TStoreRotarypicEntity tStoreRotarypic = tStoreRotarypicService.queryObject(id);
		
		return R.ok().put("tStoreRotarypic", tStoreRotarypic);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存轮播图")
	@RequestMapping("/save")
	@RequiresPermissions("store:tstorerotarypic:save")
	public R save(@RequestBody TStoreRotarypicEntity tStoreRotarypic){
		tStoreRotarypic.setCreateId(getUserId().toString());
		tStoreRotarypicService.save(tStoreRotarypic);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改轮播图")
	@RequestMapping("/update")
	@RequiresPermissions("store:tstorerotarypic:update")
	public R update(@RequestBody TStoreRotarypicEntity tStoreRotarypic){
		tStoreRotarypic.setUpdateId(getUserId().toString());
		tStoreRotarypicService.update(tStoreRotarypic);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除轮播图")
	@RequestMapping("/delete")
	@RequiresPermissions("store:tstorerotarypic:delete")
	public R delete(@RequestBody Long[] ids){
		tStoreRotarypicService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
