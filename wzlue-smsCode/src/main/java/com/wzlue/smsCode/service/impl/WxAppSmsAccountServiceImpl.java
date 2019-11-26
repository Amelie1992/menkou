package com.wzlue.smsCode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.smsCode.dao.WxAppSmsAccountDao;
import com.wzlue.smsCode.entity.WxAppSmsAccountEntity;
import com.wzlue.smsCode.service.WxAppSmsAccountService;



@Service("wxAppSmsAccountService")
public class WxAppSmsAccountServiceImpl implements WxAppSmsAccountService {
	@Autowired
	private WxAppSmsAccountDao wxAppSmsAccountDao;
	
	@Override
	public WxAppSmsAccountEntity queryObject(String appId){
		return wxAppSmsAccountDao.queryObject(appId);
	}
	
	@Override
	public List<WxAppSmsAccountEntity> queryList(Map<String, Object> map){
		return wxAppSmsAccountDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return wxAppSmsAccountDao.queryTotal(map);
	}
	
	@Override
	public void save(WxAppSmsAccountEntity wxAppSmsAccount){
		wxAppSmsAccountDao.save(wxAppSmsAccount);
	}
	
	@Override
	public void update(WxAppSmsAccountEntity wxAppSmsAccount){
		wxAppSmsAccountDao.update(wxAppSmsAccount);
	}
	
	@Override
	public void delete(String appId){
		wxAppSmsAccountDao.delete(appId);
	}
	
	@Override
	public void deleteBatch(String[] appIds){
		wxAppSmsAccountDao.deleteBatch(appIds);
	}
	
}
