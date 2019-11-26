package com.wzlue.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店联系我们
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-09-28 17:14:06
 */
@Data
public class TStoreContactUsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//标题
	private String title;
	//内容
	private String content;
	//电话
	private String phone;
	//邮箱
	private String email;
	//地址
	private String address;
	// 应用ID
	private String appId;
	//
	private String createId;
	//
	private Date createDate;
	//
	private String updateId;
	//
	private Date updateDate;
	//软删除标识：1已删除 2未删除
	private Integer delFlag;
	//微店名称
	private String wname;

}
