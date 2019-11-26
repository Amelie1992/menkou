package com.wzlue.draw.dao;

import com.wzlue.draw.entity.IntegralDrawPrizeEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分抽奖奖项
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
@Mapper
public interface IntegralDrawPrizeDao extends BaseDao<IntegralDrawPrizeEntity> {
    void deleteByAppId(String appId);
}
