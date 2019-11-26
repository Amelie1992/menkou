package com.wzlue.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.store.dao.WxAppThemeColorDao;
import com.wzlue.store.entity.WxAppThemeColorEntity;
import com.wzlue.store.service.WxAppThemeColorService;



@Service("wxAppThemeColorService")
public class WxAppThemeColorServiceImpl implements WxAppThemeColorService {
	@Autowired
	private WxAppThemeColorDao wxAppThemeColorDao;
	
	@Override
	public WxAppThemeColorEntity queryObject(String appId){
		return wxAppThemeColorDao.queryObject(appId);
	}
	
	@Override
	public List<WxAppThemeColorEntity> queryList(Map<String, Object> map){
		return wxAppThemeColorDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return wxAppThemeColorDao.queryTotal(map);
	}
	
	@Override
	public void save(WxAppThemeColorEntity wxAppThemeColor){
		wxAppThemeColorDao.save(wxAppThemeColor);
	}
	
	@Override
	public void update(WxAppThemeColorEntity wxAppThemeColor){
		wxAppThemeColorDao.update(wxAppThemeColor);
	}
	
	@Override
	public void delete(String appId){
		wxAppThemeColorDao.delete(appId);
	}
	
	@Override
	public void deleteBatch(String[] appIds){
		wxAppThemeColorDao.deleteBatch(appIds);
	}
	
}
