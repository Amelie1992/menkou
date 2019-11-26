package com.wzlue.sys.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 城市信息表
 * 
 * @author 
 * @email 
 * @date 2018-11-26 10:05:57
 */
public class SysCityInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//地址KEY
	private String cityKey;
	//地市ID
	private String cityId;
	//地市编码
	private String cityCode;
	//上级地市编码
	private String subCityCode;
	//地市名称
	private String cityName;
	//区域编号(区号)
	private String areaCode;
	//邮政编码
	private String postalcode;
	//状态(0:有效;1:无效;)
	private String status;
	//末节点标记(0:末节点;1:非末节点;)
	private String endFlag;
	//经度
	private String longitude;
	//纬度
	private String latitude;
	//纠偏后经度
	private String longitudeTrue;
	//纠偏后纬度
	private String latitudeTrue;
	//排序号码
	private Integer rankNo;
	//显示标记(0:显示;1:隐藏;)
	private String showFlag;
	//删除标记(1:是;0:否;)
	private String deleteFlag;
	//新增时间
	private Date addTime;
	//更新时间
	private Date modTime;
	//描述
	private String remarks;

	/**
	 * 设置：地址KEY
	 */
	public void setCityKey(String cityKey) {
		this.cityKey = cityKey;
	}
	/**
	 * 获取：地址KEY
	 */
	public String getCityKey() {
		return cityKey;
	}
	/**
	 * 设置：地市ID
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	/**
	 * 获取：地市ID
	 */
	public String getCityId() {
		return cityId;
	}
	/**
	 * 设置：地市编码
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	/**
	 * 获取：地市编码
	 */
	public String getCityCode() {
		return cityCode;
	}
	/**
	 * 设置：上级地市编码
	 */
	public void setSubCityCode(String subCityCode) {
		this.subCityCode = subCityCode;
	}
	/**
	 * 获取：上级地市编码
	 */
	public String getSubCityCode() {
		return subCityCode;
	}
	/**
	 * 设置：地市名称
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * 获取：地市名称
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * 设置：区域编号(区号)
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	/**
	 * 获取：区域编号(区号)
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * 设置：邮政编码
	 */
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	/**
	 * 获取：邮政编码
	 */
	public String getPostalcode() {
		return postalcode;
	}
	/**
	 * 设置：状态(0:有效;1:无效;)
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取：状态(0:有效;1:无效;)
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置：末节点标记(0:末节点;1:非末节点;)
	 */
	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}
	/**
	 * 获取：末节点标记(0:末节点;1:非末节点;)
	 */
	public String getEndFlag() {
		return endFlag;
	}
	/**
	 * 设置：经度
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * 获取：经度
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * 设置：纬度
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * 获取：纬度
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * 设置：纠偏后经度
	 */
	public void setLongitudeTrue(String longitudeTrue) {
		this.longitudeTrue = longitudeTrue;
	}
	/**
	 * 获取：纠偏后经度
	 */
	public String getLongitudeTrue() {
		return longitudeTrue;
	}
	/**
	 * 设置：纠偏后纬度
	 */
	public void setLatitudeTrue(String latitudeTrue) {
		this.latitudeTrue = latitudeTrue;
	}
	/**
	 * 获取：纠偏后纬度
	 */
	public String getLatitudeTrue() {
		return latitudeTrue;
	}
	/**
	 * 设置：排序号码
	 */
	public void setRankNo(Integer rankNo) {
		this.rankNo = rankNo;
	}
	/**
	 * 获取：排序号码
	 */
	public Integer getRankNo() {
		return rankNo;
	}
	/**
	 * 设置：显示标记(0:显示;1:隐藏;)
	 */
	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}
	/**
	 * 获取：显示标记(0:显示;1:隐藏;)
	 */
	public String getShowFlag() {
		return showFlag;
	}
	/**
	 * 设置：删除标记(1:是;0:否;)
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * 获取：删除标记(1:是;0:否;)
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * 设置：新增时间
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * 获取：新增时间
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getModTime() {
		return modTime;
	}
	/**
	 * 设置：描述
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：描述
	 */
	public String getRemarks() {
		return remarks;
	}
}
