package com.wzlue.wechat.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.wechat.entity.WxMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义菜单
 *
 * @author JL
 * @date 2019-03-27 16:52:10
 */
@Mapper
public interface WxMenuDao extends BaseDao<WxMenuEntity> {

    void deleteById(String appId);

    void deleteByAppID(String appId);

    List<WxMenuEntity> selectMenu(@Param("appId") String appId, @Param("parentId") String parentId, @Param("sort") int sort);

    WxMenuEntity getById(String eventKey);
}
