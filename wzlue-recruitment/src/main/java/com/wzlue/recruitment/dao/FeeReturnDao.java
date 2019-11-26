package com.wzlue.recruitment.dao;

import com.wzlue.recruitment.entity.FeeReturnEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * （招聘）返费
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 17:12:55
 */
@Mapper
public interface FeeReturnDao extends BaseDao<FeeReturnEntity> {

    void saveList(List<FeeReturnEntity> feeReturnList);

    List<FeeReturnEntity> queryByRecruitmentId(Long recruitmentId);

    /**
     * 查询最大的等级天数
     * @param recruitmentId
     * @return
     */
    int queryMaxDay(Long recruitmentId);

    void deleteByRecruitmentId(Long recruitmentId);
}
