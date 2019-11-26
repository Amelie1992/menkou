package com.wzlue.draw.dao;

import com.wzlue.draw.entity.WxAppIntegralDrawEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 门店（自定义）积分抽奖活动   （付费开通） 
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
@Mapper
public interface WxAppIntegralDrawDao extends BaseDao<WxAppIntegralDrawEntity> {


    List<WxAppIntegralDrawEntity> queryAppList(Map<String, Object> map);

    int queryAppTotal(Map<String, Object> map);
	
}
