
package com.wzlue.web.controller.store;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.R;

import java.util.Date;
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

import com.wzlue.store.entity.WxAppThemeColorEntity;
import com.wzlue.store.service.WxAppThemeColorService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 门店（自定义）主题色
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-06 15:40:32
 */
@RestController
@RequestMapping("/store/wxappthemecolor")
public class WxAppThemeColorController extends AbstractController{
	@Autowired
	private WxAppThemeColorService wxAppThemeColorService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		if (!isStore(query)) {
			return R.page(new Object[]{}, 0);
		}

		List<WxAppThemeColorEntity> wxAppThemeColorList = wxAppThemeColorService.queryList(query);
		int total = wxAppThemeColorService.queryTotal(query);
		
		return R.page(wxAppThemeColorList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{appId}")
	@RequiresPermissions("store:wxappthemecolor:info")
	public R info(@PathVariable("appId") String appId){
		WxAppThemeColorEntity wxAppThemeColor = wxAppThemeColorService.queryObject(appId);
		
		return R.ok().put("wxAppThemeColor", wxAppThemeColor);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("store:wxappthemecolor:save")
	public R save(@RequestBody WxAppThemeColorEntity wxAppThemeColor){
        if (StrUtil.isBlank(wxAppThemeColor.getAppId())) {
            return R.error("appId不能为空");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("appId", wxAppThemeColor.getAppId());
        int i = wxAppThemeColorService.queryTotal(map);
        if (i > 0) {
            return R.error("门店只能保存一条岗位种类信息");
        }

		wxAppThemeColorService.save(wxAppThemeColor);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("store:wxappthemecolor:update")
	public R update(@RequestBody WxAppThemeColorEntity wxAppThemeColor){
	    wxAppThemeColor.setUpdateTime(new Date());
		wxAppThemeColorService.update(wxAppThemeColor);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("store:wxappthemecolor:delete")
	public R delete(@RequestBody String[] appIds){
		wxAppThemeColorService.deleteBatch(appIds);
		
		return R.ok();
	}
	
}
