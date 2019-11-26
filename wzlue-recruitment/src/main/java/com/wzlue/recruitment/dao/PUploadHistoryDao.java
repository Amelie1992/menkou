package com.wzlue.recruitment.dao;

import com.wzlue.common.base.BaseDao;
import com.wzlue.recruitment.entity.PUploadHistoryEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 供人历史清单表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 16:13:50
 */
@Mapper
public interface PUploadHistoryDao extends BaseDao<PUploadHistoryEntity> {
    //门店确认供人
    void confirm(Map<String, Object> map);


}
