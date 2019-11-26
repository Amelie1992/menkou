package com.wzlue.recruitment.service;

import com.wzlue.recruitment.entity.ShopRecruitmentEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店招聘
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 10:49:06
 */
public interface ShopRecruitmentService {

    ShopRecruitmentEntity queryObject(Long id);

    List<ShopRecruitmentEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ShopRecruitmentEntity shopRecruitment);

    void update(ShopRecruitmentEntity shopRecruitment);

    void delete(Long id);

    void deleteBatch(Long[] ids);

    void updateShopSuspendFlag(Map<String, Object> map);
    void toExamineSuccessPl(Map<String, Object> map);
    void toExamineFailedPl(Map<String, Object> map);

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


    List<ShopRecruitmentEntity> queryListByPlatform(Map<String, Object> map);

    int queryTotalByPlatform(Map<String, Object> map);

    /**
     * 修改保证金
     * @param entity
     */
    void updateaErnest(ShopRecruitmentEntity entity);



    List<ShopRecruitmentEntity> queryListByIds(Object[] ids);
    List<ShopRecruitmentEntity> queryListAll(Map<String, Object> map);

    void pageView(Long id);
}
