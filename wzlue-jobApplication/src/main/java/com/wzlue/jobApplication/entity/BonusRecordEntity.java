package com.wzlue.jobApplication.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;


/**
 * 推荐奖励金记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-23 15:54:07
 */
@Data
public class BonusRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private Long id;
	//openid
	private String openid;
	//被推荐人报名id
	private Long jobApplicationId;
	//金额
	private BigDecimal amount;
	//类型：1推荐奖励;2提现 
	private Integer type;
	//备注
	private String remark;
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


	//被推荐人openid
    private String openid2;
    //被推荐人 微信名
    private String nickName2;
	//招聘公司
	private String zpCompany;
	//招聘标题
    private String zpTitle;

    //推荐人 微信名
	private String wname;

}
