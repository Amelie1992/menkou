package com.wzlue.wechat.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BalanceRecordEntity implements Serializable {

    private Long id;
    //用户id
    private String userId;
    //金额变动数量
    private BigDecimal amount;
    //金额变动类型，1-金额增加，2-金额减少
    private Integer type;

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
