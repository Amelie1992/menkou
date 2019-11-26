package com.wzlue.recruitment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.recruitment.dao.ShopRecruitmentLabelDao;
import com.wzlue.recruitment.entity.ShopRecruitmentLabelEntity;
import com.wzlue.recruitment.service.ShopRecruitmentLabelService;



@Service("shopRecruitmentLabelService")
public class ShopRecruitmentLabelServiceImpl implements ShopRecruitmentLabelService {
	@Autowired
	private ShopRecruitmentLabelDao shopRecruitmentLabelDao;
	
	@Override
	public ShopRecruitmentLabelEntity queryObject(Long id){
		return shopRecruitmentLabelDao.queryObject(id);
	}
	
	@Override
	public List<ShopRecruitmentLabelEntity> queryList(Map<String, Object> map){
		return shopRecruitmentLabelDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return shopRecruitmentLabelDao.queryTotal(map);
	}
	
	@Override
	public void save(ShopRecruitmentLabelEntity shopRecruitmentLabel){
		shopRecruitmentLabelDao.save(shopRecruitmentLabel);
	}
	
	@Override
	public void update(ShopRecruitmentLabelEntity shopRecruitmentLabel){
		shopRecruitmentLabelDao.update(shopRecruitmentLabel);
	}
	
	@Override
	public void delete(Long id){
		shopRecruitmentLabelDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		shopRecruitmentLabelDao.deleteBatch(ids);
	}
	
}
