
package com.wzlue.app.controller.store;

import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.store.service.TStoreWxUserService;
import com.wzlue.app.controller.sys.AbstractController;
import com.wzlue.wechat.entity.WxUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 门店用户信息
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
@RestController
@RequestMapping("/app/tstorewxuser")
public class TStoreWxUserController extends AbstractController{
	@Autowired
	private TStoreWxUserService tStoreWxUserService;

	/**
	 * 分页查询
	 *
	 * @param params tagidList 标签
	 * @return
	 */
	@GetMapping("/list")
	//@RequiresPermissions("wechat:wxuser:list")
	public R getWxUserPage(@RequestParam Map<String, Object> params) {

		//查询列表数据
		Query query = new Query(params);

		List<WxUserEntity> wxUserEntities = tStoreWxUserService.queryList(query);
		int count = tStoreWxUserService.queryTotal(query);
		return R.page(wxUserEntities, count);
	}



	

	
}
