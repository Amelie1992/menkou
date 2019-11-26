package com.wzlue.draw.dao;

import com.wzlue.draw.entity.IntegralDrawRecordEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 积分抽奖记录（我的奖品）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
@Mapper
public interface IntegralDrawRecordDao extends BaseDao<IntegralDrawRecordEntity> {

    void exchangeBatch(Long[] ids);

}
