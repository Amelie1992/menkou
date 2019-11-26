package com.wzlue.notice.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 通知
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-28 14:34:50
 */
@Data
public class NoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//归属：1门店；2平台
	private Integer belongTo;
	//分类：1供人成交通知
	private Integer type;
	//用人门店
	private String demandAppId;
	//供人门店
	private String supplyAppId;
	//招聘id
	private Long shopRecruitmentId;
	//供人id
	private Long provideStaffId;
	//内容
	private String content;
	//创建时间
	private Date createDate;

	//用人门店
	private String demand;
	//供人门店
	private String supply;

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
	 * 设置：归属：1门店；2平台
	 */
	public void setBelongTo(Integer belongTo) {
		this.belongTo = belongTo;
	}
	/**
	 * 获取：归属：1门店；2平台
	 */
	public Integer getBelongTo() {
		return belongTo;
	}
	/**
	 * 设置：分类：1供人成交通知
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：分类：1供人成交通知
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：用人门店
	 */
	public void setDemandAppId(String demandAppId) {
		this.demandAppId = demandAppId;
	}
	/**
	 * 获取：用人门店
	 */
	public String getDemandAppId() {
		return demandAppId;
	}
	/**
	 * 设置：供人门店
	 */
	public void setSupplyAppId(String supplyAppId) {
		this.supplyAppId = supplyAppId;
	}
	/**
	 * 获取：供人门店
	 */
	public String getSupplyAppId() {
		return supplyAppId;
	}
	/**
	 * 设置：招聘id
	 */
	public void setShopRecruitmentId(Long shopRecruitmentId) {
		this.shopRecruitmentId = shopRecruitmentId;
	}
	/**
	 * 获取：招聘id
	 */
	public Long getShopRecruitmentId() {
		return shopRecruitmentId;
	}
	/**
	 * 设置：供人id
	 */
	public void setProvideStaffId(Long provideStaffId) {
		this.provideStaffId = provideStaffId;
	}
	/**
	 * 获取：供人id
	 */
	public Long getProvideStaffId() {
		return provideStaffId;
	}
	/**
	 * 设置：内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：内容
	 */
	public String getContent() {
		return content;
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
}
