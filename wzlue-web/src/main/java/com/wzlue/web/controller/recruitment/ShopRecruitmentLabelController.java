
package com.wzlue.web.controller.recruitment;

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

import com.wzlue.recruitment.entity.ShopRecruitmentLabelEntity;
import com.wzlue.recruitment.service.ShopRecruitmentLabelService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 门店招聘-标签
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 10:51:26
 */
@RestController
@RequestMapping("/recruitment/shoprecruitmentlabel")
public class ShopRecruitmentLabelController extends AbstractController{
	@Autowired
	private ShopRecruitmentLabelService shopRecruitmentLabelService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ShopRecruitmentLabelEntity> shopRecruitmentLabelList = shopRecruitmentLabelService.queryList(query);
		int total = shopRecruitmentLabelService.queryTotal(query);
		
		return R.page(shopRecruitmentLabelList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recruitment:shoprecruitmentlabel:info")
	public R info(@PathVariable("id") Long id){
		ShopRecruitmentLabelEntity shopRecruitmentLabel = shopRecruitmentLabelService.queryObject(id);
		
		return R.ok().put("shopRecruitmentLabel", shopRecruitmentLabel);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recruitment:shoprecruitmentlabel:save")
	public R save(@RequestBody ShopRecruitmentLabelEntity shopRecruitmentLabel){
		shopRecruitmentLabelService.save(shopRecruitmentLabel);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recruitment:shoprecruitmentlabel:update")
	public R update(@RequestBody ShopRecruitmentLabelEntity shopRecruitmentLabel){
		shopRecruitmentLabelService.update(shopRecruitmentLabel);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recruitment:shoprecruitmentlabel:delete")
	public R delete(@RequestBody Long[] ids){
		shopRecruitmentLabelService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
