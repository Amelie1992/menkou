
package com.wzlue.app.controller.store;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreNoticeEntity;
import com.wzlue.store.service.TStoreNoticeService;
import com.wzlue.app.controller.sys.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 门店公告表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 16:44:30
 */
@RestController
@RequestMapping("/app/tstorenotice")
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

		String appId = (String)query.get("appId");
		if(StrUtil.isBlank(appId)){
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
	public R info(@PathVariable("noticeId") Long noticeId){
		TStoreNoticeEntity tStoreNotice = tStoreNoticeService.queryObject(noticeId);

		return R.ok().put("tStoreNotice", tStoreNotice);
	}
	

	
}
