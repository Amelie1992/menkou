
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

import com.wzlue.recruitment.entity.FeeReturnEntity;
import com.wzlue.recruitment.service.FeeReturnService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * （招聘）返费
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 17:12:55
 */
@RestController
@RequestMapping("/recruitment/feereturn")
public class FeeReturnController extends AbstractController{
	@Autowired
	private FeeReturnService feeReturnService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<FeeReturnEntity> feeReturnList = feeReturnService.queryList(query);
		int total = feeReturnService.queryTotal(query);
		
		return R.page(feeReturnList,total);
	}


	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("recruitment:feereturn:info")
	public R info(@PathVariable("id") Long id){
		FeeReturnEntity feeReturn = feeReturnService.queryObject(id);

		return R.ok().put("feeReturn", feeReturn);
	}

	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("recruitment:feereturn:save")
	public R save(@RequestBody FeeReturnEntity feeReturn){
		feeReturnService.save(feeReturn);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("recruitment:feereturn:update")
	public R update(@RequestBody FeeReturnEntity feeReturn){
		feeReturnService.update(feeReturn);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("recruitment:feereturn:delete")
	public R delete(@RequestBody Long[] ids){
		feeReturnService.deleteBatch(ids);

		return R.ok();
	}
	
}
