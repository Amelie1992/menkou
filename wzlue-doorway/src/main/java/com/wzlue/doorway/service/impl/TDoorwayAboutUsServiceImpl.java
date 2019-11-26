package com.wzlue.doorway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.doorway.dao.TDoorwayAboutUsDao;
import com.wzlue.doorway.entity.TDoorwayAboutUsEntity;
import com.wzlue.doorway.service.TDoorwayAboutUsService;



@Service("tDoorwayAboutUsService")
public class TDoorwayAboutUsServiceImpl implements TDoorwayAboutUsService {
	@Autowired
	private TDoorwayAboutUsDao tDoorwayAboutUsDao;
	
	@Override
	public TDoorwayAboutUsEntity queryObject(Long id){
		return tDoorwayAboutUsDao.queryObject(id);
	}
	
	@Override
	public List<TDoorwayAboutUsEntity> queryList(Map<String, Object> map){
		return tDoorwayAboutUsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tDoorwayAboutUsDao.queryTotal(map);
	}
	
	@Override
	public void save(TDoorwayAboutUsEntity tDoorwayAboutUs){
		tDoorwayAboutUsDao.save(tDoorwayAboutUs);
	}
	
	@Override
	public void update(TDoorwayAboutUsEntity tDoorwayAboutUs){
		tDoorwayAboutUsDao.update(tDoorwayAboutUs);
	}
	
	@Override
	public void delete(Long id){
		tDoorwayAboutUsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tDoorwayAboutUsDao.deleteBatch(ids);
	}
	
}
