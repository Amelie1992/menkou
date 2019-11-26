package com.wzlue.recruitment.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * （招聘）返费
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 17:12:55
 */
public class FeeReturnEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//天数
	private Integer days;
	//奖励（元）
	private BigDecimal reward;
	//招聘id
	private Long recruitmentId;
	// 应用ID
	private String appId;
	//
	private String createId;
	//
	private Date createDate;
	//
	private String updateId;
	//
	private Date updateDate;
	//软删除标识：1已删除 2未删除
	private Integer delFlag;

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
	 * 设置：天数
	 */
	public void setDays(Integer days) {
		this.days = days;
	}
	/**
	 * 获取：天数
	 */
	public Integer getDays() {
		return days;
	}
	/**
	 * 设置：奖励（元）
	 */
	public void setReward(BigDecimal reward) {
		this.reward = reward;
	}
	/**
	 * 获取：奖励（元）
	 */
	public BigDecimal getReward() {
		return reward;
	}
	/**
	 * 设置：招聘id
	 */
	public void setRecruitmentId(Long recruitmentId) {
		this.recruitmentId = recruitmentId;
	}
	/**
	 * 获取：招聘id
	 */
	public Long getRecruitmentId() {
		return recruitmentId;
	}
	/**
	 * 设置： 应用ID
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取： 应用ID
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置：
	 */
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	/**
	 * 获取：
	 */
	public String getCreateId() {
		return createId;
	}
	/**
	 * 设置：
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：
	 */
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	/**
	 * 获取：
	 */
	public String getUpdateId() {
		return updateId;
	}
	/**
	 * 设置：
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：软删除标识：1已删除 2未删除
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：软删除标识：1已删除 2未删除
	 */
	public Integer getDelFlag() {
		return delFlag;
	}
}
