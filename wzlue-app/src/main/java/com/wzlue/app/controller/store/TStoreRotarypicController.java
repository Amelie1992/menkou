
package com.wzlue.app.controller.store;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreRotarypicEntity;
import com.wzlue.store.service.TStoreRotarypicService;
import com.wzlue.app.controller.sys.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 门店轮播图
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-05 11:01:10
 */
@RestController
@RequestMapping("/app/tstorerotarypic")
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

		String appId = (String)query.get("appId");
		if(StrUtil.isBlank(appId)){
			return R.page(new Object[]{},0);
		}
		List<TStoreRotarypicEntity> tStoreRotarypicList = tStoreRotarypicService.queryListByParam(query);
		int total = tStoreRotarypicService.queryTotalByParam(query);

		
		return R.page(tStoreRotarypicList,total);
	}

	
}
