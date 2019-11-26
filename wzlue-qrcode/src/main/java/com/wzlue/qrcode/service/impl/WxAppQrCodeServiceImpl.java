package com.wzlue.qrcode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.qrcode.dao.WxAppQrCodeDao;
import com.wzlue.qrcode.entity.WxAppQrCodeEntity;
import com.wzlue.qrcode.service.WxAppQrCodeService;



@Service("wxAppQrCodeService")
public class WxAppQrCodeServiceImpl implements WxAppQrCodeService {
	@Autowired
	private WxAppQrCodeDao wxAppQrCodeDao;
	
	@Override
	public WxAppQrCodeEntity queryObject(Long id){
		return wxAppQrCodeDao.queryObject(id);
	}
	
	@Override
	public List<WxAppQrCodeEntity> queryList(Map<String, Object> map){
		return wxAppQrCodeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return wxAppQrCodeDao.queryTotal(map);
	}
	
	@Override
	public void save(WxAppQrCodeEntity wxAppQrCode){
		wxAppQrCodeDao.save(wxAppQrCode);
	}
	
	@Override
	public void update(WxAppQrCodeEntity wxAppQrCode){
		wxAppQrCodeDao.update(wxAppQrCode);
	}
	
	@Override
	public void delete(Long id){
		wxAppQrCodeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		wxAppQrCodeDao.deleteBatch(ids);
	}

	@Override
	public List<WxAppQrCodeEntity> queryAppList(Map<String, Object> map){
		return wxAppQrCodeDao.queryAppList(map);
	}

	@Override
	public int queryAppTotal(Map<String, Object> map){
		return wxAppQrCodeDao.queryAppTotal(map);
	}
	
}
