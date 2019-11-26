package com.wzlue.jobApplication.service.impl;

import com.wzlue.common.exception.RRException;
import com.wzlue.wechat.dao.WxUserDao;
import com.wzlue.wechat.entity.WxUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.wzlue.jobApplication.dao.BonusRecordDao;
import com.wzlue.jobApplication.entity.BonusRecordEntity;
import com.wzlue.jobApplication.service.BonusRecordService;


@Service("bonusRecordService")
public class BonusRecordServiceImpl implements BonusRecordService {
    @Autowired
    private BonusRecordDao bonusRecordDao;
    @Autowired
    private WxUserDao wxUserDao;

    @Override
    public BonusRecordEntity queryObject(Long id) {
        return bonusRecordDao.queryObject(id);
    }

    @Override
    public List<BonusRecordEntity> queryList(Map<String, Object> map) {
        return bonusRecordDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return bonusRecordDao.queryTotal(map);
    }

    @Override
    public void save(BonusRecordEntity bonusRecord) {
        bonusRecordDao.save(bonusRecord);
    }

    @Override
    public void update(BonusRecordEntity bonusRecord) {
        bonusRecordDao.update(bonusRecord);
    }

    @Override
    public void delete(Long id) {
        bonusRecordDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        bonusRecordDao.deleteBatch(ids);
    }

    //奖励金提现
    @Override
    public void withdraw(String openid, BigDecimal amount) {
        WxUserEntity wxUserEntity = wxUserDao.queryObject(openid);
        if (null != wxUserEntity) {
            BigDecimal bonus = wxUserEntity.getBonus();
            if (null == bonus) {
                bonus = BigDecimal.ZERO;
            } else if (amount.compareTo(bonus) == 1) {
                throw new RRException("提现金额不能大于奖励金");
            }
            WxUserEntity wxUser = new WxUserEntity();
            wxUser.setId(openid);
            wxUser.setBonus(bonus.subtract(amount));
            wxUserDao.update(wxUser);

            BonusRecordEntity bonusRecord = new BonusRecordEntity();
            bonusRecord.setOpenid(openid);
            bonusRecord.setAmount(amount);
            bonusRecord.setType(2);
            bonusRecord.setRemark("提现");
            bonusRecord.setAppId(wxUserEntity.getAppId());
            bonusRecordDao.save(bonusRecord);
        }
    }

}
