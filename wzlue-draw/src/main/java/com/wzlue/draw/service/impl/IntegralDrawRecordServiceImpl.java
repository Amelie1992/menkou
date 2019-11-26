package com.wzlue.draw.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.draw.dao.IntegralDrawRecordDao;
import com.wzlue.draw.entity.IntegralDrawRecordEntity;
import com.wzlue.draw.service.IntegralDrawRecordService;



@Service("integralDrawRecordService")
public class IntegralDrawRecordServiceImpl implements IntegralDrawRecordService {
	@Autowired
	private IntegralDrawRecordDao integralDrawRecordDao;
	
	@Override
	public IntegralDrawRecordEntity queryObject(Long id){
		return integralDrawRecordDao.queryObject(id);
	}
	
	@Override
	public List<IntegralDrawRecordEntity> queryList(Map<String, Object> map){
		return integralDrawRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return integralDrawRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(IntegralDrawRecordEntity integralDrawRecord){
		integralDrawRecordDao.save(integralDrawRecord);
	}
	
	@Override
	public void update(IntegralDrawRecordEntity integralDrawRecord){
		integralDrawRecordDao.update(integralDrawRecord);
	}
	
	@Override
	public void delete(Long id){
		integralDrawRecordDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		integralDrawRecordDao.deleteBatch(ids);
	}


    @Override
    public void exchangeBatch(Long[] ids){
        integralDrawRecordDao.exchangeBatch(ids);
    }
}
