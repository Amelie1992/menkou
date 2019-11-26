package com.wzlue.smsCode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.smsCode.dao.SmsCodeDao;
import com.wzlue.smsCode.entity.SmsCodeEntity;
import com.wzlue.smsCode.service.SmsCodeService;



@Service("smsCodeService")
public class SmsCodeServiceImpl implements SmsCodeService {
	@Autowired
	private SmsCodeDao smsCodeDao;
	
	@Override
	public SmsCodeEntity queryObject(Long id){
		return smsCodeDao.queryObject(id);
	}
	
	@Override
	public List<SmsCodeEntity> queryList(Map<String, Object> map){
		return smsCodeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return smsCodeDao.queryTotal(map);
	}
	
	@Override
	public void save(SmsCodeEntity smsCode){
		smsCodeDao.save(smsCode);
	}
	
	@Override
	public void update(SmsCodeEntity smsCode){
		smsCodeDao.update(smsCode);
	}
	
	@Override
	public void delete(Long id){
		smsCodeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		smsCodeDao.deleteBatch(ids);
	}

	@Override
	public void invalid(SmsCodeEntity smsCode) {
        smsCodeDao.invalid(smsCode);
    }

	@Override
	public void deleteBeforeToday(SmsCodeEntity smsCode) {
		smsCodeDao.deleteBeforeToday(smsCode);
	}
}
