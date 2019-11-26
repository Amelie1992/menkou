package com.wzlue.store.dao;

import com.wzlue.store.entity.TStoreContactUsEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门店联系我们
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-09-28 17:14:06
 */
@Mapper
public interface TStoreContactUsDao extends BaseDao<TStoreContactUsEntity> {
    List<TStoreContactUsEntity> queryListByParam(Map<String, Object> map);

    int queryTotalByParam(Map<String, Object> map);

}
