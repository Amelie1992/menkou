
package com.wzlue.app.controller.store;

import com.wzlue.app.controller.sys.AbstractController;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreConfigEntity;
import com.wzlue.store.service.TStoreConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 门店配置表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 09:44:21
 */
@RestController
@RequestMapping("/app/tstoreconfig")
public class TStoreConfigController extends AbstractController {
	@Autowired
	private TStoreConfigService tStoreConfigService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		String configType = org.apache.commons.lang3.StringUtils.EMPTY;
		//查询列表数据
        Query query = new Query(params);


		List<TStoreConfigEntity> tStoreConfigList = tStoreConfigService.queryListByParam(query);
		int total = tStoreConfigService.queryTotalByParam(query);
		return R.page(tStoreConfigList,total);
	}
	
	

	
}
