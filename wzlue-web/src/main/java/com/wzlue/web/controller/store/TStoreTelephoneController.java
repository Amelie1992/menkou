
package com.wzlue.web.controller.store;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreTelephoneEntity;
import com.wzlue.store.service.TStoreTelephoneService;
import com.wzlue.web.controller.sys.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * 门店电话表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 16:10:28
 */
@RestController
@RequestMapping("/store/tstoretelephone")
public class TStoreTelephoneController extends AbstractController{
	@Autowired
	private TStoreTelephoneService tStoreTelephoneService;
	
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
		List<TStoreTelephoneEntity> tStoreTelephoneList = tStoreTelephoneService.queryListByParam(query);
		int total = tStoreTelephoneService.queryTotalByParam(query);

		return R.page(tStoreTelephoneList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("store:tstoretelephone:info")
	public R info(@PathVariable("id") Long id){
		TStoreTelephoneEntity tStoreTelephone = tStoreTelephoneService.queryObject(id);
		
		return R.ok().put("tStoreTelephone", tStoreTelephone);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存电话")
	@RequestMapping("/save")
	@RequiresPermissions("store:tstoretelephone:save")
	public R save(@RequestBody TStoreTelephoneEntity tStoreTelephone){
		String appId = tStoreTelephone.getAppId();
		if (StrUtil.isBlank(appId)) {
			return R.error("appId不能为空");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("appId",appId);
//		int i = tStoreTelephoneService.queryTotalByParam(map);
//		if (i > 0) {
//			return R.error("门店只能保存一条电话信息");
//		}
		tStoreTelephone.setCreateId(getUserId().toString());
		tStoreTelephoneService.save(tStoreTelephone);
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改电话")
	@RequestMapping("/update")
	@RequiresPermissions("store:tstoretelephone:update")
	public R update(@RequestBody TStoreTelephoneEntity tStoreTelephone){
		tStoreTelephone.setUpdateId(getUserId().toString());
		tStoreTelephoneService.update(tStoreTelephone);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除标语")
	@RequestMapping("/delete")
	@RequiresPermissions("store:tstoretelephone:delete")
	public R delete(@RequestBody Long[] ids){
		tStoreTelephoneService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
