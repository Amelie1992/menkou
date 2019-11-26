package com.wzlue.draw.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 积分抽奖奖项
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
public class IntegralDrawPrizeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private String appId;
	//奖项
	private String prize;
	//概率
	private Integer probability;
	//排序1~8等奖
	private Integer sort;
	//备注
	private String remarks;
	//创建时间
	private Date createTime;
	//修改时间
	private Date updateTime;
	//库存
	private Integer stock;

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
	 * 设置：奖项
	 */
	public void setPrize(String prize) {
		this.prize = prize;
	}
	/**
	 * 获取：奖项
	 */
	public String getPrize() {
		return prize;
	}
	/**
	 * 设置：概率
	 */
	public void setProbability(Integer probability) {
		this.probability = probability;
	}
	/**
	 * 获取：概率
	 */
	public Integer getProbability() {
		return probability;
	}
	/**
	 * 设置：排序1~8等奖
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	/**
	 * 获取：排序1~8等奖
	 */
	public Integer getSort() {
		return sort;
	}
	/**
	 * 设置：备注
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * 获取：备注
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：库存
	 */
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	/**
	 * 获取：库存
	 */
	public Integer getStock() {
		return stock;
	}
}
