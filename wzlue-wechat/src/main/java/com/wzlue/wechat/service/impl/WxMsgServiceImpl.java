package com.wzlue.wechat.service.impl;

import com.wzlue.common.base.Query;
import com.wzlue.wechat.dao.WxMsgDao;
import com.wzlue.wechat.entity.WxMsgEntity;
import com.wzlue.wechat.entity.WxMsgEntityVO;
import com.wzlue.wechat.service.WxMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 微信消息
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-23 15:11:41
 */
@Service
public class WxMsgServiceImpl implements WxMsgService {

    @Autowired
    private WxMsgDao wxMsgDao;

    @Override
    public WxMsgEntity queryObject(String id) {
        return wxMsgDao.queryObject(id);
    }

    @Override
    public List<WxMsgEntity> queryList(Map<String, Object> map) {
        return wxMsgDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return wxMsgDao.queryTotal(map);
    }

    @Override
    public void save(WxMsgEntity wxApp) {
        wxMsgDao.save(wxApp);
    }

    @Override
    public void update(WxMsgEntity wxApp) {
        wxMsgDao.update(wxApp);
    }

    @Override
    public void delete(String id) {
        wxMsgDao.delete(id);
    }

    @Override
    public void deleteBatch(String[] ids) {
        wxMsgDao.deleteBatch(ids);
    }

    @Override
    public List<WxMsgEntityVO> listWxMsgMapGroup(Map<String, Object> map) {
        return wxMsgDao.listWxMsgMapGroup(map);
    }

    @Override
    public int listWxMsgMapGroupCount(Query query) {
        return wxMsgDao.listWxMsgMapGroupCount(query);
    }

    @Override
    public List<WxMsgEntity> findListByUserIdFlagAppid(Query query) {
        return wxMsgDao.findListByUserIdFlagAppid(query);
    }

    @Override
    public int findListByUserIdFlagAppidCount(Query query) {
        return wxMsgDao.findListByUserIdFlagAppidCount(query);
    }
}
