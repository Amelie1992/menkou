package com.wzlue.sys.dao;
import com.wzlue.sys.entity.SysCityInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 城市信息表
 * 
 * @author 
 * @email 
 * @date 2018-11-26 10:05:57
 */
@Mapper
public interface SysCityInfoDao {

    //根据上级地市编码查询
    List<SysCityInfoEntity> queryByCode(@Param("subCityCode") String subCityCode);
	
    /**
     * 根据省名查询省
     * @Description:
     * @param cityName
     * @return
     * @version:v1.0
     * @author:QianTao
     * @date:2018年12月5日 下午7:54:40
     */
    List<SysCityInfoEntity> queryProvinceByName(@Param("cityName") String cityName);

    /**
     * 根据编码和上级编码查名字
     * @param
     * @return
     */
    SysCityInfoEntity queryNameByCode(SysCityInfoEntity sysCityInfoEntity);
}
