package com.wzlue.web.controller.sys;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.common.utils.Constant;
import com.wzlue.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 * 
 * @author chenshun
 * @email wzlue.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected boolean isStore(Query query) {

		SysUserEntity user = getUser();

		if (getUserId() == Constant.SUPER_ADMIN) {
			return  true;

		}else if(StrUtil.isNotBlank(user.getAppId()) && StrUtil.equals(user.getStore(),"1")) {
			query.put("appId", getUser().getAppId());
			return true;

		}else if(StrUtil.isBlank(user.getAppId()) && StrUtil.equals(user.getStore(),"1")) {
			return false;

		}else{
			return true;
		}
	}
}
