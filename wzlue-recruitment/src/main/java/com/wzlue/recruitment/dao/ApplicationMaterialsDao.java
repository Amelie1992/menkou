package com.wzlue.recruitment.dao;

import com.wzlue.recruitment.entity.ApplicationMaterialsEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 申请材料（上平台招聘的申请材料）
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-09 16:51:59
 */
@Mapper
public interface ApplicationMaterialsDao extends BaseDao<ApplicationMaterialsEntity> {

    ApplicationMaterialsEntity queryByRecruitmentId(Long recruitmentId);

    void deleteByRecruitmentId(Long recruitmentId);

}
