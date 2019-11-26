package com.wzlue.wechat.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 自定义菜单表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-19 15:34:31
 */
@Data
@EqualsAndHashCode
public class WxMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单ID（click、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album、pic_weixin、location_select：保存key）
	 */
	private String id;
	/**
	 * 所属租户
	 */
	private Integer tenantId;
	/**
	 * 父菜单ID
	 */
	private String parentId;
	/**
	 * 排序值
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 逻辑删除标记（0：显示；1：隐藏）
	 */
	private String delFlag;
	/**
	 * 应用ID
	 */
	private String appId;
	/**
	 * 菜单类型click、view、miniprogram、scancode_push、scancode_waitmsg、pic_sysphoto、pic_photo_or_album、pic_weixin、location_select、media_id、view_limited等
	 */
	private String type;
	/**
	 * 菜单名
	 */
	private String name;
	/**
	 * View：保存链接到url
	 */
	private String url;
	/**
	 * Img、voice、News：保存mediaID
	 */
	private String repMediaId;
	/**
	 * 回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）
	 */
	private String repType;
	/**
	 * 素材名、视频和音乐的标题
	 */
	private String repName;
	/**
	 * Text:保存文字
	 */
	private String repContent;
	/**
	 * 小程序的appid
	 */
	private String maAppId;
	/**
	 * 小程序的页面路径
	 */
	private String maPagePath;
	/**
	 * 视频和音乐的描述
	 */
	private String repDesc;
	/**
	 * 视频和音乐的描述
	 */
	private String repUrl;
	/**
	 * 高质量链接
	 */
	private String repHqUrl;
	/**
	 * 缩略图的媒体id
	 */
	private String repThumbMediaId;
	/**
	 * 缩略图url
	 */
	private String repThumbUrl;
	/**
	 * 图文消息的内容
	 */
	private JSONObject content;

}
