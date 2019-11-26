package com.wzlue.store.dao;

import com.wzlue.store.entity.TStoreIntegralRecordEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 门店积分记录
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
@Mapper
public interface TStoreIntegralRecordDao extends BaseDao<TStoreIntegralRecordEntity> {

    List<TStoreIntegralRecordEntity> queryListByYearAndMonth(Map<String, Object> map);

    int queryTotalByYearAndMonth(Map<String, Object> map);

    /**
     * 查询是否完成新手任务
     *
     * @param entity
     * @return
     */
    int getByUserIdType(TStoreIntegralRecordEntity entity);

}
