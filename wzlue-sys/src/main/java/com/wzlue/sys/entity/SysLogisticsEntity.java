package com.wzlue.sys.entity;

import java.io.Serializable;


/**
 * 物流表
 * 
 * @author YJ
 * @email wzlue.com
 * @date 2018-06-29 10:01:23
 */
public class SysLogisticsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private String name;
	//服务id
	private String serviceId;
	//code
	private String code;
	//
	private Integer sort;

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
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：服务id
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	/**
	 * 获取：服务id
	 */
	public String getServiceId() {
		return serviceId;
	}
	/**
	 * 设置：code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：
	 */
	public Integer getSort() {
		return sort;
	}
}
