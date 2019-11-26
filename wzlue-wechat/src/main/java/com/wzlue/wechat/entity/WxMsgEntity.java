package com.wzlue.wechat.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 微信消息
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-19 14:48:10
 */
@Data
@EqualsAndHashCode
public class WxMsgEntity {
private static final long serialVersionUID = 1L;

    /**
   * 主键
   */
    private String id;
    /**
   * 创建者
   */
    private String createId;
    /**
   * 创建时间
   */
    private LocalDateTime createDate;
    /**
   * 更新者
   */
    private String updateId;
    /**
   * 更新时间
   */
    private LocalDateTime updateDate;
    /**
   * 备注
   */
    private String remark;
    /**
   * 逻辑删除标记（0：显示；1：隐藏）
   */
    private String delFlag;
    /**
   * 所属租户
   */
    private Integer tenantId;
    /**
   * 公众号配置ID、小程序AppID
   */
    private String appId;
	/**
	 * 公众号名称
	 */
	private String appName;
	/**
	 * 公众号logo
	 */
	private String appLogo;
    /**
   * 微信用户ID
   */
    private String wxUserId;
	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 头像
	 */
	private String headimgUrl;
    /**
   * 消息分类（1、用户发给公众号；2、公众号发给用户；）
   */
    private String type;
	/**
	 * 消息类型（text：文本；image：图片；voice：语音；video：视频；shortvideo：小视频；location：地理位置；music：音乐；news：图文；event：推送事件）
	 */
	private String repType;
	/**
	 * 事件类型（subscribe：关注；unsubscribe：取关；CLICK、VIEW：菜单事件）
	 */
	private String repEvent;
    /**
   * 回复类型文本保存文字
   */
    private String repContent;
    /**
   * 回复类型imge、voice、news、video的mediaID或音乐缩略图的媒体id
   */
    private String repMediaId;
    /**
   * 回复的素材名、视频和音乐的标题
   */
    private String repName;
    /**
   * 视频和音乐的描述
   */
    private String repDesc;
    /**
   * 链接
   */
    private String repUrl;
    /**
   * 高质量链接
   */
    private String repHqUrl;
    /**
   * 图文消息的内容
   */
    private JSONObject content;
	/**
	 * 缩略图的媒体id
	 */
	private String repThumbMediaId;
	/**
	 * 缩略图url
	 */
	private String repThumbUrl;
	/**
	 * 地理位置维度
	 */
	private Double repLocationX;
	/**
	 * 地理位置经度
	 */
	private Double repLocationY;
	/**
	 * 地图缩放大小
	 */
	private Double repScale;
    /**
   * 已读标记（0：是；1：否）
   */
    private String readFlag;
  
}
