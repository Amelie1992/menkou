package com.wzlue.sys.service;

import com.wzlue.sys.entity.SysImageEntity;

import java.util.List;
import java.util.Map;

/**
 * 图片表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-09 16:53:50
 */
public interface SysImageService {
	
	SysImageEntity queryObject(Long id);
	
	List<SysImageEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysImageEntity sysImage);
	
	void update(SysImageEntity sysImage);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
