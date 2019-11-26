package com.wzlue.store.dao;

import com.wzlue.store.entity.TStoreSloganEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门店标语表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 16:44:29
 */
@Mapper
public interface TStoreSloganDao extends BaseDao<TStoreSloganEntity> {

    List<TStoreSloganEntity> queryListByParam(Map<String, Object> map);

    int queryTotalByParam(Map<String, Object> map);
	
}
