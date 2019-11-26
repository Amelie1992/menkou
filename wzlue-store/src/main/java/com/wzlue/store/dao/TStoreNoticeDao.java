package com.wzlue.store.dao;

import com.wzlue.store.entity.TStoreNoticeEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门店公告表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 16:44:30
 */
@Mapper
public interface TStoreNoticeDao extends BaseDao<TStoreNoticeEntity> {

    List<TStoreNoticeEntity> queryListByParam(Map<String, Object> map);

    int queryTotalByParam(Map<String, Object> map);
	
}
