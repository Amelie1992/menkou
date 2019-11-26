package com.wzlue.wechat.entity;

import com.wzlue.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 微信用户
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-19 14:48:10
 */
@Data
@EqualsAndHashCode
public class WxUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Long rownum;
    /**
     * 用户对应的门店名称
     */
    @Excel(name = "门店")
    private String wname;
    /**
     * 主键
     */
    private String id;
    /**
     * 昵称
     */
    @Excel(name = "微信昵称")
    private String nickName;
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
     * 备注信息
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
     * 应用类型(1:小程序，2:公众号)
     */
    private String appType;
    /**
     * 是否订阅（0：是；1：否；2：网页授权用户）
     */
    @Excel(name = "是否订阅", readConverterExp = "0=是,1=否")
    private String subscribe;
    /**
     * 返回用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENEPROFILE LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_OTHERS 其他
     */
    private String subscribeScene;
    /**
     * 关注时间
     */
//    @Excel(name = "关注时间", dateFormat = "yyyy-MM-dd")
    @Excel(name = "关注时间")
    private LocalDateTime subscribeTime;
    /**
     * 关注次数
     */
    private Integer subscribeNum;
    /**
     * 取消关注时间
     */
    private LocalDateTime cancelSubscribeTime;
    /**
     * 用户标识
     */
    private String openId;

    /**
     * 性别（1：男，2：女，0：未知）
     */
    @Excel(name = "性别", readConverterExp = "0=未知,1=男,2=女")
    private String sex;
    /**
     * 所在国家
     */
    @Excel(name = "所在国家")
    private String country;
    /**
     * 所在省份
     */
    @Excel(name = "所在省份")
    private String province;
    /**
     * 所在城市
     */
    @Excel(name = "所在城市")
    private String city;
    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phone;

    /**
     * 用户语言
     */
    private String language;
    /**
     * 头像
     */
    private String headimgUrl;
    /**
     * union_id
     */
    private String unionId;
    /**
     * 用户组
     */
    private String groupId;
    /**
     * 标签列表
     */
    private Long[] tagidList;
    /**
     * 二维码扫码场景
     */
    private String qrSceneStr;
    /**
     * 地理位置纬度
     */
    private Double latitude;
    /**
     * 地理位置经度
     */
    private Double longitude;
    /**
     * 地理位置精度
     */
    private Double precision;
    /**
     * 会话密钥
     */
    private String sessionKey;
    /**
     * 微信号
     */
    private String weChat;
    /**
     * 新手任务标识 0：未完成，1：完成
     */
    @Excel(name = "新手任务", readConverterExp = "0=未完成,1=完成")
    private Integer newTask;
    /**
     * 积分
     */
    @Excel(name = "积分")
    private Integer integral;
    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 用户对应的门店是否有效 0：无效，1：有效
     */
    private String appstate;

    /**
     * 新手任务提示次数
     */
    private Integer tipNum;

    //推荐奖励金
    private BigDecimal bonus;

    //验证码
    private String smsCode;

    //带参二维码的场景值
    private Integer sceneId;
}
