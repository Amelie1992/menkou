
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

import com.wzlue.store.entity.TStoreNoticeEntity;
import com.wzlue.store.service.TStoreNoticeService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 门店公告表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 16:44:30
 */
@RestController
@RequestMapping("/store/tstorenotice")
public class TStoreNoticeController extends AbstractController{
	@Autowired
	private TStoreNoticeService tStoreNoticeService;
	
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
		List<TStoreNoticeEntity> tStoreNoticeList = tStoreNoticeService.queryListByParam(query);
		int total = tStoreNoticeService.queryTotalByParam(query);
		
		return R.page(tStoreNoticeList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{noticeId}")
	@RequiresPermissions("store:tstorenotice:info")
	public R info(@PathVariable("noticeId") Long noticeId){
		TStoreNoticeEntity tStoreNotice = tStoreNoticeService.queryObject(noticeId);
		
		return R.ok().put("tStoreNotice", tStoreNotice);
	}
	
	/**
	 * 保存
	 */
	@SysLog("保存公告")
	@RequestMapping("/save")
	@RequiresPermissions("store:tstorenotice:save")
	public R save(@RequestBody TStoreNoticeEntity tStoreNotice){
		tStoreNotice.setCreateId(getUserId().toString());
		tStoreNoticeService.save(tStoreNotice);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@SysLog("修改公告")
	@RequestMapping("/update")
	@RequiresPermissions("store:tstorenotice:update")
	public R update(@RequestBody TStoreNoticeEntity tStoreNotice){
		tStoreNotice.setUpdateId(getUserId().toString());
		tStoreNoticeService.update(tStoreNotice);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@SysLog("删除公告")
	@RequestMapping("/delete")
	@RequiresPermissions("store:tstorenotice:delete")
	public R delete(@RequestBody Long[] noticeIds){
		tStoreNoticeService.deleteBatch(noticeIds);
		
		return R.ok();
	}
	
}
