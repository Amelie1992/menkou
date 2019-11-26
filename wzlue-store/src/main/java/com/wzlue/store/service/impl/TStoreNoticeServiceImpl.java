package com.wzlue.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.store.dao.TStoreNoticeDao;
import com.wzlue.store.entity.TStoreNoticeEntity;
import com.wzlue.store.service.TStoreNoticeService;
import org.springframework.transaction.annotation.Transactional;


@Service("tStoreNoticeService")
public class TStoreNoticeServiceImpl implements TStoreNoticeService {
	@Autowired
	private TStoreNoticeDao tStoreNoticeDao;
	
	@Override
	public TStoreNoticeEntity queryObject(Long noticeId){
		return tStoreNoticeDao.queryObject(noticeId);
	}
	
	@Override
	public List<TStoreNoticeEntity> queryList(Map<String, Object> map){
		return tStoreNoticeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tStoreNoticeDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(TStoreNoticeEntity tStoreNotice){
		tStoreNoticeDao.save(tStoreNotice);
	}
	
	@Override
	@Transactional
	public void update(TStoreNoticeEntity tStoreNotice){
		tStoreNoticeDao.update(tStoreNotice);
	}
	
	@Override
	@Transactional
	public void delete(Long noticeId){
		tStoreNoticeDao.delete(noticeId);
	}
	
	@Override
	@Transactional
	public void deleteBatch(Long[] noticeIds){
		tStoreNoticeDao.deleteBatch(noticeIds);
	}


	@Override
	public List<TStoreNoticeEntity> queryListByParam(Map<String, Object> map) {
		return tStoreNoticeDao.queryListByParam(map);
	}

	@Override
	public int queryTotalByParam(Map<String, Object> map) {
		return tStoreNoticeDao.queryTotalByParam(map);
	}
	
}
