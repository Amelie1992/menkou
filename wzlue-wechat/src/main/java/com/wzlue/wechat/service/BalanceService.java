package com.wzlue.wechat.service;

import com.wzlue.common.base.Query;
import com.wzlue.wechat.entity.BalanceRecordEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 用户余额
 * @date 2019-05-21 下午 1:49
 */
public interface BalanceService {

    /**
     * 根据当前用户查询余额
     *
     * @param userId 当前登录用户id
     * @return 用户余额
     */
    BigDecimal getUserBalance(String userId);

    /**
     * 根据当前用户查询余额变动记录
     *
     * @param userId 当前登录用户id
     * @param query  分页参数
     * @return 余额变动记录
     */
    List<BalanceRecordEntity> getBalanceRecordList(Long userId, Query query);

    /**
     * 余额更新
     *
     * @param userId 用户id
     * @param amount 金额
     * @param type   变动类型 1-金额增加，2-金额减少
     * @param createBy 创建人
     * @return 是否更新成功，更新失败会抛出业务异常
     */
    boolean updateBalance(String userId, BigDecimal amount, Integer type, Long createBy);

}
