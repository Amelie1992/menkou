package com.wzlue.recruitment.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import java.io.Serializable;
import java.util.Date;


/**
 * 供人信息表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 16:13:50
 */
@ExcelTarget("providePersonnel")
public class ProvidePersonnelEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//供人id
	private Long id;
	//门店招聘id
	private Long shopRecruitmentId;
	private String shopTitle;
	//上传历史清单id
	private Long uploadHistoryId;
	//姓名
	@Excel(name="姓名", orderNum = "1")
	private String name;
	//性别:1男；2女
	private Integer sex;
	@Excel(name="性别", orderNum = "2")
	private String genderName;
	//年龄
	@Excel(name="年龄", orderNum = "3")
	private Integer age;
	//省
	@Excel(name="省", orderNum = "4")
	private String province;
	//市
	@Excel(name="市", orderNum = "5")
	private String city;

	//导出错误原因
	@Excel(name="错误原因", orderNum = "6")
	private String errorReason;

	//省
	private String provinceStr;
	//市
	private String cityStr;

	//状态：1待审核 2通过 3拒绝
	private Integer status;
	//拒绝原因
	private String reason;
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

	public String getGenderName() {
		return genderName;
	}

	public void setGenderName(String genderName) {
		this.genderName = genderName;
	}

	public String getErrorReason() {
		return errorReason;
	}

	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}

	public String getProvinceStr() {
		return provinceStr;
	}

	public void setProvinceStr(String provinceStr) {
		this.provinceStr = provinceStr;
	}

	public String getCityStr() {
		return cityStr;
	}

	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}

	public String getShopTitle() {
		return shopTitle;
	}

	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}

	/**
	 * 设置：供人id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：供人id
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
	 * 设置：上传历史清单id
	 */
	public void setUploadHistoryId(Long uploadHistoryId) {
		this.uploadHistoryId = uploadHistoryId;
	}
	/**
	 * 获取：上传历史清单id
	 */
	public Long getUploadHistoryId() {
		return uploadHistoryId;
	}
	/**
	 * 设置：姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：性别:1男；2女
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别:1男；2女
	 */
	public Integer getSex() {
		return sex;
	}
	/**
	 * 设置：年龄
	 */
	public void setAge(Integer age) {
		this.age = age;
	}
	/**
	 * 获取：年龄
	 */
	public Integer getAge() {
		return age;
	}
	/**
	 * 设置：省
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：省
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：状态：1待审核 2通过 3拒绝
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态：1待审核 2通过 3拒绝
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：拒绝原因
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * 获取：拒绝原因
	 */
	public String getReason() {
		return reason;
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
