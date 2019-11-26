package com.wzlue.sys.dao;

import com.wzlue.sys.entity.SysAreaEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author YJ
 * @email wzlue.com
 * @date 2018-06-11 14:13:34
 */
@Mapper
public interface SysAreaDao extends BaseDao<SysAreaEntity> {


    List<SysAreaEntity> areaList(Map<String,Object> params);

    List<SysAreaEntity> queryProvincesList(Map<String, Object> map);

    int queryProvincesListTotal(Map<String, Object> map);

    //根据上级地市编码查询
    List<SysAreaEntity> queryByCode(@Param("parentid") Integer parentid);

    /**
     * 根据编码和上级编码查名字
     * @param
     * @return
     */
    SysAreaEntity queryNameByCode(SysAreaEntity sysAreaEntity);

    /**
     * 根据名字查编码
     * @param
     * @return
     */
    List<SysAreaEntity> queryCodeByName(String name);
}
