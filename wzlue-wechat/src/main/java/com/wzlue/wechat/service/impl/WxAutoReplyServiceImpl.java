package com.wzlue.wechat.service.impl;

import com.wzlue.wechat.dao.WxAutoReplyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.wechat.entity.WxAutoReplyEntity;
import com.wzlue.wechat.service.WxAutoReplyService;


@Service("wxAutreplyService")
public class WxAutoReplyServiceImpl implements WxAutoReplyService {
    @Autowired
    private WxAutoReplyDao wxAutreplyDao;

    @Override
    public WxAutoReplyEntity queryObject(String id) {
        return wxAutreplyDao.queryObject(id);
    }

    @Override
    public List<WxAutoReplyEntity> queryList(Map<String, Object> map) {
        return wxAutreplyDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return wxAutreplyDao.queryTotal(map);
    }

    @Override
    public void save(WxAutoReplyEntity wxAutreply) {
        wxAutreplyDao.save(wxAutreply);
    }

    @Override
    public void update(WxAutoReplyEntity wxAutreply) {
        wxAutreplyDao.update(wxAutreply);
    }

    @Override
    public void delete(String id) {
        wxAutreplyDao.delete(id);
    }

    @Override
    public void deleteBatch(String[] ids) {
        wxAutreplyDao.deleteBatch(ids);
    }

    @Override
    public List<WxAutoReplyEntity> findList(String appId, String type, String repMate, String reqKey, String reqType) {
        return wxAutreplyDao.findList(appId, type, repMate, reqKey, reqType);
    }

    @Override
    public List<WxAutoReplyEntity> queryDefault(String appId) {
        return wxAutreplyDao.queryDefault(appId);
    }
}
