package com.wzlue.store.entity;

import com.wzlue.common.annotation.CreateTime;
import com.wzlue.common.annotation.UpdateTime;
import com.wzlue.wechat.entity.WxAppEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店轮播图
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-05 11:01:10
 */
@Data
public class TStoreRotarypicEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//标题
	private String title;
	//轮播图
	private String picUrl;
	//排序
	private Long sort;

	//图片跳转链接
	private String link;
	//图片大小说明
	private String explain;
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
