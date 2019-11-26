package com.wzlue.common.utils;

/**
 * 系统参数相关Key
 * @author chenshun
 * @email wzlue.com
 * @date 2017-03-26 10:33
 */
public interface ConfigConstant {
    /**
     * 云存储配置KEY
     */
    String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";
    /**
     * 应用类型 1:小程序
     */
     String WX_APP_TYPE_1 = "1";
    /**
     * 应用类型 2:公众号
     */
    String WX_APP_TYPE_2 = "2";
    /**
     * 是
     */
    String YES = "0";
    /**
     * 否
     */
    String NO = "1";

    /**
     * 成功标记
     */
    Integer SUCCESS = 0;
    /**
     * 失败标记
     */
    Integer FAIL = 1;

    //是否订阅（0：已订阅；1：未订阅；2：网页授权用户）
    /**
     * 1：已订阅
     */
    String SUBSCRIBE_TYPE_YES = "0";
    /**
     * 0：未订阅
     */
    String SUBSCRIBE_TYPE_NO = "1";

    /**
     * 消息自动回复类型（1、关注时回复；2、消息回复；3、关键词回复）
     */
    String WX_AUTO_REPLY_TYPE_1 = "1";
    String WX_AUTO_REPLY_TYPE_2 = "2";
    String WX_AUTO_REPLY_TYPE_3 = "3";

    /**
     * 回复类型文本匹配类型（1、全匹配，2、半匹配）
     */
    String WX_REP_MATE_1 = "1";
    String WX_REP_MATE_2 = "2";

    /**
     * 消息分类（1、用户发给公众号；2、公众号发给用户；）
     */
    String WX_MSG_TYPE_1 = "1";
    String WX_MSG_TYPE_2 = "2";


    String USER_DESTINATION_PREFIX = "/wechat/";
    String WX_MSG = "wx_msg";


    /**
     * 授权登陆错误    100 code为空, 101 d
     */
    Integer AUTH_LOGIN_CODE = 100;
    Integer AUTH_LOGIN_NULL = 101;
    Integer AUTH_LOGIN_APPID = 102;


}
