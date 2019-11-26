package com.wzlue.provideStaff.dao;

import com.wzlue.provideStaff.entity.ProvideStaffEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 供人信息
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-24 16:17:25
 */
@Mapper
public interface ProvideStaffDao extends BaseDao<ProvideStaffEntity> {

    List<ProvideStaffEntity> examineList(Map<String, Object> map);

    int examineTotal(Map<String, Object> map);
	
}
