package com.wzlue.qrcode.service;

import com.wzlue.qrcode.entity.WxAppQrCodeEntity;

import java.util.List;
import java.util.Map;

/**
 * 带参二维码
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-18 11:47:43
 */
public interface WxAppQrCodeService {
	
	WxAppQrCodeEntity queryObject(Long id);
	
	List<WxAppQrCodeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(WxAppQrCodeEntity wxAppQrCode);
	
	void update(WxAppQrCodeEntity wxAppQrCode);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	List<WxAppQrCodeEntity> queryAppList(Map<String, Object> map);

	int queryAppTotal(Map<String, Object> map);
}
