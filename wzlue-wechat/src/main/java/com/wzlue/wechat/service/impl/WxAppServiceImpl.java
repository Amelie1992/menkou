package com.wzlue.wechat.service.impl;

import com.wzlue.common.base.Query;
import com.wzlue.common.exception.RRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.wzlue.wechat.dao.WxAppDao;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.service.WxAppService;


@Service("wxAppService")
public class WxAppServiceImpl implements WxAppService {
    @Autowired
    private WxAppDao wxAppDao;

    @Override
    public WxAppEntity queryObject(String id) {
        return wxAppDao.queryObject(id);
    }

    @Override
    public List<WxAppEntity> queryList(Map<String, Object> map) {
        return wxAppDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return wxAppDao.queryTotal(map);
    }

    @Override
    public void save(WxAppEntity wxApp) {
        wxAppDao.save(wxApp);
    }

    @Override
    public void update(WxAppEntity wxApp) {
        wxAppDao.update(wxApp);
    }

    @Override
    public void delete(String id) {
        wxAppDao.delete(id);
    }

    @Override
    public void deleteBatch(String[] ids) {
        wxAppDao.deleteBatch(ids);
    }

    @Override
    public WxAppEntity getById(String appId) {
        return wxAppDao.getById(appId);
    }

    /**
     * 微信原始标识查找
     *
     * @param weixinSign
     * @return
     */
    @Override
    public WxAppEntity findByWeixinSign(String weixinSign) {
        return wxAppDao.findByWeixinSign(weixinSign);
//		return baseMapper.selectOne(Wrappers.query(new WxApp()).eq("weixin_sign",weixinSign));
    }

    @Override
    public List<WxAppEntity> selectlist(Query query) {
        return wxAppDao.selectlist(query);
    }

    @Override
    public void editQualified(WxAppEntity wxApp) {
        int count = wxAppDao.isQualified(wxApp);
        if (count <= 0) {
            throw new RRException("门店资质未开启");
        }
        WxAppEntity editApp = new WxAppEntity();

        editApp.setId(wxApp.getId());
        editApp.setDescription(wxApp.getDescription());

        wxAppDao.update(editApp);
    }

    @Override
    public void updateEnabled(String[] ids) {
        List<WxAppEntity> listByAppIds = wxAppDao.getListByAppIds(ids);
        for (WxAppEntity appEntity : listByAppIds) {
            String enabled = appEntity.getEnabled();
            if ("1".equals(enabled))
                appEntity.setEnabled("0");
            else if ("0".equals(enabled))
                appEntity.setEnabled("1");
            else
                break;

            WxAppEntity entity = new WxAppEntity();
            entity.setId(appEntity.getId());
            entity.setEnabled(appEntity.getEnabled());

            wxAppDao.update(entity);
        }
    }

    @Override
    public void updateQualified(String[] ids) {
        List<WxAppEntity> listByAppIds = wxAppDao.getListByAppIds(ids);
        for (WxAppEntity appEntity : listByAppIds) {
            String enabled = appEntity.getQualified();
            if ("1".equals(enabled))
                appEntity.setQualified("0");
            else if ("0".equals(enabled))
                appEntity.setQualified("1");
            else
                break;

            WxAppEntity entity = new WxAppEntity();
            entity.setId(appEntity.getId());
            entity.setQualified(appEntity.getQualified());

            wxAppDao.update(entity);
        }
    }

    @Override
    public List<WxAppEntity> selectlistNotApp() {
        return wxAppDao.selectlistNotApp();
    }
}
