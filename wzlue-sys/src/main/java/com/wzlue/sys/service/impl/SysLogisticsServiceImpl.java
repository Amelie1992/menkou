package com.wzlue.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.sys.dao.SysLogisticsDao;
import com.wzlue.sys.entity.SysLogisticsEntity;
import com.wzlue.sys.service.SysLogisticsService;



@Service("sysLogisticsService")
public class SysLogisticsServiceImpl implements SysLogisticsService {
	@Autowired
	private SysLogisticsDao sysLogisticsDao;
	
	@Override
	public SysLogisticsEntity queryObject(Long id){
		return sysLogisticsDao.queryObject(id);
	}
	
	@Override
	public List<SysLogisticsEntity> queryList(Map<String, Object> map){
		return sysLogisticsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysLogisticsDao.queryTotal(map);
	}
	
	@Override
	public void save(SysLogisticsEntity sysLogistics){
		sysLogisticsDao.save(sysLogistics);
	}
	
	@Override
	public void update(SysLogisticsEntity sysLogistics){
		sysLogisticsDao.update(sysLogistics);
	}
	
	@Override
	public void delete(Long id){
		sysLogisticsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysLogisticsDao.deleteBatch(ids);
	}
	
}
