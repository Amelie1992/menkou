package com.wzlue.wechat.entity;

import lombok.Data;

/**
 * 微信消息
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-19 14:48:10
 */
@Data
public class WxMsgEntityVO extends WxMsgEntity {
private static final long serialVersionUID = 1L;

    /**
   * 数量
   */
    private Integer countMsg;

	/**
	 * repType not in筛选
	 */
	private String notInRepType;

}
