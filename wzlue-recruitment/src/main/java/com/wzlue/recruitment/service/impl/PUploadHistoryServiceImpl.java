package com.wzlue.recruitment.service.impl;

import com.wzlue.recruitment.dao.PUploadHistoryDao;
import com.wzlue.recruitment.dao.ProvidePersonnelDao;
import com.wzlue.recruitment.entity.PUploadHistoryEntity;
import com.wzlue.recruitment.entity.ProvidePersonnelEntity;
import com.wzlue.recruitment.service.PUploadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("pUploadHistoryService")
public class PUploadHistoryServiceImpl implements PUploadHistoryService {
    @Autowired
    private PUploadHistoryDao pUploadHistoryDao;
    @Autowired
    private ProvidePersonnelDao providePersonnelDao;

    @Override
    public PUploadHistoryEntity queryObject(Long id) {
        return pUploadHistoryDao.queryObject(id);
    }

    @Override
    public List<PUploadHistoryEntity> queryList(Map<String, Object> map) {
        return pUploadHistoryDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return pUploadHistoryDao.queryTotal(map);
    }

    @Override
    public void save(PUploadHistoryEntity pUploadHistory) {
        pUploadHistory.setStatus(1);
        pUploadHistory.setDelFlag(2);
        pUploadHistory.setCreateDate(new Date());
        pUploadHistoryDao.save(pUploadHistory);
    }

    @Override
    public void update(PUploadHistoryEntity pUploadHistory) {
        pUploadHistoryDao.update(pUploadHistory);
    }

    @Override
    public void delete(Long id) {
        pUploadHistoryDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        pUploadHistoryDao.deleteBatch(ids);
    }

    @Override
    @Transactional
    public void auditOk(Long[] ids) {

        for (Long id : ids) {
            // 更新供人历史清单
            PUploadHistoryEntity history = new PUploadHistoryEntity();
            history.setId(id);
            history.setStatus(2);
            pUploadHistoryDao.update(history);

            // 更新供人信息
            ProvidePersonnelEntity person = new ProvidePersonnelEntity();
            person.setUploadHistoryId(id);
            person.setStatus(2);
            providePersonnelDao.updateByHistoryId(person);
        }

    }

    @Override
    @Transactional
    public void auditNo(Map<String, Object> params) {

        ArrayList ids = (ArrayList)params.get("ids");
        String content = (String)params.get("content");
        for (Object id : ids) {
            // 更新供人历史清单
            PUploadHistoryEntity history = new PUploadHistoryEntity();
            history.setId(Long.valueOf(id + ""));
            history.setStatus(3);//平台拒绝
            history.setReason(content);
            history.setConfirm(3);//用人门店拒绝
            history.setErrorInfo(content);
            pUploadHistoryDao.update(history);

            // 更新供人信息
            ProvidePersonnelEntity person = new ProvidePersonnelEntity();
            person.setUploadHistoryId(Long.valueOf(id + ""));
            person.setStatus(3);
            person.setReason(content);
            providePersonnelDao.updateByHistoryId(person);
        }

    }

    //门店确认供人
    @Override
    public void confirm(Map<String, Object> map) {
        pUploadHistoryDao.confirm(map);
    }
}
