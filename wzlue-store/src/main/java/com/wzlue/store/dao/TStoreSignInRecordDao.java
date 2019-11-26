package com.wzlue.store.dao;

import com.wzlue.store.entity.TStoreSignInRecordEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 门店签到记录
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 17:03:41
 */
@Mapper
public interface TStoreSignInRecordDao extends BaseDao<TStoreSignInRecordEntity> {


    int queryTodaySignInRecord(TStoreSignInRecordEntity tStoreSignInRecordEntity);


    TStoreSignInRecordEntity queryYesterdaySignInRecord(TStoreSignInRecordEntity tStoreSignInRecordEntity);

}
