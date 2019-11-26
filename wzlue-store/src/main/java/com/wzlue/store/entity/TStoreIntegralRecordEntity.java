package com.wzlue.store.entity;

import com.wzlue.common.annotation.CreateTime;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店积分记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
@Data
public class TStoreIntegralRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//用户id
	private String userId;
	//用户标识
	private String openId;
	//公众号id
	private String appId;
	//备注
	private String remarks;
	//积分
	private Integer integral;
	//创建时间
	@CreateTime
	private Date createTime;
	//积分类型：1签到；2新手任务；3积分修改；4积分抽奖
	private Integer integralType;

	private String wname;
}
