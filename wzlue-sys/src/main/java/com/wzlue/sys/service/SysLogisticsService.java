package com.wzlue.sys.service;

import com.wzlue.sys.entity.SysLogisticsEntity;

import java.util.List;
import java.util.Map;

/**
 * 物流表
 * 
 * @author YJ
 * @email wzlue.com
 * @date 2018-06-29 10:01:23
 */
public interface SysLogisticsService {
	
	SysLogisticsEntity queryObject(Long id);
	
	List<SysLogisticsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysLogisticsEntity sysLogistics);
	
	void update(SysLogisticsEntity sysLogistics);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
