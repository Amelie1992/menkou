package com.wzlue.jobApplication.service.impl;

import com.wzlue.jobApplication.dao.MemberClockDao;
import com.wzlue.jobApplication.entity.MemberClockEntity;
import com.wzlue.jobApplication.service.MemberClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("memberClockService")
public class MemberClockServiceImpl implements MemberClockService {
	@Autowired
	private MemberClockDao memberClockDao;
	
	@Override
	public MemberClockEntity queryObject(String id){
		return memberClockDao.queryObject(id);
	}
	
	@Override
	public List<MemberClockEntity> queryList(Map<String, Object> map){
		return memberClockDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return memberClockDao.queryTotal(map);
	}
	
	@Override
	public void save(MemberClockEntity memberClock){
		memberClockDao.save(memberClock);
	}
	
	@Override
	public void update(MemberClockEntity memberClock){
		memberClockDao.update(memberClock);
	}
	
	@Override
	public void delete(String id){
		memberClockDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		memberClockDao.deleteBatch(ids);
	}
	
}
