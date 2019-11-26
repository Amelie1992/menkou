package com.wzlue.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wzlue.store.dao.TStoreGloryDao;
import com.wzlue.store.entity.TStoreGloryEntity;
import com.wzlue.store.service.TStoreGloryService;
import org.springframework.transaction.annotation.Transactional;


@Service("tStoreGloryService")
public class TStoreGloryServiceImpl implements TStoreGloryService {
	@Autowired
	private TStoreGloryDao tStoreGloryDao;
	
	@Override
	public TStoreGloryEntity queryObject(Long gloryId){
		return tStoreGloryDao.queryObject(gloryId);
	}
	
	@Override
	public List<TStoreGloryEntity> queryList(Map<String, Object> map){
		return tStoreGloryDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tStoreGloryDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(TStoreGloryEntity tStoreGlory){
		tStoreGloryDao.save(tStoreGlory);
	}
	
	@Override
	@Transactional
	public void update(TStoreGloryEntity tStoreGlory){
		tStoreGloryDao.update(tStoreGlory);
	}
	
	@Override
	@Transactional
	public void delete(Long gloryId){
		tStoreGloryDao.delete(gloryId);
	}
	
	@Override
	@Transactional
	public void deleteBatch(Long[] gloryIds){
		tStoreGloryDao.deleteBatch(gloryIds);
	}


	@Override
	public List<TStoreGloryEntity> queryListByParam(Map<String, Object> map) {
		return tStoreGloryDao.queryListByParam(map);
	}

	@Override
	public List<TStoreGloryEntity> queryListByIds(Object[] ids) {
		return tStoreGloryDao.queryListByIds(ids);
	}

	@Override
	public List<TStoreGloryEntity> queryListAll(Map<String, Object> map) {
		return tStoreGloryDao.queryListAll(map);
	}

	@Override
	public int queryTotalByParam(Map<String, Object> map) {
		return tStoreGloryDao.queryTotalByParam(map);
	}
}
