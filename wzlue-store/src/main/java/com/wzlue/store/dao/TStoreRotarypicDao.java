package com.wzlue.store.dao;

import com.wzlue.store.entity.TStoreRotarypicEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门店轮播图
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-05 11:01:10
 */
@Mapper
public interface TStoreRotarypicDao extends BaseDao<TStoreRotarypicEntity> {

    List<TStoreRotarypicEntity> queryListByParam(Map<String, Object> map);

    int queryTotalByParam(Map<String, Object> map);
}
