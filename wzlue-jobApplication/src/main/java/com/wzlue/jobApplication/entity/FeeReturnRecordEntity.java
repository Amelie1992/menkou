package com.wzlue.jobApplication.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;


/**
 * 返费记录表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-05 09:59:54
 */
public class FeeReturnRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//返费记录id
	private Long id;
	//入职员工id
	private Long jobId;
	//返费规则Id
	private Long feeId;
	//数额
	private BigDecimal amount;
	//类型：1返费2提现 
	private Integer type;
	//备注
	private String remark;
	//
	private String appId;
	//删除标志(1已删除2未删除)
	private Integer delFlag;
	//
	private String createId;
	//
	private Date createDate;
	//
	private String updateId;
	//
	private Date updateDate;

	//入职员工
	private JobApplicationEntity jobApplication;

	//公司名
	private String companyName;

	public Long getFeeId() {
		return feeId;
	}

	public void setFeeId(Long feeId) {
		this.feeId = feeId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public JobApplicationEntity getJobApplication() {
		return jobApplication;
	}

	public void setJobApplication(JobApplicationEntity jobApplication) {
		this.jobApplication = jobApplication;
	}

	/**
	 * 设置：返费记录id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：返费记录id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：入职员工id
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	/**
	 * 获取：入职员工id
	 */
	public Long getJobId() {
		return jobId;
	}
	/**
	 * 设置：数额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：数额
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：类型：1返费2提现 
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型：1返费2提现 
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
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
	 * 设置：删除标志(1已删除2未删除)
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：删除标志(1已删除2未删除)
	 */
	public Integer getDelFlag() {
		return delFlag;
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
}
