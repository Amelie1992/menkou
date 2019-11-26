
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

import com.wzlue.draw.entity.IntegralDrawRecordEntity;
import com.wzlue.draw.service.IntegralDrawRecordService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 积分抽奖记录（我的奖品）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
@RestController
@RequestMapping("/draw/integraldrawrecord")
public class IntegralDrawRecordController extends AbstractController{
	@Autowired
	private IntegralDrawRecordService integralDrawRecordService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<IntegralDrawRecordEntity> integralDrawRecordList = integralDrawRecordService.queryList(query);
		int total = integralDrawRecordService.queryTotal(query);
		
		return R.page(integralDrawRecordList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("draw:integraldrawrecord:info")
	public R info(@PathVariable("id") Long id){
		IntegralDrawRecordEntity integralDrawRecord = integralDrawRecordService.queryObject(id);
		
		return R.ok().put("integralDrawRecord", integralDrawRecord);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("draw:integraldrawrecord:save")
	public R save(@RequestBody IntegralDrawRecordEntity integralDrawRecord){
		integralDrawRecordService.save(integralDrawRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("draw:integraldrawrecord:update")
	public R update(@RequestBody IntegralDrawRecordEntity integralDrawRecord){
		integralDrawRecordService.update(integralDrawRecord);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("draw:integraldrawrecord:delete")
	public R delete(@RequestBody Long[] ids){
		integralDrawRecordService.deleteBatch(ids);
		
		return R.ok();
	}

    /**
     * 奖品兑换
     */
    @RequestMapping("/exchange")
    public R exchange(@RequestBody Long[] ids){
        integralDrawRecordService.exchangeBatch(ids);

        return R.ok();
    }
}
