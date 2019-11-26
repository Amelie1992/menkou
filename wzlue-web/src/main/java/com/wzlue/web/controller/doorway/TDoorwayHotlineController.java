
package com.wzlue.web.controller.doorway;

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

import com.wzlue.doorway.entity.TDoorwayHotlineEntity;
import com.wzlue.doorway.service.TDoorwayHotlineService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 平台热线
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-08 15:00:13
 */
@RestController
@RequestMapping("/doorway/tdoorwayhotline")
public class TDoorwayHotlineController extends AbstractController{
	@Autowired
	private TDoorwayHotlineService tDoorwayHotlineService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<TDoorwayHotlineEntity> tDoorwayHotlineList = tDoorwayHotlineService.queryList(query);
		int total = tDoorwayHotlineService.queryTotal(query);
		
		return R.page(tDoorwayHotlineList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("doorway:tdoorwayhotline:info")
	public R info(@PathVariable("id") Long id){
		TDoorwayHotlineEntity tDoorwayHotline = tDoorwayHotlineService.queryObject(id);
		
		return R.ok().put("tDoorwayHotline", tDoorwayHotline);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("doorway:tdoorwayhotline:save")
	public R save(@RequestBody TDoorwayHotlineEntity tDoorwayHotline){
		int i = tDoorwayHotlineService.queryTotal(null);
		if (i > 0) {
			return R.error("只能保存一条平台热线");
		}
		tDoorwayHotline.setCreateId(getUserId().toString());
		tDoorwayHotlineService.save(tDoorwayHotline);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("doorway:tdoorwayhotline:update")
	public R update(@RequestBody TDoorwayHotlineEntity tDoorwayHotline){
		tDoorwayHotlineService.update(tDoorwayHotline);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("doorway:tdoorwayhotline:delete")
	public R delete(@RequestBody Long[] ids){
		tDoorwayHotlineService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
