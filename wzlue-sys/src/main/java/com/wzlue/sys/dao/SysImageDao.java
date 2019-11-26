package com.wzlue.sys.dao;

import com.wzlue.sys.entity.SysImageEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 图片表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-09 16:53:50
 */
@Mapper
public interface SysImageDao extends BaseDao<SysImageEntity> {

    void deleteByTypeAndCode(@Param("type") String type,@Param("code") Long code);

    void deleteByTypeAndRemark(@Param("type") String type,@Param("remark") String remark);
	
}
