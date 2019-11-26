package com.wzlue.jobApplication.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.jobApplication.entity.FeeReturnRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 返费记录表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-05 09:59:54
 */
@Mapper
public interface FeeReturnRecordDao extends BaseDao<FeeReturnRecordEntity> {

	/**
	 * 软删除
	 * @param
	 * @return
	 */
	int updateAllFlag(Map<String, Object> map);

	/**
	 * 用户所有返费list
	 * @param map
	 * @return
	 */
	List<FeeReturnRecordEntity> userFeeReturn(Map<String, Object> map);

	/**
	 * 根据入职id和返费规则Id查询该等级是否已返费
	 * @param jobId
	 * @param feeId
	 * @return
	 */
	int feeIsExist(@Param("jobId") Long jobId, @Param("feeId") Long feeId);
	int feeIsExistOfToday(@Param("jobId") Long jobId, @Param("createDate") String createDate);
}
