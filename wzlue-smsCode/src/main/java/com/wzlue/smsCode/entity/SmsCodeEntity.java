package com.wzlue.smsCode.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 短信验证码（含过期时间）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-01 10:47:38
 */
@Data
public class SmsCodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//openid
	private String openid;
	//手机号
	private String mobile;
	//验证码
	private String code;
	//创建时间
	private Date createTime;
	//有效时间
	private String effectiveTime;
	//过期时间
	private Date expirationTime;
	//计数（今天的）
	private Integer count;
	//状态：1有效；2失效
	private Integer state;
    //请求间隔时间
    private String intervalTime;
    //允许请求时间
    private Date permissibleRequestTime;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：验证码
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：验证码
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：有效时间
	 */
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	/**
	 * 获取：有效时间
	 */
	public String getEffectiveTime() {
		return effectiveTime;
	}
	/**
	 * 设置：过期时间
	 */
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	/**
	 * 获取：过期时间
	 */
	public Date getExpirationTime() {
		return expirationTime;
	}
	/**
	 * 设置：计数（今天的）
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	/**
	 * 获取：计数（今天的）
	 */
	public Integer getCount() {
		return count;
	}
	/**
	 * 设置：状态：1有效；2失效
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：状态：1有效；2失效
	 */
	public Integer getState() {
		return state;
	}
}
