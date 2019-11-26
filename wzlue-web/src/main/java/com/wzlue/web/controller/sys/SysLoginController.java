package com.wzlue.web.controller.sys;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.wzlue.common.base.R;
import com.wzlue.common.exception.RRException;
import com.wzlue.sys.common.util.ShiroUtils;
import com.wzlue.sys.entity.SysUserEntity;
import com.wzlue.sys.form.LoginForm;
import com.wzlue.sys.service.SysUserService;
import com.wzlue.sys.service.SysUserTokenService;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.service.WxAppService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 登录相关
 * 
 * @author chenshun
 * @email wzlue.com
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private Producer producer;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private WxAppService wxAppService;

	/**
	 * 验证码
	 */
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);
		//保存到shiro session
		ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	public Map<String, Object> login(@RequestBody LoginForm form)throws IOException {
		//本项目已实现，前后端完全分离，但页面还是跟项目放在一起了，所以还是会依赖session
		//如果想把页面单独放到nginx里，实现前后端完全分离，则需要把验证码注释掉(因为不再依赖session了)
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!form.getCaptcha().equalsIgnoreCase(kaptcha)){
			return R.error("验证码不正确");
		}

		//用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return R.error("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return R.error("账号已被锁定,请联系管理员");
		}

		// 查询公众号是否到期
		WxAppEntity wxApp = wxAppService.getById(user.getAppId());
		if (null != wxApp)
			if ("0".equals(wxApp.getExpType()) && (wxApp.getExpDate().compareTo(new Date()) <= 0))
				return R.error("该账号已到期，无法使用！");

		//生成token，并保存到数据库
		return sysUserTokenService.createToken(user.getUserId());
	}


	/**
	 * 退出
	 */
	@RequestMapping(value = "/sys/logout", method = RequestMethod.POST)
	public R logout() {
		sysUserTokenService.logout(getUserId());
		return R.ok();
	}
	
}
