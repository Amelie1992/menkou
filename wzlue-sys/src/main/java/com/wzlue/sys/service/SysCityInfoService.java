package com.wzlue.sys.service;
import com.wzlue.sys.entity.SysCityInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 城市信息表
 * 
 * @author 
 * @email 
 * @date 2018-11-26 10:05:57
 */
public interface SysCityInfoService {
	//根据上级地市编码查询
	List<SysCityInfoEntity> queryByCode(@Param("subCityCode") String subCityCode);
	
	/**
     * 根据省名查询省  返回map
     * @Description:
     * @param cityName
     * @return
     * @version:v1.0
     * @author:QianTao
     * @date:2018年12月5日 下午7:54:40
     */
    Map<String,String> queryProvinceByName(@Param("cityName") String cityName);
    
    /**
     * 根据上级地市编码查询 返回map
     * @Description:
     * @param subCityCode
     * @return
     * @version:v1.0
     * @author:QianTao
     * @date:2018年12月5日 下午8:19:45
     */
    Map<String,String> queryByCodeToMap(@Param("subCityCode") String subCityCode);

    /**
     * 根据编码和上级编码查名字
     * @param
     * @return
     */
    SysCityInfoEntity queryNameByCode(SysCityInfoEntity sysCityInfoEntity);

}
