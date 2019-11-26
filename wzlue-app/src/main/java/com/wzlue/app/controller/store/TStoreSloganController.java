
package com.wzlue.app.controller.store;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreSloganEntity;
import com.wzlue.store.service.TStoreSloganService;
import com.wzlue.app.controller.sys.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 门店标语表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 16:44:29
 */
@RestController
@RequestMapping("/app/tstoreslogan")
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
		String appId = (String)query.get("appId");
		if(StrUtil.isBlank(appId)){
			return R.page(new Object[]{},0);
		}
		List<TStoreSloganEntity> tStoreSloganList = tStoreSloganService.queryListByParam(query);
		int total = tStoreSloganService.queryTotalByParam(query);
		
		return R.page(tStoreSloganList,total);
	}
	
	

	
}
