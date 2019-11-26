package com.wzlue.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.store.dao.TStoreTelephoneDao;
import com.wzlue.store.entity.TStoreTelephoneEntity;
import com.wzlue.store.service.TStoreTelephoneService;
import org.springframework.transaction.annotation.Transactional;


@Service("tStoreTelephoneService")
public class TStoreTelephoneServiceImpl implements TStoreTelephoneService {
	@Autowired
	private TStoreTelephoneDao tStoreTelephoneDao;
	
	@Override
	public TStoreTelephoneEntity queryObject(Long id){
		return tStoreTelephoneDao.queryObject(id);
	}
	
	@Override
	public List<TStoreTelephoneEntity> queryList(Map<String, Object> map){
		return tStoreTelephoneDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tStoreTelephoneDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(TStoreTelephoneEntity tStoreTelephone){
		tStoreTelephoneDao.save(tStoreTelephone);
	}
	
	@Override
	@Transactional
	public void update(TStoreTelephoneEntity tStoreTelephone){
		tStoreTelephoneDao.update(tStoreTelephone);
	}
	
	@Override
	@Transactional
	public void delete(Long id){
		tStoreTelephoneDao.delete(id);
	}
	
	@Override
	@Transactional
	public void deleteBatch(Long[] ids){
		tStoreTelephoneDao.deleteBatch(ids);
	}


	@Override
	public List<TStoreTelephoneEntity> queryListByParam(Map<String, Object> map) {
		return tStoreTelephoneDao.queryListByParam(map);
	}

	@Override
	public int queryTotalByParam(Map<String, Object> map) {
		return tStoreTelephoneDao.queryTotalByParam(map);
	}
}
