package com.wzlue.sys.service.impl;

import com.wzlue.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.sys.dao.SysAreaDao;
import com.wzlue.sys.entity.SysAreaEntity;
import com.wzlue.sys.service.SysAreaService;


@Service("sysAreaService")
public class SysAreaServiceImpl implements SysAreaService {
    @Autowired
    private SysAreaDao sysAreaDao;

    @Override
    public SysAreaEntity queryObject(Integer id) {
        return sysAreaDao.queryObject(id);
    }

    @Override
    public List<SysAreaEntity> queryList(Map<String, Object> map) {
        return sysAreaDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysAreaDao.queryTotal(map);
    }

    @Override
    public void save(SysAreaEntity sysArea) {
        sysAreaDao.save(sysArea);
    }

    @Override
    public void update(SysAreaEntity sysArea) {
        sysAreaDao.update(sysArea);
    }

    @Override
    public void delete(Integer id) {
        sysAreaDao.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        sysAreaDao.deleteBatch(ids);
    }

    @Override
    public List<SysAreaEntity> areaList(Map<String, Object> params) {
        String areaKey = "sysAreaList" + (params.get("parentid") != null ? "_" + params.get("parentid").toString() : "_0");

        List<SysAreaEntity> sysAreaList = null;
//        try {
//            sysAreaList = RedisUtils.get(areaKey, List.class);
//        } catch (Exception e) {
//
//        }
//        if (null != sysAreaList && sysAreaList.size() > 0)
//            return sysAreaList;
        sysAreaList = sysAreaDao.areaList(params);
//        RedisUtils.set(areaKey, sysAreaList);
        return sysAreaList;
    }


    @Override
    public List<SysAreaEntity> queryProvincesList(Map<String, Object> map) {
        return sysAreaDao.queryProvincesList(map);
    }

    @Override
    public int queryProvincesListTotal(Map<String, Object> map) {
        return sysAreaDao.queryProvincesListTotal(map);
    }


    @Override
    public SysAreaEntity queryNameByCode(SysAreaEntity sysAreaEntity) {
        return sysAreaDao.queryNameByCode(sysAreaEntity);
    }

}
