package com.wzlue.draw.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 积分抽奖记录（我的奖品）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
@Data
public class IntegralDrawRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//
	private String openid;
	//积分记录id
	private Long integralRecordId;
	//消耗的积分
	private Integer consumeIntegral;
	//奖项id
	private Long prizeId;
	//奖项名
	private String prizeName;
	//奖项排序 1-8等奖
	private Integer prizeSort;
	//分类：1中奖；2未中奖
	private Integer type;
	//状态：1未兑奖；2已兑奖
	private Integer state;
	//创建时间
	private Date createTime;
    //兑奖时间
    private Date exchangeTime;
	//微信名
    private String wname;
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
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	/**
	 * 获取：
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * 设置：积分记录id
	 */
	public void setIntegralRecordId(Long integralRecordId) {
		this.integralRecordId = integralRecordId;
	}
	/**
	 * 获取：积分记录id
	 */
	public Long getIntegralRecordId() {
		return integralRecordId;
	}
	/**
	 * 设置：消耗的积分
	 */
	public void setConsumeIntegral(Integer consumeIntegral) {
		this.consumeIntegral = consumeIntegral;
	}
	/**
	 * 获取：消耗的积分
	 */
	public Integer getConsumeIntegral() {
		return consumeIntegral;
	}
	/**
	 * 设置：奖项id
	 */
	public void setPrizeId(Long prizeId) {
		this.prizeId = prizeId;
	}
	/**
	 * 获取：奖项id
	 */
	public Long getPrizeId() {
		return prizeId;
	}
	/**
	 * 设置：奖项名
	 */
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}
	/**
	 * 获取：奖项名
	 */
	public String getPrizeName() {
		return prizeName;
	}
	/**
	 * 设置：奖项排序 1-8等奖
	 */
	public void setPrizeSort(Integer prizeSort) {
		this.prizeSort = prizeSort;
	}
	/**
	 * 获取：奖项排序 1-8等奖
	 */
	public Integer getPrizeSort() {
		return prizeSort;
	}
	/**
	 * 设置：分类：1中奖；2未中奖
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：分类：1中奖；2未中奖
	 */
	public Integer getType() {
		return type;
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
}
