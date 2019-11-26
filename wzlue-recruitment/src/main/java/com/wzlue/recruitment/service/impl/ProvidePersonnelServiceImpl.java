package com.wzlue.recruitment.service.impl;

import com.wzlue.recruitment.dao.ProvidePersonnelDao;
import com.wzlue.recruitment.entity.ProvidePersonnelEntity;
import com.wzlue.recruitment.service.ProvidePersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("providePersonnelService")
public class ProvidePersonnelServiceImpl implements ProvidePersonnelService {
	@Autowired
	private ProvidePersonnelDao providePersonnelDao;
	
	@Override
	public ProvidePersonnelEntity queryObject(Long id){
		return providePersonnelDao.queryObject(id);
	}
	
	@Override
	public List<ProvidePersonnelEntity> queryList(Map<String, Object> map){
		return providePersonnelDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return providePersonnelDao.queryTotal(map);
	}
	
	@Override
	public void save(ProvidePersonnelEntity providePersonnel){
		providePersonnel.setCreateDate(new Date());
		providePersonnelDao.save(providePersonnel);
	}
	
	@Override
	public void update(ProvidePersonnelEntity providePersonnel){
		providePersonnel.setUpdateDate(new Date());
		providePersonnelDao.update(providePersonnel);
	}
	
	@Override
	public void delete(Long id){
		providePersonnelDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		providePersonnelDao.deleteBatch(ids);
	}

	@Override
	public void saveList(List<ProvidePersonnelEntity> providePersonnelEntities) {
		providePersonnelDao.saveList(providePersonnelEntities);
	}

}
