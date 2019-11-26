
package com.wzlue.web.controller.store;

import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.common.utils.AjaxResult;
import com.wzlue.common.utils.ExcelUtil;
import com.wzlue.store.entity.TStoreSignInRecordEntity;
import com.wzlue.store.service.TStoreSignInRecordService;
import com.wzlue.store.service.TStoreWxUserService;
import com.wzlue.web.controller.sys.AbstractController;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.entity.WxUserEntity;
import com.wzlue.wechat.service.WxAppService;
import com.wzlue.wechat.service.WxUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/store/tstorewxuser")
public class TStoreWxUserController extends AbstractController{
	@Autowired
	private TStoreWxUserService tStoreWxUserService;
    @Value("${fileupload.filepath}")
    String filePath;

	/**
	 * 分页查询
	 *
	 * @param params tagidList 标签
	 * @return
	 */
	@GetMapping("/list")
	public R getWxUserPage(@RequestParam Map<String, Object> params) {

		//查询列表数据
		Query query = new Query(params);

		if(!isStore(query)){
			return R.page(new Object[]{},0);
		}
		List<WxUserEntity> wxUserEntities = tStoreWxUserService.queryList(query);
		int count = tStoreWxUserService.queryTotal(query);
		return R.page(wxUserEntities, count);
	}




	/**
	 *积分修改
	 */
	@SysLog("积分修改")
	@RequiresPermissions("store:tstorewxuser:editIntegral")
	@RequestMapping("/editIntegral")
	public R editIntegral(String id, int integral){

		return tStoreWxUserService.editIntegral(id,integral);
	}


	/**
	 * 下载 吸粉记录
	 */
	@SysLog("下载吸粉记录")
	@PostMapping("/export/{type}")
	@ResponseBody
	public AjaxResult export(@PathVariable("type") String type, @RequestBody Map<String, Object> params)
	{

		List<WxUserEntity> list = null;
		if("0".equals(type)) {
			Query query = new Query(params);
			if(isStore(query)){
				list = tStoreWxUserService.queryToExport(query);
			}
		} else {
			List ids = (ArrayList) params.get("ids");
			Object[] idObjs = ids.toArray() ;
			list = tStoreWxUserService.queryToExportByIds(idObjs);
		}
		ExcelUtil<WxUserEntity> util = new ExcelUtil<WxUserEntity>(WxUserEntity.class);
		return util.exportExcel(list, "吸粉数据",filePath);
	}

	
}
