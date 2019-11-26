
package com.wzlue.web.controller.store;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;

import java.util.HashMap;
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

import com.wzlue.store.entity.TStoreSloganEntity;
import com.wzlue.store.service.TStoreSloganService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 门店标语表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 16:44:29
 */
@RestController
@RequestMapping("/store/tstoreslogan")
public class TStoreSloganController extends AbstractController{
	@Autowired
	private TStoreSloganService tStoreSloganService;
	
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
		List<TStoreSloganEntity> tStoreSloganList = tStoreSloganService.queryListByParam(query);
		int total = tStoreSloganService.queryTotalByParam(query);
		
		return R.page(tStoreSloganList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("store:tstoreslogan:info")
	public R info(@PathVariable("id") Long id){
		TStoreSloganEntity tStoreSlogan = tStoreSloganService.queryObject(id);
		
		return R.ok().put("tStoreSlogan", tStoreSlogan);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存标语")
	@RequestMapping("/save")
	@RequiresPermissions("store:tstoreslogan:save")
	public R save(@RequestBody TStoreSloganEntity tStoreSlogan){
		String appId = tStoreSlogan.getAppId();
		if (StrUtil.isBlank(appId)) {
			return R.error("appId不能为空");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("appId",appId);
		int i = tStoreSloganService.queryTotalByParam(map);
		if (i > 0) {
			return R.error("门店只能保存一条标语信息");
		}


		tStoreSlogan.setCreateId(getUserId().toString());
		tStoreSloganService.save(tStoreSlogan);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改标语")
	@RequestMapping("/update")
	@RequiresPermissions("store:tstoreslogan:update")
	public R update(@RequestBody TStoreSloganEntity tStoreSlogan){
		tStoreSlogan.setUpdateId(getUserId().toString());
		tStoreSloganService.update(tStoreSlogan);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除标语")
	@RequestMapping("/delete")
	@RequiresPermissions("store:tstoreslogan:delete")
	public R delete(@RequestBody Long[] ids){
		tStoreSloganService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
