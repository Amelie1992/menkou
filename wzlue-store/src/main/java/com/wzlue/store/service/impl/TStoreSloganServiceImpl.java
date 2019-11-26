package com.wzlue.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.store.dao.TStoreSloganDao;
import com.wzlue.store.entity.TStoreSloganEntity;
import com.wzlue.store.service.TStoreSloganService;
import org.springframework.transaction.annotation.Transactional;


@Service("tStoreSloganService")
public class TStoreSloganServiceImpl implements TStoreSloganService {
	@Autowired
	private TStoreSloganDao tStoreSloganDao;
	
	@Override
	public TStoreSloganEntity queryObject(Long id){
		return tStoreSloganDao.queryObject(id);
	}
	
	@Override
	public List<TStoreSloganEntity> queryList(Map<String, Object> map){
		return tStoreSloganDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tStoreSloganDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(TStoreSloganEntity tStoreSlogan){
		tStoreSloganDao.save(tStoreSlogan);
	}
	
	@Override
	@Transactional
	public void update(TStoreSloganEntity tStoreSlogan){
		tStoreSloganDao.update(tStoreSlogan);
	}
	
	@Override
	@Transactional
	public void delete(Long id){
		tStoreSloganDao.delete(id);
	}
	
	@Override
	@Transactional
	public void deleteBatch(Long[] ids){
		tStoreSloganDao.deleteBatch(ids);
	}




	@Override
	public List<TStoreSloganEntity> queryListByParam(Map<String, Object> map) {
		return tStoreSloganDao.queryListByParam(map);
	}

	@Override
	public int queryTotalByParam(Map<String, Object> map) {
		return tStoreSloganDao.queryTotalByParam(map);
	}
	
}
