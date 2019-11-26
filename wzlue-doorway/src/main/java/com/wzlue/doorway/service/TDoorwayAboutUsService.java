package com.wzlue.doorway.service;

import com.wzlue.doorway.entity.TDoorwayAboutUsEntity;
import com.wzlue.doorway.entity.TDoorwayTelephoneEntity;

import java.util.List;
import java.util.Map;

/**
 * 门口公司简介
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-09-28 19:08:21
 */
public interface TDoorwayAboutUsService {
	
	TDoorwayAboutUsEntity queryObject(Long id);
	
	List<TDoorwayAboutUsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TDoorwayAboutUsEntity tDoorwayAboutUs);
	
	void update(TDoorwayAboutUsEntity tDoorwayAboutUs);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

}
