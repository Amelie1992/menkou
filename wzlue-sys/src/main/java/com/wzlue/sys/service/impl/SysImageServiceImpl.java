package com.wzlue.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.sys.dao.SysImageDao;
import com.wzlue.sys.entity.SysImageEntity;
import com.wzlue.sys.service.SysImageService;



@Service("sysImageService")
public class SysImageServiceImpl implements SysImageService {
	@Autowired
	private SysImageDao sysImageDao;
	
	@Override
	public SysImageEntity queryObject(Long id){
		return sysImageDao.queryObject(id);
	}
	
	@Override
	public List<SysImageEntity> queryList(Map<String, Object> map){
		return sysImageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysImageDao.queryTotal(map);
	}
	
	@Override
	public void save(SysImageEntity sysImage){
		sysImageDao.save(sysImage);
	}
	
	@Override
	public void update(SysImageEntity sysImage){
		sysImageDao.update(sysImage);
	}
	
	@Override
	public void delete(Long id){
		sysImageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysImageDao.deleteBatch(ids);
	}
	
}
