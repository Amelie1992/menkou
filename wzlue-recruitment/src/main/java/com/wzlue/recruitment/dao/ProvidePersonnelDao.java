package com.wzlue.recruitment.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.recruitment.entity.ProvidePersonnelEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 供人信息表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 16:13:50
 */
@Mapper
public interface ProvidePersonnelDao extends BaseDao<ProvidePersonnelEntity> {

	/**
	 * 批量上传
	 * @param providePersonnelEntities
	 */
	void saveList(List<ProvidePersonnelEntity> providePersonnelEntities);

    void updateByHistoryId(ProvidePersonnelEntity person);
}
