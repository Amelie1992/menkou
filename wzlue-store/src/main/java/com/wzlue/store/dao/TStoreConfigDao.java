package com.wzlue.store.dao;

import com.wzlue.store.entity.TStoreConfigEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门店配置表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 09:44:21
 */
@Mapper
public interface TStoreConfigDao extends BaseDao<TStoreConfigEntity> {

    /**
     * configType:1,标签; 2,待遇;3,规模;4,企业年限;5,返费金额;
     * @param map
     * @return
     */
    List<TStoreConfigEntity> queryListByParam(Map<String, Object> map);

    int queryTotalByParam(Map<String, Object> map);
}
