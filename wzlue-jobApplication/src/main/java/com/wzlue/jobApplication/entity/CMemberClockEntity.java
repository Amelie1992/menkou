package com.wzlue.jobApplication.entity;

import com.wzlue.recruitment.entity.ShopRecruitmentEntity;

import java.io.Serializable;
import java.util.Date;


/**
 * 入职用户打卡表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 09:33:48
 */
public class CMemberClockEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//微信用户唯一标识
	private String openId;
	//入职用户id
	private Long userNo;
	//门店招聘id
	private Long shopRecruitmentId;
	//打卡时间
	private Date clockTime;
	//打卡类型(1打卡2补卡)
	private Integer clockType;
	//打卡地址
	private String clockAddress;
	//备注说明
	private String remark;
	//设备id
	private String deviceId;
	//逻辑删除标记（1已删除2未删除）
	private String delFlag;
	//应用ID
	private String appId;
	//创建者
	private String createId;
	//创建时间
	private Date createDate;
	//更新者
	private String updateId;
	//更新时间
	private Date updateDate;
	//打卡周期记录
	private Integer numFlag;

	//报名用户信息
	private JobApplicationEntity jobApplication;
	//门店招聘信息
	private ShopRecruitmentEntity shopRecruitment;

	//表名
	private String tableName;

	//是否可以补卡(1是2否)
	private Integer toMend;

	public Integer getToMend() {
		return toMend;
	}

	public void setToMend(Integer toMend) {
		this.toMend = toMend;
	}

	public Integer getNumFlag() {
		return numFlag;
	}

	public void setNumFlag(Integer numFlag) {
		this.numFlag = numFlag;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public JobApplicationEntity getJobApplication() {
		return jobApplication;
	}

	public void setJobApplication(JobApplicationEntity jobApplication) {
		this.jobApplication = jobApplication;
	}

	public ShopRecruitmentEntity getShopRecruitment() {
		return shopRecruitment;
	}

	public void setShopRecruitment(ShopRecruitmentEntity shopRecruitment) {
		this.shopRecruitment = shopRecruitment;
	}

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：微信用户唯一标识
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取：微信用户唯一标识
	 */
	public String getOpenId() {
		return openId;
	}
	/**
	 * 设置：入职用户id
	 */
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	/**
	 * 获取：入职用户id
	 */
	public Long getUserNo() {
		return userNo;
	}
	/**
	 * 设置：门店招聘id
	 */
	public void setShopRecruitmentId(Long shopRecruitmentId) {
		this.shopRecruitmentId = shopRecruitmentId;
	}
	/**
	 * 获取：门店招聘id
	 */
	public Long getShopRecruitmentId() {
		return shopRecruitmentId;
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
	 * 设置：打卡类型(1打卡2补卡)
	 */
	public void setClockType(Integer clockType) {
		this.clockType = clockType;
	}
	/**
	 * 获取：打卡类型(1打卡2补卡)
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
	 * 设置：逻辑删除标记（1已删除2未删除）
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：逻辑删除标记（1已删除2未删除）
	 */
	public String getDelFlag() {
		return delFlag;
	}
	/**
	 * 设置：应用ID
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取：应用ID
	 */
	public String getAppId() {
		return appId;
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
