package com.wzlue.store.dao;

import com.wzlue.store.entity.TStoreTelephoneEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门店电话表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 16:10:28
 */
@Mapper
public interface TStoreTelephoneDao extends BaseDao<TStoreTelephoneEntity> {


    List<TStoreTelephoneEntity> queryListByParam(Map<String, Object> map);


    int queryTotalByParam(Map<String, Object> map);
	
}
