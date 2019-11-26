package com.wzlue.jobApplication.entity;

import com.wzlue.recruitment.entity.ShopRecruitmentEntity;

import java.io.Serializable;
import java.util.Date;


/**
 * 打卡地址设置
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-29 18:44:41
 */
public class ClockInEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private Long id;
	//应用id
	private String appId;
	//入职用户Id
	private Long memberId;
	//招聘id
	private Long recruitId;
	//x坐标
	private Double coordinateX;
	//y坐标
	private Double coordinateY;
	//有效范围
	private Double effectiveDistance;
	//打卡显示名称
	private String displayAddress;
	//详细地址
	private String address;
	//状态：1正常2禁用
	private String type;
	//归属（1入职个人 2招聘）
	private Integer ascription;
	//删除标志
	private Integer delFlag;
	//创建者
	private String createId;
	//创建时间
	private Date createDate;
	//更新者
	private String updateId;
	//更新时间
	private Date updateDate;

	//关联入职员工
	private JobApplicationEntity jobApplicationEntity;

	//关联门店招聘表
	private ShopRecruitmentEntity shopRecruitmentEntity;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAscription() {
		return ascription;
	}

	public void setAscription(Integer ascription) {
		this.ascription = ascription;
	}

	public ShopRecruitmentEntity getShopRecruitmentEntity() {
		return shopRecruitmentEntity;
	}

	public void setShopRecruitmentEntity(ShopRecruitmentEntity shopRecruitmentEntity) {
		this.shopRecruitmentEntity = shopRecruitmentEntity;
	}

	public JobApplicationEntity getJobApplicationEntity() {
		return jobApplicationEntity;
	}

	public void setJobApplicationEntity(JobApplicationEntity jobApplicationEntity) {
		this.jobApplicationEntity = jobApplicationEntity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设置：应用id
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取：应用id
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置：入职用户Id
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取：入职用户Id
	 */
	public Long getMemberId() {
		return memberId;
	}
	/**
	 * 设置：招聘id
	 */
	public void setRecruitId(Long recruitId) {
		this.recruitId = recruitId;
	}
	/**
	 * 获取：招聘id
	 */
	public Long getRecruitId() {
		return recruitId;
	}
	/**
	 * 设置：x坐标
	 */
	public void setCoordinateX(Double coordinateX) {
		this.coordinateX = coordinateX;
	}
	/**
	 * 获取：x坐标
	 */
	public Double getCoordinateX() {
		return coordinateX;
	}
	/**
	 * 设置：y坐标
	 */
	public void setCoordinateY(Double coordinateY) {
		this.coordinateY = coordinateY;
	}
	/**
	 * 获取：y坐标
	 */
	public Double getCoordinateY() {
		return coordinateY;
	}
	/**
	 * 设置：有效范围
	 */
	public void setEffectiveDistance(Double effectiveDistance) {
		this.effectiveDistance = effectiveDistance;
	}
	/**
	 * 获取：有效范围
	 */
	public Double getEffectiveDistance() {
		return effectiveDistance;
	}
	/**
	 * 设置：打卡显示名称
	 */
	public void setDisplayAddress(String displayAddress) {
		this.displayAddress = displayAddress;
	}
	/**
	 * 获取：打卡显示名称
	 */
	public String getDisplayAddress() {
		return displayAddress;
	}
	/**
	 * 设置：详细地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：详细地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：删除标志
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：删除标志
	 */
	public Integer getDelFlag() {
		return delFlag;
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
