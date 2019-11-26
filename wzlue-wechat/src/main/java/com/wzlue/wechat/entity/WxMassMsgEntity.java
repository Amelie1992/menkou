package com.wzlue.wechat.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 微信消息群发
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-19 15:34:31
 */
@Data
public class WxMassMsgEntity implements Serializable {
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
     * 应用ID
     */
    private String appId;
    /**
     * 是否全部用户发送(0：是；1：否)
     */
    private String isToAll;
    /**
     * 发送类型（1、分组发；2、选择用户发）
     */
    private String sendType;
    /**
     * 标签的tag_id
     */
    private Long tagId;
    /**
     * 回复消息类型（text：文本；image：图片；voice：语音；video：视频；music：音乐；news：图文）
     */
    private String repType;
    /**
     * Text:保存文字
     */
    private String repContent;
    /**
     * imge、voice、news、video：mediaID
     */
    private String repMediaId;
    /**
     * 素材名、视频和音乐的标题
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
    /**
     * 文章被判定为转载时，是否继续进行群发操作
     */
    private Boolean sendIgnoreReprint;
    /**
     * 群发消息后返回的消息id
     */
    private String msgId;
    /**
     * 消息的数据ID，该字段只有在群发图文消息时才会出现
     */
    private String msgDataId;
    /**
     * 消息发送后的状态(SUB_SUCCESS：提交成功，SUB_FAIL：提交失败，SEND_SUCCESS：发送成功，SENDING：发送中，SEND_FAIL：发送失败，DELETE：已删除)
     */
    private String msgStatus;
    /**
     * 发送的总粉丝数
     */
    private Integer totalCount;
    /**
     * 发送成功的粉丝数
     */
    private Integer sentCount;
    /**
     * 过滤（过滤是指特定地区、性别的过滤、用户设置拒收的过滤，用户接收已超4条的过滤）后，准备发送的粉丝数.
     * 原则上，filterCount = sentCount + errorCount
     */
    private Integer filterCount;
    /**
     * 发送失败的粉丝数
     */
    private Integer errorCount;
    /**
     * 错误码
     */
    private String errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;

    private List<String> openIds;
}
