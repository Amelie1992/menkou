package com.wzlue.jobApplication.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 入职用户打卡表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-29 18:46:15
 */
public class MemberClockEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//用户id
	private Integer userNo;
	//打卡时间
	private Date clockTime;
	//打卡类型
	private Integer clockType;
	//打卡地址
	private String clockAddress;
	//备注说明
	private String remark;
	//设备id
	private String deviceId;
	//逻辑删除标记（0：显示；1：隐藏）
	private String delFlag;
	//创建者
	private String createId;
	//创建时间
	private Date createDate;
	//更新者
	private String updateId;
	//更新时间
	private Date updateDate;

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设置：用户id
	 */
	public void setUserNo(Integer userNo) {
		this.userNo = userNo;
	}
	/**
	 * 获取：用户id
	 */
	public Integer getUserNo() {
		return userNo;
	}
	/**
	 * 设置：打卡时间
	 */
	public void setClockTime(Date clockTime) {
		this.clockTime = clockTime;
	}
	/**
	 * 获取：打卡时间
	 */
	public Date getClockTime() {
		return clockTime;
	}
	/**
	 * 设置：打卡类型
	 */
	public void setClockType(Integer clockType) {
		this.clockType = clockType;
	}
	/**
	 * 获取：打卡类型
	 */
	public Integer getClockType() {
		return clockType;
	}
	/**
	 * 设置：打卡地址
	 */
	public void setClockAddress(String clockAddress) {
		this.clockAddress = clockAddress;
	}
	/**
	 * 获取：打卡地址
	 */
	public String getClockAddress() {
		return clockAddress;
	}
	/**
	 * 设置：备注说明
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注说明
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：设备id
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	/**
	 * 获取：设备id
	 */
	public String getDeviceId() {
		return deviceId;
	}
	/**
	 * 设置：创建者
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	/**
	 * 获取：创建者
	 */
	public String getCreateId() {
		return createId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：更新者
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	/**
	 * 获取：更新者
	 */
	public String getUpdateId() {
		return updateId;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
}
