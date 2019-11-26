package com.wzlue.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.store.dao.TStoreContactUsDao;
import com.wzlue.store.entity.TStoreContactUsEntity;
import com.wzlue.store.service.TStoreContactUsService;



@Service("tStoreContactUsService")
public class TStoreContactUsServiceImpl implements TStoreContactUsService {
	@Autowired
	private TStoreContactUsDao tStoreContactUsDao;
	
	@Override
	public TStoreContactUsEntity queryObject(Long id){
		return tStoreContactUsDao.queryObject(id);
	}
	
	@Override
	public List<TStoreContactUsEntity> queryList(Map<String, Object> map){
		return tStoreContactUsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tStoreContactUsDao.queryTotal(map);
	}
	
	@Override
	public void save(TStoreContactUsEntity tStoreContactUs){
		tStoreContactUsDao.save(tStoreContactUs);
	}
	
	@Override
	public void update(TStoreContactUsEntity tStoreContactUs){
		tStoreContactUsDao.update(tStoreContactUs);
	}
	
	@Override
	public void delete(Long id){
		tStoreContactUsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tStoreContactUsDao.deleteBatch(ids);
	}

	@Override
	public List<TStoreContactUsEntity> queryListByParam(Map<String, Object> map) {
	    return tStoreContactUsDao.queryListByParam(map);
    };

	@Override
	public int queryTotalByParam(Map<String, Object> map) {
	     return tStoreContactUsDao.queryTotalByParam(map);
    };
	
}
