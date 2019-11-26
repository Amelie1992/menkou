package com.wzlue.store.entity;

import com.wzlue.common.annotation.CreateTime;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店签到记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
@Data
public class TStoreSignInRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//签到id
	private Long id;
	//用户id
	private String userId;
	//用户标识
	private String openId;
	//公众号id
	private String appId;
	//创建时间
	@CreateTime
	private Date createTime;
	//备注
	private String remarks;
	//连续签到天数
	private Integer countDate;

	private String wname;

}
