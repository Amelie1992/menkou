package com.wzlue.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.store.dao.TStoreConfigDao;
import com.wzlue.store.entity.TStoreConfigEntity;
import com.wzlue.store.service.TStoreConfigService;
import org.springframework.transaction.annotation.Transactional;


@Service("tStoreConfigService")
public class TStoreConfigServiceImpl implements TStoreConfigService {
	@Autowired
	private TStoreConfigDao tStoreConfigDao;
	
	@Override
	public TStoreConfigEntity queryObject(Long configId){
		return tStoreConfigDao.queryObject(configId);
	}
	
	@Override
	public List<TStoreConfigEntity> queryList(Map<String, Object> map){
		return tStoreConfigDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tStoreConfigDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(TStoreConfigEntity tStoreConfig){
		tStoreConfigDao.save(tStoreConfig);
	}
	
	@Override
	@Transactional
	public void update(TStoreConfigEntity tStoreConfig){
		tStoreConfigDao.update(tStoreConfig);
	}
	
	@Override
	@Transactional
	public void delete(Long configId){
		tStoreConfigDao.delete(configId);
	}
	
	@Override
	@Transactional
	public void deleteBatch(Long[] configIds){
		tStoreConfigDao.deleteBatch(configIds);
	}



	@Override
	public List<TStoreConfigEntity> queryListByParam(Map<String, Object> map) {
		return tStoreConfigDao.queryListByParam(map);
	}

	@Override
	public int queryTotalByParam(Map<String, Object> map) {
		return tStoreConfigDao.queryTotalByParam(map);
	}
	
}
