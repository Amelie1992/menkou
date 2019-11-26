package com.wzlue.draw.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.draw.dao.IntegralDrawPrizeDao;
import com.wzlue.draw.entity.IntegralDrawPrizeEntity;
import com.wzlue.draw.service.IntegralDrawPrizeService;



@Service("integralDrawPrizeService")
public class IntegralDrawPrizeServiceImpl implements IntegralDrawPrizeService {
	@Autowired
	private IntegralDrawPrizeDao integralDrawPrizeDao;
	
	@Override
	public IntegralDrawPrizeEntity queryObject(Long id){
		return integralDrawPrizeDao.queryObject(id);
	}
	
	@Override
	public List<IntegralDrawPrizeEntity> queryList(Map<String, Object> map){
		return integralDrawPrizeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return integralDrawPrizeDao.queryTotal(map);
	}
	
	@Override
	public void save(IntegralDrawPrizeEntity integralDrawPrize){
		integralDrawPrizeDao.save(integralDrawPrize);
	}
	
	@Override
	public void update(IntegralDrawPrizeEntity integralDrawPrize){
		integralDrawPrizeDao.update(integralDrawPrize);
	}
	
	@Override
	public void delete(Long id){
		integralDrawPrizeDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		integralDrawPrizeDao.deleteBatch(ids);
	}
	
}
