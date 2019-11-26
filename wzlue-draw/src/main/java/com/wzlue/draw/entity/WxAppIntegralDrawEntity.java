package com.wzlue.draw.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 门店（自定义）积分抽奖活动   （付费开通） 
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
@Data
public class WxAppIntegralDrawEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//appid
	private String appId;
	//单次抽奖消耗多少积分
	private Integer consume;
	//状态：1启用；2禁用
	private Integer state;
	//备注
	private String remarks;
	//功能开通时间
	private Date createTime;
	//修改时间
	private Date updateTime;
	//修改人
	private Long updateBy;

	private String appname;
	//奖项
    private List<IntegralDrawPrizeEntity> prizeList;

	/**
	 * 设置：appid
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取：appid
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置：单次抽奖消耗多少积分
	 */
	public void setConsume(Integer consume) {
		this.consume = consume;
	}
	/**
	 * 获取：单次抽奖消耗多少积分
	 */
	public Integer getConsume() {
		return consume;
	}
	/**
	 * 设置：状态：1启用；2禁用
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：状态：1启用；2禁用
	 */
	public Integer getState() {
		return state;
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
	 * 设置：功能开通时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：功能开通时间
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
	 * 设置：修改人
	 */
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：修改人
	 */
	public Long getUpdateBy() {
		return updateBy;
	}
}
