package com.wzlue.recruitment.entity;

import com.wzlue.sys.entity.SysImageEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 申请材料（上平台招聘的申请材料）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-09 16:51:59
 */
@Data
public class ApplicationMaterialsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//招聘id
	private Long recruitmentId;
	//办公场地面积（平方米）
	private String area;
	//参保人数（人）
	private String people;
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

	//图片材料
    private List<SysImageEntity> images;

    //联系电话（用于供人的短信通知）
	private String phone;


    public List<SysImageEntity> getImages() {
        return images;
    }

    public void setImages(List<SysImageEntity> images) {
        this.images = images;
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
	 * 设置：办公场地面积（平方米）
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * 获取：办公场地面积（平方米）
	 */
	public String getArea() {
		return area;
	}
	/**
	 * 设置：参保人数（人）
	 */
	public void setPeople(String people) {
		this.people = people;
	}
	/**
	 * 获取：参保人数（人）
	 */
	public String getPeople() {
		return people;
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
