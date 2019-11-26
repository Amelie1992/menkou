package com.wzlue.recruitment.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店招聘-标签
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 10:51:26
 */
public class ShopRecruitmentLabelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//门店招聘id
	private Long shopRecruitmentId;
	//标签id
	private Long configId;
	//标签名
	private String configValue;
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


    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

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
	 * 设置：标签id
	 */
	public void setConfigId(Long configId) {
		this.configId = configId;
	}
	/**
	 * 获取：标签id
	 */
	public Long getConfigId() {
		return configId;
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
}
