package com.wzlue.recruitment.service;

import com.wzlue.recruitment.entity.ApplicationMaterialsEntity;

import java.util.List;
import java.util.Map;

/**
 * 申请材料（上平台招聘的申请材料）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-09 16:51:59
 */
public interface ApplicationMaterialsService {
	
	ApplicationMaterialsEntity queryObject(Long id);
	
	List<ApplicationMaterialsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ApplicationMaterialsEntity applicationMaterials);
	
	void update(ApplicationMaterialsEntity applicationMaterials);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
