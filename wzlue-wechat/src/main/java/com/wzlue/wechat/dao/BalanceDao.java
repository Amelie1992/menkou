package com.wzlue.wechat.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.wechat.entity.BalanceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 微信用户余额表
 * @date 2019-05-21 下午 2:10
 */
@Mapper
public interface BalanceDao extends BaseDao<BalanceEntity> {


    BigDecimal queryBalanceByUserId(String userId);

    BalanceEntity queryByUserId(String userId);

    int updateBalance(@Param("userId") String userId, @Param("newBalance") BigDecimal newBalance, @Param("version") Long version, @Param("updateDate") Date updateDate);

}
