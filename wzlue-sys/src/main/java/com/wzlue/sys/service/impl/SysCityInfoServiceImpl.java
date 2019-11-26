package com.wzlue.sys.service.impl;

import com.wzlue.sys.dao.SysCityInfoDao;
import com.wzlue.sys.entity.SysCityInfoEntity;
import com.wzlue.sys.service.SysCityInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("sysCityInfoService")
public class SysCityInfoServiceImpl implements SysCityInfoService {
   @Autowired
   private SysCityInfoDao sysCityInfoDao;


	@Override
	public List<SysCityInfoEntity> queryByCode(String subCityCode) {
		return sysCityInfoDao.queryByCode(subCityCode);
	}


	@Override
	public Map<String,String> queryProvinceByName(String cityName)
	{
		return sysCityInfoDao.queryProvinceByName(cityName).stream().collect(Collectors.toMap(SysCityInfoEntity::getCityName,SysCityInfoEntity::getCityId));
	}


	@Override
	public Map<String,String> queryByCodeToMap(String subCityCode)
	{
		return sysCityInfoDao.queryByCode(subCityCode).stream().collect(Collectors.toMap(SysCityInfoEntity::getCityName,SysCityInfoEntity::getCityId));
	}

	@Override
	public SysCityInfoEntity queryNameByCode(SysCityInfoEntity sysCityInfoEntity) {
		return sysCityInfoDao.queryNameByCode(sysCityInfoEntity);
	}
}
