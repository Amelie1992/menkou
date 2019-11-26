
package com.wzlue.web.controller.draw;

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

import com.wzlue.draw.entity.IntegralDrawPrizeEntity;
import com.wzlue.draw.service.IntegralDrawPrizeService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 积分抽奖奖项
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
@RestController
@RequestMapping("/draw/integraldrawprize")
public class IntegralDrawPrizeController extends AbstractController{
	@Autowired
	private IntegralDrawPrizeService integralDrawPrizeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<IntegralDrawPrizeEntity> integralDrawPrizeList = integralDrawPrizeService.queryList(query);
		int total = integralDrawPrizeService.queryTotal(query);
		
		return R.page(integralDrawPrizeList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("draw:integraldrawprize:info")
	public R info(@PathVariable("id") Long id){
		IntegralDrawPrizeEntity integralDrawPrize = integralDrawPrizeService.queryObject(id);
		
		return R.ok().put("integralDrawPrize", integralDrawPrize);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("draw:integraldrawprize:save")
	public R save(@RequestBody IntegralDrawPrizeEntity integralDrawPrize){
		integralDrawPrizeService.save(integralDrawPrize);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("draw:integraldrawprize:update")
	public R update(@RequestBody IntegralDrawPrizeEntity integralDrawPrize){
		integralDrawPrizeService.update(integralDrawPrize);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("draw:integraldrawprize:delete")
	public R delete(@RequestBody Long[] ids){
		integralDrawPrizeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
