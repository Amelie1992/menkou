package com.wzlue.draw.service.impl;

import com.wzlue.draw.dao.IntegralDrawPrizeDao;
import com.wzlue.draw.entity.IntegralDrawPrizeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wzlue.draw.dao.WxAppIntegralDrawDao;
import com.wzlue.draw.entity.WxAppIntegralDrawEntity;
import com.wzlue.draw.service.WxAppIntegralDrawService;


@Service("wxAppIntegralDrawService")
public class WxAppIntegralDrawServiceImpl implements WxAppIntegralDrawService {
    @Autowired
    private WxAppIntegralDrawDao wxAppIntegralDrawDao;
    @Autowired
    private IntegralDrawPrizeDao integralDrawPrizeDao;

    @Override
    public WxAppIntegralDrawEntity queryObject(String appId) {
        return wxAppIntegralDrawDao.queryObject(appId);
    }

    @Override
    public List<WxAppIntegralDrawEntity> queryList(Map<String, Object> map) {
        return wxAppIntegralDrawDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return wxAppIntegralDrawDao.queryTotal(map);
    }

    @Override
    public void save(WxAppIntegralDrawEntity wxAppIntegralDraw) {
        wxAppIntegralDrawDao.save(wxAppIntegralDraw);
    }

    @Override
    public void update(WxAppIntegralDrawEntity wxAppIntegralDraw) {
        wxAppIntegralDrawDao.update(wxAppIntegralDraw);
        List<IntegralDrawPrizeEntity> prizeList = wxAppIntegralDraw.getPrizeList();
        if (null != prizeList && prizeList.size() > 0) {
            integralDrawPrizeDao.deleteByAppId(wxAppIntegralDraw.getAppId());
            for (IntegralDrawPrizeEntity prize : prizeList) {
		        prize.setAppId(wxAppIntegralDraw.getAppId());
		        prize.setUpdateTime(new Date());
		        integralDrawPrizeDao.save(prize);
            }
        }
    }

    @Override
    public void delete(String appId) {
        wxAppIntegralDrawDao.delete(appId);
    }

    @Override
    public void deleteBatch(String[] appIds) {
        wxAppIntegralDrawDao.deleteBatch(appIds);
    }


    @Override
    public List<WxAppIntegralDrawEntity> queryAppList(Map<String, Object> map) {
        return wxAppIntegralDrawDao.queryAppList(map);
    }

    @Override
    public int queryAppTotal(Map<String, Object> map) {
        return wxAppIntegralDrawDao.queryAppTotal(map);
    }

}
