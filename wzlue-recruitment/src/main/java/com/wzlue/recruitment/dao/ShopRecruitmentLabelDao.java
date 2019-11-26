package com.wzlue.recruitment.dao;

import com.wzlue.recruitment.entity.ShopRecruitmentLabelEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 门店招聘-标签
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 10:51:26
 */
@Mapper
public interface ShopRecruitmentLabelDao extends BaseDao<ShopRecruitmentLabelEntity> {
    void saveList(List<ShopRecruitmentLabelEntity> labelList);

    List<ShopRecruitmentLabelEntity> queryByRecruitmentId(Long recruitmentId);


    void deleteByRecruitmentId(Long recruitmentId);

}
