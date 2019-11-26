package com.wzlue.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店（自定义）主题色
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-06 15:40:32
 */
@Data
public class WxAppThemeColorEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//appid
	private String appId;
	//背景色 （10种）
	private Integer backcolor;
	//按钮颜色 （10种）
	private Integer buttonColor;
	//形式 （8种）
	private Integer form;
	//
	private Date createTime;
	//
	private Date updateTime;
    //公众号名称
    private String appname;

	/**
	 * 设置：appid
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取：appid
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置：背景色 （10种）
	 */
	public void setBackcolor(Integer backcolor) {
		this.backcolor = backcolor;
	}
	/**
	 * 获取：背景色 （10种）
	 */
	public Integer getBackcolor() {
		return backcolor;
	}
	/**
	 * 设置：按钮颜色 （10种）
	 */
	public void setButtonColor(Integer buttonColor) {
		this.buttonColor = buttonColor;
	}
	/**
	 * 获取：按钮颜色 （10种）
	 */
	public Integer getButtonColor() {
		return buttonColor;
	}
	/**
	 * 设置：形式 （3种）【主动报名框】
	 */
	public void setForm(Integer form) {
		this.form = form;
	}
	/**
	 * 获取：形式 （3种）【主动报名框】
	 */
	public Integer getForm() {
		return form;
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
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
