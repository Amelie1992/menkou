
package com.wzlue.web.controller.bonus;

import com.wzlue.common.base.R;

import java.math.BigDecimal;
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

import com.wzlue.jobApplication.entity.BonusRecordEntity;
import com.wzlue.jobApplication.service.BonusRecordService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 推荐奖励金记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-23 15:54:07
 */
@RestController
@RequestMapping("/jobApplication/bonusrecord")
public class BonusRecordController extends AbstractController{
	@Autowired
	private BonusRecordService bonusRecordService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BonusRecordEntity> bonusRecordList = bonusRecordService.queryList(query);
		int total = bonusRecordService.queryTotal(query);
		
		return R.page(bonusRecordList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("jobApplication:bonusrecord:info")
	public R info(@PathVariable("id") Long id){
		BonusRecordEntity bonusRecord = bonusRecordService.queryObject(id);
		
		return R.ok().put("bonusRecord", bonusRecord);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("jobApplication:bonusrecord:save")
	public R save(@RequestBody BonusRecordEntity bonusRecord){
		bonusRecordService.save(bonusRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("jobApplication:bonusrecord:update")
	public R update(@RequestBody BonusRecordEntity bonusRecord){
		bonusRecordService.update(bonusRecord);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("jobApplication:bonusrecord:delete")
	public R delete(@RequestBody Long[] ids){
		bonusRecordService.deleteBatch(ids);
		
		return R.ok();
	}

	/**
	 * 奖励金提现
	 */
	@RequestMapping("/withdraw")
	public R withdraw(String openid, BigDecimal amount) {
		bonusRecordService.withdraw(openid,amount);

		return R.ok();
	}


}
