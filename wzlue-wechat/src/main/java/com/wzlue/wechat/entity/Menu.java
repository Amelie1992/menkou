package com.wzlue.wechat.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import me.chanjar.weixin.common.bean.menu.WxMenuRule;

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
public class Menu implements Serializable {
  private static final long serialVersionUID = -7083914585539687746L;

  private List<MenuButtonEntity> button = new ArrayList<>();

  private WxMenuRule matchRule;

  /**
   * 反序列化
   */
  public static Menu fromJson(String json) {
	  return JSON.parseObject(json,Menu.class);
  }

  public String toJson() {
    return JSON.toJSONString(this);
  }

  @Override
  public String toString() {
    return this.toJson();
  }

}
