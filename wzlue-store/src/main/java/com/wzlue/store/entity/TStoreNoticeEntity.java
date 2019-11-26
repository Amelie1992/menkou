package com.wzlue.store.entity;

import com.wzlue.common.annotation.CreateTime;
import com.wzlue.common.annotation.UpdateTime;
import com.wzlue.wechat.entity.WxAppEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店公告表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 16:44:30
 */
@Data
public class TStoreNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long noticeId;
	//标题
	private String noticeTitle;
	//内容
	private String noticeContent;
	//公告图
	private String headLogo;
	//排序
	private Long noticeSort;
	//应用编号
	private String appId;
	//创建者
	private String createId;
	//创建时间
	@CreateTime
	private Date createDate;
	//更新者
	private String updateId;
	//更新时间
	@UpdateTime
	private Date updateDate;


	private String wname;


}
