
package com.wzlue.web.controller.smsCode;

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

import com.wzlue.smsCode.entity.SmsCodeEntity;
import com.wzlue.smsCode.service.SmsCodeService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 短信验证码（含过期时间）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-01 10:47:38
 */
@RestController
@RequestMapping("/smsCode/smscode")
public class SmsCodeController extends AbstractController{
	@Autowired
	private SmsCodeService smsCodeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SmsCodeEntity> smsCodeList = smsCodeService.queryList(query);
		int total = smsCodeService.queryTotal(query);
		
		return R.page(smsCodeList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("smsCode:smscode:info")
	public R info(@PathVariable("id") Long id){
		SmsCodeEntity smsCode = smsCodeService.queryObject(id);
		
		return R.ok().put("smsCode", smsCode);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("smsCode:smscode:save")
	public R save(@RequestBody SmsCodeEntity smsCode){
		smsCodeService.save(smsCode);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("smsCode:smscode:update")
	public R update(@RequestBody SmsCodeEntity smsCode){
		smsCodeService.update(smsCode);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("smsCode:smscode:delete")
	public R delete(@RequestBody Long[] ids){
		smsCodeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
