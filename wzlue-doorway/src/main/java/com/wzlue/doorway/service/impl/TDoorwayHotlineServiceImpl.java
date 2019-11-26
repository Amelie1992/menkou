package com.wzlue.doorway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.doorway.dao.TDoorwayHotlineDao;
import com.wzlue.doorway.entity.TDoorwayHotlineEntity;
import com.wzlue.doorway.service.TDoorwayHotlineService;



@Service("tDoorwayHotlineService")
public class TDoorwayHotlineServiceImpl implements TDoorwayHotlineService {
	@Autowired
	private TDoorwayHotlineDao tDoorwayHotlineDao;
	
	@Override
	public TDoorwayHotlineEntity queryObject(Long id){
		return tDoorwayHotlineDao.queryObject(id);
	}
	
	@Override
	public List<TDoorwayHotlineEntity> queryList(Map<String, Object> map){
		return tDoorwayHotlineDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return tDoorwayHotlineDao.queryTotal(map);
	}
	
	@Override
	public void save(TDoorwayHotlineEntity tDoorwayHotline){
		tDoorwayHotlineDao.save(tDoorwayHotline);
	}
	
	@Override
	public void update(TDoorwayHotlineEntity tDoorwayHotline){
		tDoorwayHotlineDao.update(tDoorwayHotline);
	}
	
	@Override
	public void delete(Long id){
		tDoorwayHotlineDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		tDoorwayHotlineDao.deleteBatch(ids);
	}
	
}
