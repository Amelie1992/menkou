package com.wzlue.store.dao;

import com.wzlue.store.entity.TStoreGloryEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门店光荣榜表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 09:44:21
 */
@Mapper
public interface TStoreGloryDao extends BaseDao<TStoreGloryEntity> {

    List<TStoreGloryEntity> queryListByParam(Map<String, Object> map);

    List<TStoreGloryEntity> queryListByIds(Object[] ids);
    List<TStoreGloryEntity> queryListAll(Map<String, Object> map);



    int queryTotalByParam(Map<String, Object> map);
	
}
