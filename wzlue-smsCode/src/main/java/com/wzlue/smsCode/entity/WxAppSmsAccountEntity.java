package com.wzlue.smsCode.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店短信账户
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-22 13:49:45
 */
public class WxAppSmsAccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private String appId;
	//
	private String appName;
	//账户
	private String accountSid;
	//秘钥
	private String accountApikey;
	//短信模板---验证码
	private String tplId;
	//短信模板---供人通知
	private String tplIdGr;
	//短信模板---供人审核通知
	private String tplIdSh;
	//
	private Date createTime;

	/**
	 * 设置：
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取：
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置：
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * 获取：
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * 设置：账户
	 */
	public void setAccountSid(String accountSid) {
		this.accountSid = accountSid;
	}
	/**
	 * 获取：账户
	 */
	public String getAccountSid() {
		return accountSid;
	}
	/**
	 * 设置：秘钥
	 */
	public void setAccountApikey(String accountApikey) {
		this.accountApikey = accountApikey;
	}
	/**
	 * 获取：秘钥
	 */
	public String getAccountApikey() {
		return accountApikey;
	}
	/**
	 * 设置：短信模板---验证码
	 */
	public void setTplId(String tplId) {
		this.tplId = tplId;
	}
	/**
	 * 获取：短信模板---验证码
	 */
	public String getTplId() {
		return tplId;
	}
	/**
	 * 设置：短信模板---供人通知
	 */
	public void setTplIdGr(String tplIdGr) {
		this.tplIdGr = tplIdGr;
	}
	/**
	 * 获取：短信模板---供人通知
	 */
	public String getTplIdGr() {
		return tplIdGr;
	}
	/**
	 * 设置：短信模板---供人审核通知
	 */
	public void setTplIdSh(String tplIdSh) {
		this.tplIdSh = tplIdSh;
	}
	/**
	 * 获取：短信模板---供人审核通知
	 */
	public String getTplIdSh() {
		return tplIdSh;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
