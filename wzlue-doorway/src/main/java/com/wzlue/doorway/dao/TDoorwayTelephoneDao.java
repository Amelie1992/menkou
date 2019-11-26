package com.wzlue.doorway.dao;

import com.wzlue.doorway.entity.TDoorwayTelephoneEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门口电话表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 16:08:30
 */
@Mapper
public interface TDoorwayTelephoneDao extends BaseDao<TDoorwayTelephoneEntity> {


    List<TDoorwayTelephoneEntity> queryListByParam(Map<String, Object> map);


    int queryTotalByParam(Map<String, Object> map);
	
}
