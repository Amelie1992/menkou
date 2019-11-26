package com.wzlue.wechat.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 图片表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-09 16:53:50
 */
public class SysImageEntity2 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//图片名称
	private String picName;
	//图片路径
	private String picUrl;
	//图片类型
	private String picType;
	//关联表的表名
	private String type;
	//备注
	private String remark;
	//创建人
	private String createBy;
	//创建时间
	private Date createDate;
	//排序
	private Integer sort;
	//关联表的id
	private Long code;

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
	 * 设置：图片名称
	 */
	public void setPicName(String picName) {
		this.picName = picName;
	}
	/**
	 * 获取：图片名称
	 */
	public String getPicName() {
		return picName;
	}
	/**
	 * 设置：图片路径
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 获取：图片路径
	 */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * 设置：图片类型
	 */
	public void setPicType(String picType) {
		this.picType = picType;
	}
	/**
	 * 获取：图片类型
	 */
	public String getPicType() {
		return picType;
	}
	/**
	 * 设置：关联表的表名
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获取：关联表的表名
	 */
	public String getType() {
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
	 * 设置：创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return createBy;
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
	 * 设置：排序
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * 设置：关联表的id
	 */
	public void setCode(Long code) {
		this.code = code;
	}
	/**
	 * 获取：关联表的id
	 */
	public Long getCode() {
		return code;
	}
}
