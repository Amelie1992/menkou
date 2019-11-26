
package com.wzlue.web.controller.store;

import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.common.utils.AjaxResult;
import com.wzlue.common.utils.ExcelUtil;
import com.wzlue.store.entity.TStoreGloryEntity;
import com.wzlue.store.service.TStoreGloryService;
import com.wzlue.web.controller.sys.AbstractController;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;




/**
 * 门店光荣榜表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 09:44:21
 */
@RestController
@RequestMapping("/store/tstoreglory")
public class TStoreGloryController extends AbstractController{
	@Autowired
	private TStoreGloryService tStoreGloryService;

	@Value("${fileupload.filepath}")
	String filePath;

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

		List<TStoreGloryEntity> tStoreGloryList = tStoreGloryService.queryListByParam(query);
		int total = tStoreGloryService.queryTotalByParam(query);
		
		return R.page(tStoreGloryList,total);
	}


	@RequiresPermissions("store:tstoreglory:export")
	@PostMapping("/export/{type}")
	@ResponseBody
	public AjaxResult export(@PathVariable("type") String type,@RequestBody Map<String, Object> params)
	{

		List<TStoreGloryEntity> list = null;
		if("0".equals(type)) {
			//查询列表数据
			Query query = new Query(params);
			if(isStore(query)){
				list = tStoreGloryService.queryListAll(query);
			}

		} else {
			List ids = (ArrayList) params.get("ids");
			Object[] idObjs = ids.toArray() ;
			list = tStoreGloryService.queryListByIds(idObjs);
		}
		ExcelUtil<TStoreGloryEntity> util = new ExcelUtil<TStoreGloryEntity>(TStoreGloryEntity.class);
		return util.exportExcel(list, "光荣榜数据",filePath);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{gloryId}")
	@RequiresPermissions("store:tstoreglory:info")
	public R info(@PathVariable("gloryId") Long gloryId){
		TStoreGloryEntity tStoreGlory = tStoreGloryService.queryObject(gloryId);
		
		return R.ok().put("tStoreGlory", tStoreGlory);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存光荣榜")
	@RequestMapping("/save")
	@RequiresPermissions("store:tstoreglory:save")
	public R save(@RequestBody TStoreGloryEntity tStoreGlory){
		tStoreGlory.setCreateId(getUserId().toString());
		tStoreGloryService.save(tStoreGlory);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改光荣榜")
	@RequestMapping("/update")
	@RequiresPermissions("store:tstoreglory:update")
	public R update(@RequestBody TStoreGloryEntity tStoreGlory){
		tStoreGlory.setUpdateId(getUserId().toString());
		tStoreGloryService.update(tStoreGlory);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除光荣榜")
	@RequestMapping("/delete")
	@RequiresPermissions("store:tstoreglory:delete")
	public R delete(@RequestBody Long[] gloryIds){
		tStoreGloryService.deleteBatch(gloryIds);
		
		return R.ok();
	}




	
}
