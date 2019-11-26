package com.wzlue.jobApplication.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.jobApplication.entity.MemberClockEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 入职用户打卡表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-29 18:46:15
 */
@Mapper
public interface MemberClockDao extends BaseDao<MemberClockEntity> {
	
}
