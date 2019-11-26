
package com.wzlue.app.controller.bonus;

import com.wzlue.app.common.annotation.Login;
import com.wzlue.app.common.annotation.LoginUser;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.jobApplication.entity.BonusRecordEntity;
import com.wzlue.jobApplication.service.BonusRecordService;
import com.wzlue.wechat.entity.WxUserEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 推荐奖励金记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-23 15:54:07
 */
@RestController
@RequestMapping("/app/bonusrecord")
public class BonusRecordController {
	@Autowired
	private BonusRecordService bonusRecordService;

	/**
	 * 记录
	 * type 类型 1奖励金记录  2提现记录
	 *
	 * token
	 */
	@Login
	@GetMapping("/list")
	public R list(@RequestParam Map<String, Object> params, @LoginUser WxUserEntity wxUser){
		//不分页
//        Query query = new Query(params);
        params.put("openid",wxUser.getId());

		List<BonusRecordEntity> bonusRecordList = bonusRecordService.queryList(params);

		int total = bonusRecordList.size();
//		int total = bonusRecordService.queryTotal(params);

		return R.page(bonusRecordList,total).put("bonus",wxUser.getBonus());
	}
	

}
