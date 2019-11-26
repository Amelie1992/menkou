package com.wzlue.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.store.dao.WxAppJobTypeDao;
import com.wzlue.store.entity.WxAppJobTypeEntity;
import com.wzlue.store.service.WxAppJobTypeService;



@Service("wxAppJobTypeService")
public class WxAppJobTypeServiceImpl implements WxAppJobTypeService {
	@Autowired
	private WxAppJobTypeDao wxAppJobTypeDao;
	
	@Override
	public WxAppJobTypeEntity queryObject(String appId){
		return wxAppJobTypeDao.queryObject(appId);
	}
	
	@Override
	public List<WxAppJobTypeEntity> queryList(Map<String, Object> map){
		return wxAppJobTypeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return wxAppJobTypeDao.queryTotal(map);
	}
	
	@Override
	public void save(WxAppJobTypeEntity wxAppJobType){
		wxAppJobTypeDao.save(wxAppJobType);
	}
	
	@Override
	public void update(WxAppJobTypeEntity wxAppJobType){
		wxAppJobTypeDao.update(wxAppJobType);
	}
	
	@Override
	public void delete(String appId){
		wxAppJobTypeDao.delete(appId);
	}
	
	@Override
	public void deleteBatch(String[] appIds){
		wxAppJobTypeDao.deleteBatch(appIds);
	}
	
}
