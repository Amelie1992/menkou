package com.wzlue.wechat.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.common.base.Query;
import com.wzlue.wechat.entity.BalanceRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 微信用户余额记录表
 * @date 2019-05-21 下午 2:11
 */
@Mapper
public interface BalanceRecordDao extends BaseDao<BalanceRecordEntity> {


    List<BalanceRecordEntity> queryByUserId(@Param("userId") Long userId, @Param("query") Query query);

}
