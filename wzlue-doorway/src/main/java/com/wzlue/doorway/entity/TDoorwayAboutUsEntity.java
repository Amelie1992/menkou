package com.wzlue.doorway.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门口公司简介
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-09-28 19:08:21
 */
@Data
public class TDoorwayAboutUsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//简介
	private String profile;
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

    private String wname;

}
