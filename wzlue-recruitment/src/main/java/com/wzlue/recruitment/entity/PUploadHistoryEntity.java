package com.wzlue.recruitment.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 供人历史清单表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 16:13:50
 */
@Data
public class PUploadHistoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//历史清单id
	private Long id;
	//门店招聘id
	private Long shopRecruitmentId;
	//成功条数
	private Long num;
	//备注
	private String remark;
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

	//关联招聘
    private ShopRecruitmentEntity shopRecruitment;
	//供人门店名称
	private String appName;

	//门店确认供人状态：1待门店确认；2已确认接受；3拒绝；	5供人方自己取消
	private Integer confirm;

	// 门店拒绝原因
	private String errorInfo;

	//供人名单list
	private List<ProvidePersonnelEntity> providePersonnel;
}
