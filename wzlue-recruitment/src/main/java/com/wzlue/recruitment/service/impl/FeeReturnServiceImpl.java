package com.wzlue.recruitment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.recruitment.dao.FeeReturnDao;
import com.wzlue.recruitment.entity.FeeReturnEntity;
import com.wzlue.recruitment.service.FeeReturnService;



@Service("feeReturnService")
public class FeeReturnServiceImpl implements FeeReturnService {
	@Autowired
	private FeeReturnDao feeReturnDao;
	
	@Override
	public FeeReturnEntity queryObject(Long id){
		return feeReturnDao.queryObject(id);
	}
	
	@Override
	public List<FeeReturnEntity> queryList(Map<String, Object> map){
		return feeReturnDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return feeReturnDao.queryTotal(map);
	}
	
	@Override
	public void save(FeeReturnEntity feeReturn){
		feeReturnDao.save(feeReturn);
	}
	
	@Override
	public void update(FeeReturnEntity feeReturn){
		feeReturnDao.update(feeReturn);
	}
	
	@Override
	public void delete(Long id){
		feeReturnDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		feeReturnDao.deleteBatch(ids);
	}

	@Override
	public List<FeeReturnEntity> queryByRecruitmentId(Long recruitmentId) {
		return feeReturnDao.queryByRecruitmentId(recruitmentId);
	}

}
