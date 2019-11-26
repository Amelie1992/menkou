package com.wzlue.store.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.R;
import com.wzlue.common.exception.RRException;
import com.wzlue.store.dao.TStoreIntegralRecordDao;
import com.wzlue.store.entity.TStoreIntegralRecordEntity;
import com.wzlue.store.service.TStoreIntegralRecordService;
import com.wzlue.store.service.TStoreWxUserService;
import com.wzlue.sys.common.config.IntegralConfig;
import com.wzlue.sys.common.config.IntegralSetting;
import com.wzlue.wechat.entity.WxUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("tStoreIntegralRecordService")
public class TStoreIntegralRecordServiceImpl implements TStoreIntegralRecordService {
    @Autowired
    private TStoreIntegralRecordDao tStoreIntegralRecordDao;

    @Autowired
    private TStoreWxUserService tStoreWxUserService;


    @Autowired
    private IntegralConfig integralConfig;


    @Override
    public TStoreIntegralRecordEntity queryObject(Long id) {
        return tStoreIntegralRecordDao.queryObject(id);
    }

    @Override
    public List<TStoreIntegralRecordEntity> queryList(Map<String, Object> map) {
        return tStoreIntegralRecordDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return tStoreIntegralRecordDao.queryTotal(map);
    }

    @Override
    public List<TStoreIntegralRecordEntity> queryListByDate(Map<String, Object> map) {

        String year = (String) map.get("year");
        String month = (String) map.get("month");

        if (StringUtils.isNotBlank(month)) {
            if (!"10".equals(month) && !"11".equals(month) && !"12".equals(month) && !"0".equals(month.substring(0, 1))) {
                month = "0" + month;
                map.put("month", month);
            }
        }


        if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)) {
            map.put("createTime", year + month);
        }

        return tStoreIntegralRecordDao.queryListByYearAndMonth(map);
    }


    @Override
    public int queryTotalByDate(Map<String, Object> map) {
        return tStoreIntegralRecordDao.queryTotalByYearAndMonth(map);
    }


    @Override
    public void validateOfdoNewbieTask(String userId) {
        if (StrUtil.isBlank(userId)) {
            throw new RRException("入参用户id获取异常");
        }
        TStoreIntegralRecordEntity integralRecord = new TStoreIntegralRecordEntity();
        integralRecord.setUserId(userId);
        integralRecord.setIntegralType(2);
        int count = tStoreIntegralRecordDao.getByUserIdType(integralRecord);
        if (count > 0) {
            throw new RRException("已经完成新手任务");
        }

        //查询该用户信息
        WxUserEntity wxUser = tStoreWxUserService.queryObjectByUserId(userId);
        Integer newTask = wxUser.getNewTask();
        if (newTask.intValue() == 1) {
            throw new RRException("已经完成新手任务");
        }
    }


    /**
     * 做新手任务得积分
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public R doNewbieTask(String userId) {
        //查询该用户信息
        WxUserEntity wxUser = tStoreWxUserService.queryObjectByUserId(userId);
        //获取新手任务得奖励
        IntegralSetting.NewbieTask newbieTaskRule = integralConfig.getIntegralSetting().getNewbieTask();
        Integer score = newbieTaskRule.getScore();
        //保存积分类型是：新手任务   的积分记录
        int newIntegral = 0;
        if(wxUser.getIntegral()!= null){
            newIntegral = wxUser.getIntegral() + score;
        }
        if (newIntegral > 2000) {
            throw new RRException("积分已上限");
        }
        TStoreIntegralRecordEntity integralRecord = new TStoreIntegralRecordEntity();
        integralRecord.setUserId(userId);
        integralRecord.setAppId(wxUser.getAppId());
        integralRecord.setIntegralType(2);//2:新手任务
        integralRecord.setIntegral(score);
        integralRecord.setOpenId(wxUser.getOpenId());
        integralRecord.setRemarks("新手任务");
        //保存以签到类型的积分记录
        tStoreIntegralRecordDao.save(integralRecord);
        //保存用户新手任务标识 0 变成 1
        WxUserEntity wxUserUp = new WxUserEntity();
        wxUserUp.setId(wxUser.getId());
        wxUserUp.setNewTask(1);
        wxUserUp.setIntegral(newIntegral);
        tStoreWxUserService.update(wxUserUp);
        return  R.ok();
    }



    @Override
    @Transactional
    public R cancelNewbieTask(String userId,String cancelFlag) {

        if (org.apache.commons.lang3.StringUtils.isBlank(userId)) {
            return R.error(1, "入参用户id获取异常！");
        }
       //取消标识；1：下次弹出  2：以后不弹出
        if (StrUtil.isBlank(cancelFlag)) {
            return R.error(2, "入参取消标识cancelFlag获取异常！");
        }


        int count = 0;//记录数追加值
        if ("1".equals(cancelFlag)) {
            count = 1;//1：下次弹出，并记录count+1
        } else if ("2".equals(cancelFlag)) {
            count = 4;// 2：以后不弹出，并记录count=4
        } else {
            return R.error(2, "入参取消标识cancelFlag获取异常！");
        }

        //查询该用户信息
        WxUserEntity wxUser = tStoreWxUserService.queryObjectByUserId(userId);
        Integer tipNum = wxUser.getTipNum();
        if (tipNum != null) {
            tipNum = tipNum+count;
        } else {
            tipNum = count;
        }
        if (tipNum >= 4) {
            tipNum = 4;
        }

        wxUser.setTipNum(tipNum);
        tStoreWxUserService.update(wxUser);
        return  R.ok();
    }

    /**
     * 检查该用户是否完成新手任务
     *
     * @param
     * @return
     */
    @Override
    public R checkNewbieTask(String userId) {
        //入参用户唯一标识id
        if (org.apache.commons.lang3.StringUtils.isBlank(userId)) {
            return R.error(3, "入参用户id获取异常！");
        }
        //查询该用户信息
        WxUserEntity wxUser = tStoreWxUserService.queryObjectByUserId(userId);
        Integer newTask = wxUser.getNewTask();
        if (newTask.intValue() == 1) {
            return R.error(1,"已经完成新手任务！");
        }

        Integer tipNum = wxUser.getTipNum();
        boolean tipFlag = false;
        if ( tipNum >= 3) {

        } else {
            tipFlag = true;
        }

        return R.ok("未完成新手任务！").put("tipFlag",tipFlag);
    }



    @Override
    public void save(TStoreIntegralRecordEntity tStoreIntegralRecord) {
        tStoreIntegralRecordDao.save(tStoreIntegralRecord);
    }

    @Override
    public void update(TStoreIntegralRecordEntity tStoreIntegralRecord) {
        tStoreIntegralRecordDao.update(tStoreIntegralRecord);
    }

    @Override
    public void delete(Long id) {
        tStoreIntegralRecordDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        tStoreIntegralRecordDao.deleteBatch(ids);
    }

}
