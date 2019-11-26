package com.wzlue.store.entity;

import com.wzlue.common.annotation.CreateTime;
import com.wzlue.common.annotation.UpdateTime;
import com.wzlue.wechat.entity.WxAppEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店标语表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 16:44:29
 */
@Data
public class TStoreSloganEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//标语编号
	private Long id;

	//主标语
	private String mainTitle;
	//副标语
	private String subTitle;


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
