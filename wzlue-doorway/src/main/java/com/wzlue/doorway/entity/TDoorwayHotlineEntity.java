package com.wzlue.doorway.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 平台热线
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-08 15:00:13
 */
public class TDoorwayHotlineEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//图片
	private String image;
	//热线
	private String hotline;
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
	 * 设置：图片
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：图片
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置：热线
	 */
	public void setHotline(String hotline) {
		this.hotline = hotline;
	}
	/**
	 * 获取：热线
	 */
	public String getHotline() {
		return hotline;
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
