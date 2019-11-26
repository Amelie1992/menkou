package com.wzlue.qrcode.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 带参二维码
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-18 11:47:43
 */
@Data
public class WxAppQrCodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	//通过ticket获取二维码路径
    public static final String SHOW_QRCODE_PATH = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";


	//
	private Long id;
	//appId
	private String appId;
	//参数（场景值ID 10万以内）
	private Integer sceneId;
	//带参二维码
	private String qrCode;
	//二维码ticket
	private String ticket;
	//url
    private String url;
	//
	private Long createBy;
	//
	private Date createTime;
	//
	private Long updateBy;
	//
	private Date updateTime;
	//业务员姓名
	private String name;
	//业务员手机号
	private String phone;
	//备注
	private String remarks;

	private String appname;
	//二维码个数
    private Long count;
    //1未删除;2已删除
    private Integer delFlag;


}
