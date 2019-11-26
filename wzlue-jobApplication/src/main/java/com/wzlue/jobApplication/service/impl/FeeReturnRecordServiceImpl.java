package com.wzlue.jobApplication.service.impl;

import com.wzlue.jobApplication.dao.FeeReturnRecordDao;
import com.wzlue.jobApplication.entity.FeeReturnRecordEntity;
import com.wzlue.jobApplication.service.FeeReturnRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("feeReturnRecordService")
public class FeeReturnRecordServiceImpl implements FeeReturnRecordService {
	@Autowired
	private FeeReturnRecordDao feeReturnRecordDao;
	
	@Override
	public FeeReturnRecordEntity queryObject(Long id){
		return feeReturnRecordDao.queryObject(id);
	}
	
	@Override
	public List<FeeReturnRecordEntity> queryList(Map<String, Object> map){
		return feeReturnRecordDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return feeReturnRecordDao.queryTotal(map);
	}
	
	@Override
	public void save(FeeReturnRecordEntity feeReturnRecord){
		feeReturnRecord.setCreateDate(new Date());

		feeReturnRecordDao.save(feeReturnRecord);
	}
	
	@Override
	public void update(FeeReturnRecordEntity feeReturnRecord){
		feeReturnRecord.setUpdateDate(new Date());
		feeReturnRecordDao.update(feeReturnRecord);
	}
	
	@Override
	public void delete(Long id){
		feeReturnRecordDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		Map<String, Object> map = new HashMap<>();
		map.put("ids",ids);
		feeReturnRecordDao.updateAllFlag(map);
		//feeReturnRecordDao.deleteBatch(ids);
	}

	@Override
	public List<FeeReturnRecordEntity> userFeeReturn(Map<String, Object> map) {
		return feeReturnRecordDao.userFeeReturn(map);
	}

}
