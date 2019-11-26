package com.wzlue.recruitment.service;

import com.wzlue.recruitment.entity.ProvidePersonnelEntity;

import java.util.List;
import java.util.Map;

/**
 * 供人信息表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 16:13:50
 */
public interface ProvidePersonnelService {
	
	ProvidePersonnelEntity queryObject(Long id);
	
	List<ProvidePersonnelEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ProvidePersonnelEntity providePersonnel);
	
	void update(ProvidePersonnelEntity providePersonnel);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	/**
	 * 批量上传
	 * @param providePersonnelEntities
	 */
	void saveList(List<ProvidePersonnelEntity> providePersonnelEntities);
}
