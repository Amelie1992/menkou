
package com.wzlue.app.controller.store;

import cn.hutool.core.util.StrUtil;
import com.wzlue.app.controller.sys.AbstractController;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreGloryEntity;
import com.wzlue.store.service.TStoreGloryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/app/tstoreglory")
public class TStoreGloryController extends AbstractController{
	@Autowired
	private TStoreGloryService tStoreGloryService;


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
		List<TStoreGloryEntity> tStoreGloryList = tStoreGloryService.queryListByParam(query);
		int total = tStoreGloryService.queryTotalByParam(query);
		
		return R.page(tStoreGloryList,total);
	}


	/**
	 * 信息
	 */
	@RequestMapping("/info/{gloryId}")
	public R info(@PathVariable("gloryId") Long gloryId){
		TStoreGloryEntity tStoreGlory = tStoreGloryService.queryObject(gloryId);

		return R.ok().put("tStoreGlory", tStoreGlory);
	}
	
	

	
}
