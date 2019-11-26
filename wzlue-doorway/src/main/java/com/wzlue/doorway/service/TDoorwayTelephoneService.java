package com.wzlue.doorway.service;

import com.wzlue.doorway.entity.TDoorwayTelephoneEntity;

import java.util.List;
import java.util.Map;

/**
 * 门口电话表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 16:08:30
 */
public interface TDoorwayTelephoneService {
	
	TDoorwayTelephoneEntity queryObject(Long id);
	
	List<TDoorwayTelephoneEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TDoorwayTelephoneEntity tDoorwayTelephone);
	
	void update(TDoorwayTelephoneEntity tDoorwayTelephone);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);


	//根据参数查询列表
	List<TDoorwayTelephoneEntity> queryListByParam(Map<String, Object> map);
	//根据参数查询列表个数
	int queryTotalByParam(Map<String, Object> map);
}
