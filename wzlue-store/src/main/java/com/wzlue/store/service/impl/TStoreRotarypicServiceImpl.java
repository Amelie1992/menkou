package com.wzlue.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.store.dao.TStoreRotarypicDao;
import com.wzlue.store.entity.TStoreRotarypicEntity;
import com.wzlue.store.service.TStoreRotarypicService;
import org.springframework.transaction.annotation.Transactional;


@Service("tStoreRotarypicService")
public class TStoreRotarypicServiceImpl implements TStoreRotarypicService {

	@Autowired
	private TStoreRotarypicDao tStoreRotarypicDao;
	
	@Override
	public TStoreRotarypicEntity queryObject(Long id){
		return tStoreRotarypicDao.queryObject(id);
	}
	
	@Override
	public List<TStoreRotarypicEntity> queryList(Map<String, Object> map){
		return tStoreRotarypicDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tStoreRotarypicDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(TStoreRotarypicEntity tStoreRotarypic){
		tStoreRotarypicDao.save(tStoreRotarypic);
	}
	
	@Override
	@Transactional
	public void update(TStoreRotarypicEntity tStoreRotarypic){
		tStoreRotarypicDao.update(tStoreRotarypic);
	}
	
	@Override
	@Transactional
	public void delete(Long id){
		tStoreRotarypicDao.delete(id);
	}
	
	@Override
	@Transactional
	public void deleteBatch(Long[] ids){
		tStoreRotarypicDao.deleteBatch(ids);
	}

	@Override
	public List<TStoreRotarypicEntity> queryListByParam(Map<String, Object> map) {
		return tStoreRotarypicDao.queryListByParam(map);
	}

	@Override
	public int queryTotalByParam(Map<String, Object> map) {
		return tStoreRotarypicDao.queryTotalByParam(map);
	}
	
}
