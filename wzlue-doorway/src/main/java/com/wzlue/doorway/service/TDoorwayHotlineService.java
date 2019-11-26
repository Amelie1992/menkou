package com.wzlue.doorway.service;

import com.wzlue.doorway.entity.TDoorwayHotlineEntity;

import java.util.List;
import java.util.Map;

/**
 * 平台热线
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-08 15:00:13
 */
public interface TDoorwayHotlineService {
	
	TDoorwayHotlineEntity queryObject(Long id);
	
	List<TDoorwayHotlineEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TDoorwayHotlineEntity tDoorwayHotline);
	
	void update(TDoorwayHotlineEntity tDoorwayHotline);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
