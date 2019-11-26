package com.wzlue.wechat.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义菜单模型
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-19 14:48:10
 */
@Data
public class MenuButtonEntity implements Serializable {

	@JSONField(name="type")
	private String type;
	@JSONField(name="name")
	private String name;
	@JSONField(name="key")
	private String key;
	@JSONField(name="url")
	private String url;
	@JSONField(name="media_id")
	private String mediaId;
	@JSONField(name="appid")
	private String appId;
	@JSONField(name="pagepath")
	private String pagePath;
	@JSONField(name="sub_button")
	private List<MenuButtonEntity> subButtons = new ArrayList();
	/**
	 * content内容
	 */
	private JSONObject content;

	private String repContent;
	/**
	 * 消息类型
	 */
	private String repType;
	/**
	 * 消息名
	 */
	private String repName;
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
}
