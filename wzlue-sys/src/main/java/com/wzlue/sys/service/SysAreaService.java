package com.wzlue.sys.service;

import com.wzlue.sys.entity.SysAreaEntity;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author YJ
 * @email wzlue.com
 * @date 2018-06-11 14:13:34
 */
public interface SysAreaService {
	
	SysAreaEntity queryObject(Integer id);
	
	List<SysAreaEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysAreaEntity sysArea);
	
	void update(SysAreaEntity sysArea);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	List<SysAreaEntity> areaList(Map<String,Object> params);


	List<SysAreaEntity> queryProvincesList(Map<String, Object> map);

	int queryProvincesListTotal(Map<String, Object> map);

	/**
	 * 根据编码和上级编码查名字
	 * @param
	 * @return
	 */
	SysAreaEntity queryNameByCode(SysAreaEntity sysAreaEntity);

}
