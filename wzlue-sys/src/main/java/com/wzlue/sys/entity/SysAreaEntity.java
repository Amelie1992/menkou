package com.wzlue.sys.entity;

import java.io.Serializable;


/**
 * 
 * 
 * @author YJ
 * @email wzlue.com
 * @date 2018-06-11 14:13:34
 */
public class SysAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//ID
	private Integer id;
	//栏目名
	private String areaname;
	//父栏目
	private Integer parentid;
	//
	private String shortname;
	//
	private Integer areacode;
	//
	private Integer zipcode;
	//
	private String pinyin;
	//
	private String lng;
	//
	private String lat;
	//
	private Integer level;
	//
	private String position;
	//排序
	private Integer sort;

	/**
	 * 设置：ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：栏目名
	 */
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	/**
	 * 获取：栏目名
	 */
	public String getAreaname() {
		return areaname;
	}
	/**
	 * 设置：父栏目
	 */
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	/**
	 * 获取：父栏目
	 */
	public Integer getParentid() {
		return parentid;
	}
	/**
	 * 设置：
	 */
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	/**
	 * 获取：
	 */
	public String getShortname() {
		return shortname;
	}
	/**
	 * 设置：
	 */
	public void setAreacode(Integer areacode) {
		this.areacode = areacode;
	}
	/**
	 * 获取：
	 */
	public Integer getAreacode() {
		return areacode;
	}
	/**
	 * 设置：
	 */
	public void setZipcode(Integer zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * 获取：
	 */
	public Integer getZipcode() {
		return zipcode;
	}
	/**
	 * 设置：
	 */
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	/**
	 * 获取：
	 */
	public String getPinyin() {
		return pinyin;
	}
	/**
	 * 设置：
	 */
	public void setLng(String lng) {
		this.lng = lng;
	}
	/**
	 * 获取：
	 */
	public String getLng() {
		return lng;
	}
	/**
	 * 设置：
	 */
	public void setLat(String lat) {
		this.lat = lat;
	}
	/**
	 * 获取：
	 */
	public String getLat() {
		return lat;
	}
	/**
	 * 设置：
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * 获取：
	 */
	public String getPosition() {
		return position;
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
}
