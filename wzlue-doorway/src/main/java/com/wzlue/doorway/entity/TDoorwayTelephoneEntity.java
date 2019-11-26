package com.wzlue.doorway.entity;

import com.wzlue.common.annotation.CreateTime;
import com.wzlue.common.annotation.UpdateTime;
import com.wzlue.wechat.entity.WxAppEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门口电话表
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 16:08:30
 */
@Data
public class TDoorwayTelephoneEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //
    private String telephone;
    //应用ID
    private String appId;
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

    private String wname;


}
