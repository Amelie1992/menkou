package com.wzlue.wechat.dao;

import com.wzlue.common.base.Query;
import com.wzlue.wechat.entity.WxMsgEntity;
import com.wzlue.common.base.BaseDao;
import com.wzlue.wechat.entity.WxMsgEntityVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 微信消息
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-23 17:59:03
 */
@Mapper
public interface WxMsgDao extends BaseDao<WxMsgEntity> {

    List<WxMsgEntityVO> listWxMsgMapGroup(Map<String, Object> map);

    int listWxMsgMapGroupCount(Query query);

    List<WxMsgEntity> findListByUserIdFlagAppid(Query query);

    int findListByUserIdFlagAppidCount(Query query);
}
