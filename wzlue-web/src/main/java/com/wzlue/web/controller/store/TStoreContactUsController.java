
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

import com.wzlue.store.entity.TStoreContactUsEntity;
import com.wzlue.store.service.TStoreContactUsService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 门店联系我们
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-09-28 17:14:06
 */
@RestController
@RequestMapping("/store/tstorecontactus")
public class TStoreContactUsController extends AbstractController{
	@Autowired
	private TStoreContactUsService tStoreContactUsService;
	
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

		List<TStoreContactUsEntity> tStoreContactUsList = tStoreContactUsService.queryListByParam(query);
		int total = tStoreContactUsService.queryTotalByParam(query);
		
		return R.page(tStoreContactUsList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("store:tstorecontactus:info")
	public R info(@PathVariable("id") Long id){
		TStoreContactUsEntity tStoreContactUs = tStoreContactUsService.queryObject(id);
		
		return R.ok().put("tStoreContactUs", tStoreContactUs);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存门店联系我们")
	@RequestMapping("/save")
	@RequiresPermissions("store:tstorecontactus:save")
	public R save(@RequestBody TStoreContactUsEntity tStoreContactUs){
        String appId = tStoreContactUs.getAppId();
        if (StrUtil.isBlank(appId)) {
            return R.error("appId不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("appId",appId);
        int i = tStoreContactUsService.queryTotalByParam(map);
        if (i > 0) {
            return R.error("门店只能保存一条联系我们");
        }
        tStoreContactUs.setCreateId(getUserId().toString());
        tStoreContactUsService.save(tStoreContactUs);
        return R.ok();

	}
	
	/**
	 * 修改
	 */
	@SysLog("修改门店联系我们")
	@RequestMapping("/update")
	@RequiresPermissions("store:tstorecontactus:update")
	public R update(@RequestBody TStoreContactUsEntity tStoreContactUs){
        tStoreContactUs.setUpdateId(getUserId().toString());
        tStoreContactUsService.update(tStoreContactUs);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("store:tstorecontactus:delete")
	public R delete(@RequestBody Long[] ids){
		tStoreContactUsService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
