package com.wzlue.wechat.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BalanceEntity implements Serializable {

    private Long id;
    //用户id
    private String userId;
    //用户余额
    private BigDecimal balance;
    //版本号
    private Long version;

    //创建人
    private Long createBy;
    //创建日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
    //修改人
    private Long updateBy;
    //修改日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
}
