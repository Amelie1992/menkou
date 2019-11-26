package com.wzlue.recruitment.dao;

import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 门店招聘
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 10:49:06
 */
@Mapper
public interface ShopRecruitmentDao extends BaseDao<ShopRecruitmentEntity> {

    void updateShopSuspendFlag(Map<String, Object> map);
    void updateHavingStateAndMsg(Map<String, Object> map);

    /**
     * 平台招聘信息列表
     * @param map
     * @return
     */
    List<ShopRecruitmentEntity> queryPlatFormList(Map<String, Object> map);

    int queryPlatFormTotal(Map<String, Object> map);

    //检验是否重复转发
    Integer exist(Long id);


    int queryTotalByIds(Map<String, Object> map);


    /**
     * 平台查询
     * @param map
     * @return
     */
    List<ShopRecruitmentEntity> queryListByPlatform(Map<String, Object> map);


    int queryTotalByPlatform(Map<String, Object> map);


    /**
     * 勾选导出
     * @param ids
     * @return
     */
    List<ShopRecruitmentEntity> queryListByIds(Object[] ids);

    /**
     * 全部导出
     * @param map
     * @return
     */
    List<ShopRecruitmentEntity> queryListAll(Map<String, Object> map);


    void pageView(Long id);

}
