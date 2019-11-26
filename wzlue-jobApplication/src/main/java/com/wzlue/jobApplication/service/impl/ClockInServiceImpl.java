package com.wzlue.jobApplication.service.impl;

import com.wzlue.jobApplication.dao.ClockInDao;
import com.wzlue.jobApplication.entity.ClockInEntity;
import com.wzlue.jobApplication.service.ClockInService;
import com.wzlue.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("clockInService")
public class ClockInServiceImpl implements ClockInService {
	@Autowired
	private ClockInDao clockInDao;
	
	@Override
	public ClockInEntity queryObject(String id){
		return clockInDao.queryObject(id);
	}
	
	@Override
	public List<ClockInEntity> queryList(Map<String, Object> map){
		return clockInDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return clockInDao.queryTotal(map);
	}
	
	@Override
	@Transactional
	public void save(ClockInEntity clockIn){
		clockIn.setCreateDate(new Date());
		clockInDao.save(clockIn);
	}
	
	@Override
	@Transactional
	public void update(ClockInEntity clockIn){
		clockIn.setUpdateDate(new Date());
		clockInDao.update(clockIn);
	}
	
	@Override
	@Transactional
	public void delete(String id){
		clockInDao.delete(id);
	}
	
	@Override
	@Transactional
	public void deleteBatch(String[] ids){
		clockInDao.updateBatch(ids);//软删除
	}

	@Override
	public ClockInEntity queryRecruitClock(Long recruitId) {
		return clockInDao.queryRecruitClock(recruitId);
	}

	@Override
	public ClockInEntity queryJobClock(Long memberId) {
		return clockInDao.queryJobClock(memberId);
	}

	@Override
	public List<ClockInEntity> queryEntryList(Map<String, Object> map) {
		return clockInDao.queryEntryList(map);
	}

	@Override
	@Transactional
	public int updateDel(ClockInEntity clockInEntity) {
		return clockInDao.updateDel(clockInEntity);
	}

}
