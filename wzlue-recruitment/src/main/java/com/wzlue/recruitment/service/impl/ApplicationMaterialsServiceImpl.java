package com.wzlue.recruitment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.recruitment.dao.ApplicationMaterialsDao;
import com.wzlue.recruitment.entity.ApplicationMaterialsEntity;
import com.wzlue.recruitment.service.ApplicationMaterialsService;



@Service("applicationMaterialsService")
public class ApplicationMaterialsServiceImpl implements ApplicationMaterialsService {
	@Autowired
	private ApplicationMaterialsDao applicationMaterialsDao;
	
	@Override
	public ApplicationMaterialsEntity queryObject(Long id){
		return applicationMaterialsDao.queryObject(id);
	}
	
	@Override
	public List<ApplicationMaterialsEntity> queryList(Map<String, Object> map){
		return applicationMaterialsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return applicationMaterialsDao.queryTotal(map);
	}
	
	@Override
	public void save(ApplicationMaterialsEntity applicationMaterials){
		applicationMaterialsDao.save(applicationMaterials);
	}
	
	@Override
	public void update(ApplicationMaterialsEntity applicationMaterials){
		applicationMaterialsDao.update(applicationMaterials);
	}
	
	@Override
	public void delete(Long id){
		applicationMaterialsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		applicationMaterialsDao.deleteBatch(ids);
	}
	
}
