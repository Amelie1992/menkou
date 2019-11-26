package com.wzlue.doorway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.doorway.dao.TDoorwayTelephoneDao;
import com.wzlue.doorway.entity.TDoorwayTelephoneEntity;
import com.wzlue.doorway.service.TDoorwayTelephoneService;
import org.springframework.transaction.annotation.Transactional;


@Service("tDoorwayTelephoneService")
public class TDoorwayTelephoneServiceImpl implements TDoorwayTelephoneService {
	@Autowired
	private TDoorwayTelephoneDao tDoorwayTelephoneDao;
	
	@Override
	public TDoorwayTelephoneEntity queryObject(Long id){
		return tDoorwayTelephoneDao.queryObject(id);
	}
	
	@Override
	public List<TDoorwayTelephoneEntity> queryList(Map<String, Object> map){
		return tDoorwayTelephoneDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tDoorwayTelephoneDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(TDoorwayTelephoneEntity tDoorwayTelephone){
		tDoorwayTelephoneDao.save(tDoorwayTelephone);
	}
	
	@Override
	@Transactional
	public void update(TDoorwayTelephoneEntity tDoorwayTelephone){
		tDoorwayTelephoneDao.update(tDoorwayTelephone);
	}
	
	@Override
	@Transactional
	public void delete(Long id){
		tDoorwayTelephoneDao.delete(id);
	}
	
	@Override
	@Transactional
	public void deleteBatch(Long[] ids){
		tDoorwayTelephoneDao.deleteBatch(ids);
	}


	@Override
	public List<TDoorwayTelephoneEntity> queryListByParam(Map<String, Object> map) {
		return tDoorwayTelephoneDao.queryListByParam(map);
	}

	@Override
	public int queryTotalByParam(Map<String, Object> map) {
		return tDoorwayTelephoneDao.queryTotalByParam(map);
	}
}
