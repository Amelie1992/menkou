package com.wzlue.wechat.entity;

import com.wzlue.wechat.entity.SysImageEntity2;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wzlue.common.annotation.CreateTime;
import com.wzlue.common.annotation.UpdateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.awt.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;



/**
 * 微信应用
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-19 14:48:10
 */
@Data
@EqualsAndHashCode
public class WxAppEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //应用ID
    private String id;
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
    //逻辑删除标记（0：显示；1：隐藏）
    private String delFlag;
    //所属租户
    private Integer tenantId;
    //微信原始标识
    private String weixinSign;
    //应用类型(1:小程序，2:公众号)
    private String appType;
    //应用密钥
    private String secret;
    //token
    private String token;
    //EncodingAESKey
    private String aesKey;
    //微信号名称
    private String name;
    //是否第三方平台应用（0：是；1：否）
    private String isComponent;
    //0：订阅号；1：由历史老帐号升级后的订阅号；2：服务号
    private String weixinType;
    //公众号微信号
    private String weixinHao;
    //认证类型
    private String verifyType;
    //logo
    private String logo;
    //二维码
    private String qrCode;
    //主体名称
    private String principalName;
    //微社区URL
    private String community;
    //备注信息
    private String remarks;
    //绑定的会员卡ID
    private String vipCardId;
    //状态
    private String state;
    //有限时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date expDate;
    //有效时间类型  0 有限 1不限
    private String expType;
    //门店资质，默认关闭 0 关闭，1开启
    private String qualified;
    //头像
    private String headLogo;
    //校验文件
    private List<SysImageEntity2> checkFile;
    // 0使用1停用
    private String enabled;
    // 资质内容
    private String description;
}
