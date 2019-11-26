package com.wzlue.jobApplication.service.impl;

import ch.qos.logback.core.util.COWArrayList;
import ch.qos.logback.core.util.SystemInfo;
import com.wzlue.common.exception.RRException;
import com.wzlue.common.utils.StringUtils;
import com.wzlue.jobApplication.dao.BonusRecordDao;
import com.wzlue.jobApplication.dao.CMemberClockDao;
import com.wzlue.jobApplication.dao.FeeReturnRecordDao;
import com.wzlue.jobApplication.dao.JobApplicationDao;
import com.wzlue.jobApplication.entity.BonusRecordEntity;
import com.wzlue.jobApplication.entity.CMemberClockEntity;
import com.wzlue.jobApplication.entity.FeeReturnRecordEntity;
import com.wzlue.jobApplication.entity.JobApplicationEntity;
import com.wzlue.jobApplication.service.CMemberClockService;
import com.wzlue.recruitment.dao.FeeReturnDao;
import com.wzlue.recruitment.dao.ShopRecruitmentDao;
import com.wzlue.recruitment.entity.FeeReturnEntity;
import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import com.wzlue.wechat.dao.WxUserDao;
import com.wzlue.wechat.entity.WxUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("cMemberClockService")
public class CMemberClockServiceImpl implements CMemberClockService {
    @Autowired
    private CMemberClockDao cMemberClockDao;
    @Autowired
    private FeeReturnDao feeReturnDao;
    @Autowired
    private JobApplicationDao jobApplicationDao;
    @Autowired
    private FeeReturnRecordDao feeReturnRecordDao;
    @Autowired
    private ShopRecruitmentDao shopRecruitmentDao;
    @Autowired
    private BonusRecordDao bonusRecordDao;
    @Autowired
    private WxUserDao wxUserDao;


    @Override
    public CMemberClockEntity queryObject(Long id) {
        return cMemberClockDao.queryObject(id);
    }

    @Override
    public List<CMemberClockEntity> queryList(Map<String, Object> map) {
        return cMemberClockDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return cMemberClockDao.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(CMemberClockEntity cMemberClock) {
        cMemberClock.setClockTime(new Date());
        cMemberClockDao.save(cMemberClock);
    }

    /**
     * 后台补卡+返费
     *
     * @param cMemberClock
     */
    @Override
    @Transactional
    public void update(CMemberClockEntity cMemberClock) throws ParseException {
        //返费规则
        List<FeeReturnEntity> feeReturnEntities = feeReturnDao.queryByRecruitmentId(cMemberClock.getShopRecruitmentId());
        if (feeReturnEntities.size() <= 0) {
            return;//不存在返费，不补卡给
        }
        if (cMemberClock.getClockType() == 1) {
            cMemberClock.setClockType(1);
            cMemberClock.setClockTime(new Date());
            cMemberClock.setUpdateDate(new Date());

            //补卡的前后两天
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            String beforeDay = getPreDateByDate(formatter.format(cMemberClock.getCreateDate()));
            String nextDay = getNextDateByDate(formatter.format(cMemberClock.getCreateDate()));

            //前一天打卡记录
            CMemberClockEntity cMemberClock1 = new CMemberClockEntity();
            Calendar now = Calendar.getInstance();
            now.setTime(cMemberClock.getCreateDate());
            if (now.get(Calendar.DAY_OF_MONTH) == 1) {//补卡1号查上个月打卡表
                String tableName = beforeDay.substring(0, 4) + beforeDay.substring(5, 7);
                cMemberClock1.setTableName(tableName);
            } else {
                cMemberClock1.setTableName(cMemberClock.getTableName());
            }
            cMemberClock1.setCreateDate(formatter.parse(beforeDay, pos));
            cMemberClock1.setUserNo(cMemberClock.getUserNo());
            cMemberClock1 = cMemberClockDao.yesterdayClock(cMemberClock1);

            if (cMemberClock1 != null && cMemberClock1.getNumFlag() != null) {
                cMemberClock.setNumFlag(cMemberClock1.getNumFlag() + 1);//连续打卡+1
            } else {
                cMemberClock.setNumFlag(1);//第一天打卡
            }
            cMemberClockDao.update(cMemberClock);//补卡

            int continuClock = 0;//连续打卡天数
            //后一天打卡记录
            CMemberClockEntity cMemberClock2 = new CMemberClockEntity();
            Date dt = formatter.parse(nextDay);
            String next = String.format("%td", dt);
            if ("01".equals(next)) {//补卡月底查下个月打卡表
                String tableName = nextDay.substring(0, 4) + nextDay.substring(5, 7);
                cMemberClock2.setTableName(tableName);
            } else {
                cMemberClock2.setTableName(cMemberClock.getTableName());
            }
            cMemberClock2.setCreateDate(formatter.parse(nextDay));
            cMemberClock2.setUserNo(cMemberClock.getUserNo());
            cMemberClock2 = cMemberClockDao.yesterdayClock(cMemberClock2);

            //在职用户的全部打卡记录
            List<CMemberClockEntity> cMemberClocksAll = new ArrayList<>();
            String[] tabs = cMemberClockDao.cMemBerClockTables();//多张表名
            for (int i = 0; i < tabs.length; i++) {
                String tName = tabs[i].substring(15, 21); //表名
                Map<String, Object> params = new HashMap<>();
                params.put("time", tName);
                params.put("userNo", cMemberClock.getUserNo());
                List<CMemberClockEntity> cMemberClocks = cMemberClockDao.queryMemberClock2(params);
                cMemberClocksAll.addAll(cMemberClocks);
            }

            //判断补卡前后两天打卡状态
            if (cMemberClock1 == null) {
                if (cMemberClock2 == null) {
                    continuClock = cMemberClock.getNumFlag();
                } else {
                    if (cMemberClock2.getClockType() == 1) {//后一天正常
                        //自补卡日重新排序,最大的numFlag为连续打卡的最大天数
                        for (CMemberClockEntity cm : cMemberClocksAll) {
                            if (cm.getCreateDate().getTime() > cMemberClock.getCreateDate().getTime()) {//cm创建时间>补卡当天的创建时间
                                //年月--表名
                                String year = String.format("%tY", cm.getCreateDate());
                                String mon = String.format("%tm", cm.getCreateDate());

                                //连续打卡前一天cm3
                                String before = getPreDateByDate(formatter.format(cm.getCreateDate()));
                                CMemberClockEntity cm3 = new CMemberClockEntity();
                                String cmBefore = String.format("%td", cm.getCreateDate());
                                if ("01".equals(cmBefore)) {//当天1号查上个月打卡表
                                    String tableName = before.substring(0, 4) + before.substring(5, 7);
                                    cm3.setTableName(tableName);
                                } else {
                                    cm3.setTableName(year + mon);
                                }
                                cm3.setCreateDate(formatter.parse(before));
                                cm3.setUserNo(cMemberClock.getUserNo());
                                cm3 = cMemberClockDao.yesterdayClock(cm3);
                                if (cm.getClockType() == 1) {//打卡
                                    cm.setTableName(year + mon);
                                    if (cm3.getId().longValue() == cMemberClock.getId().longValue()) {//前一天就是补卡日
                                        cm.setNumFlag(cMemberClock.getNumFlag() + 1);
                                    } else {
                                        cm.setNumFlag(cm3.getNumFlag() + 1);
                                    }
                                    cMemberClockDao.update(cm);
                                } else {
                                    if (cm3.getId().longValue() == cMemberClock.getId().longValue()) {//前一天就是补卡日
                                        continuClock = cMemberClock.getNumFlag();
                                    } else {
                                        continuClock = cm3.getNumFlag();
                                    }
                                    break;
                                }
                            }
                        }
                    } else {//后一天缺卡，补卡当天的numFlag为连续打卡天数
                        continuClock = cMemberClock.getNumFlag();
                    }
                }
            } else {
                if (cMemberClock2 == null) {
                    continuClock = cMemberClock.getNumFlag();
                } else {
                    if (cMemberClock2.getClockType() == 1) {//补卡日期前后两天都已打卡
                        //自补卡日重新排序,最大的numFlag为连续打卡的最大天数
                        for (CMemberClockEntity cm : cMemberClocksAll) {
                            if (cm.getCreateDate().getTime() > cMemberClock.getCreateDate().getTime()) {
                                //年月--表名
                                String year = String.format("%tY", cm.getCreateDate());
                                String mon = String.format("%tm", cm.getCreateDate());

                                //连续打卡前一天cm3
                                String before = getPreDateByDate(formatter.format(cm.getCreateDate()));
                                CMemberClockEntity cm3 = new CMemberClockEntity();
                                String cmBefore = String.format("%td", cm.getCreateDate());
                                if ("01".equals(cmBefore)) {//当天1号查上个月打卡表
                                    String tableName = before.substring(0, 4) + before.substring(5, 7);
                                    cm3.setTableName(tableName);
                                } else {
                                    cm3.setTableName(year + mon);
                                }
                                cm3.setCreateDate(formatter.parse(before));
                                cm3.setUserNo(cMemberClock.getUserNo());
                                cm3 = cMemberClockDao.yesterdayClock(cm3);
                                if (cm.getClockType() == 1) {//打卡
                                    cm.setTableName(year + mon);
                                    if (cm3.getId().longValue() == cMemberClock.getId().longValue()) {//前一天就是补卡日
                                        cm.setNumFlag(cMemberClock.getNumFlag() + 1);
                                    } else {
                                        cm.setNumFlag(cm3.getNumFlag() + 1);
                                    }
                                    cMemberClockDao.update(cm);
                                } else {
                                    if (cm3.getId().longValue() == cMemberClock.getId().longValue()) {//前一天就是补卡日
                                        continuClock = cMemberClock.getNumFlag();
                                    } else {
                                        continuClock = cm3.getNumFlag();
                                    }
                                    break;
                                }
                            }
                        }
                    } else {//后一天缺卡，补卡当天的numFlag为连续打卡天数
                        continuClock = cMemberClock.getNumFlag();
                    }
                }
            }

            if (continuClock > 0) {
                //-----------------------------------------补卡返费--------------------------------------
                //入职用户
                JobApplicationEntity jobApp = jobApplicationDao.queryObject(cMemberClock.getUserNo());


                if (jobApp == null
                        || jobApp.getShopRecruitmentEntity() == null
                        || jobApp.getShopRecruitmentEntity().getFeeReturnType() == null
                        || (jobApp.getShopRecruitmentEntity().getFeeReturnType() != 1 && jobApp.getShopRecruitmentEntity().getFeeReturnType() != 2)) {
                    throw new RRException("入职员工招聘信息异常");
                }
                Integer type = jobApp.getShopRecruitmentEntity().getFeeReturnType();


                if (type == 1) {
                    for (FeeReturnEntity feeReturn : feeReturnEntities) {
                        int day = feeReturn.getDays();//返费天数
                        BigDecimal reward = feeReturn.getReward();//奖励金额
                        if (day <= continuClock) {//规则天数在连续打卡天数范围内
                            int count = feeReturnRecordDao.feeIsExist(jobApp.getId(), feeReturn.getId());//已经返费的规则
                            if (count < 1) {//返费记录不存在
                                //返费+记录
                                if (jobApp.getReward() == null) {
                                    jobApp.setReward(reward);
                                } else {
                                    BigDecimal result1 = jobApp.getReward().add(reward);//余额+奖励
                                    jobApp.setReward(result1);
                                }
                                //修改金额
                                jobApplicationDao.updateBalance(jobApp);
                                //添加返费记录
                                FeeReturnRecordEntity feeReturnRecord = new FeeReturnRecordEntity();
                                feeReturnRecord.setJobId(cMemberClock.getUserNo());
                                feeReturnRecord.setFeeId(feeReturn.getId());//返费规则id
                                feeReturnRecord.setAmount(reward);
                                feeReturnRecord.setRemark("补卡：连续打卡" + day + "天,返费" + reward + "元");
                                feeReturnRecord.setType(1);
                                feeReturnRecord.setAppId(jobApp.getAppId());
                                feeReturnRecord.setDelFlag(2);
                                feeReturnRecord.setCreateDate(new Date());
                                feeReturnRecordDao.save(feeReturnRecord);
                            }
                        }
                    }
                } else if (type == 2) {
                    if (jobApp.getShopRecruitmentEntity().getWorkHours() == null) {
                        throw new RRException("入职员工招聘信息异常");
                    }

                    if (feeReturnEntities != null && !feeReturnEntities.isEmpty()) {
                        Integer workHours = jobApp.getShopRecruitmentEntity().getWorkHours();
                        FeeReturnEntity feeReturnEntity = feeReturnEntities.get(0);
                        Integer days = feeReturnEntity.getDays();
                        BigDecimal reward = feeReturnEntity.getReward();
                        String numString = String.valueOf(workHours / days);
                        BigDecimal multiply = reward.multiply(new BigDecimal(numString));


                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String createDate = dateFormat.format(cMemberClock.getCreateDate());

                        int i = feeReturnRecordDao.feeIsExistOfToday(jobApp.getId(), createDate);
                        if (i < 1) {
                            if (jobApp.getReward() == null) {
                                jobApp.setReward(multiply);
                            } else {
                                BigDecimal result1 = jobApp.getReward().add(multiply);//余额+奖励
                                jobApp.setReward(result1);
                            }


                            //修改金额
                            jobApplicationDao.updateBalance(jobApp);
                            //添加返费记录
                            FeeReturnRecordEntity feeReturnRecord = new FeeReturnRecordEntity();
                            feeReturnRecord.setJobId(cMemberClock.getUserNo());
                            feeReturnRecord.setFeeId(feeReturnEntity.getId());//返费规则id
                            feeReturnRecord.setAmount(multiply);
                            feeReturnRecord.setRemark("补卡：打卡1天,返费" + multiply + "元");
                            feeReturnRecord.setType(1);
                            feeReturnRecord.setAppId(jobApp.getAppId());
                            feeReturnRecord.setDelFlag(2);
                            feeReturnRecord.setCreateDate(new Date());
                            feeReturnRecordDao.save(feeReturnRecord);
                        }


                    }
                }



                //推荐入职 满足奖励条件（推荐入职天数） 推荐人获得奖励金
                //最好在推荐发生时 走个接口 设置 该门店招聘的奖励金不能再修改标识
                ShopRecruitmentEntity shopRecruitment = shopRecruitmentDao.queryObject(cMemberClock.getShopRecruitmentId());
                if (StringUtils.isNotEmpty(jobApp.getRecommenderOpenid()) && null != shopRecruitment && null != shopRecruitment.getBonus() && null != shopRecruitment.getEntryDays()) {

                    if (continuClock == shopRecruitment.getEntryDays()) {//满足入职天数
                        BonusRecordEntity bonusRecord = new BonusRecordEntity();
                        bonusRecord.setOpenid(jobApp.getRecommenderOpenid());//推荐人openid
                        bonusRecord.setJobApplicationId(jobApp.getId());
                        bonusRecord.setAmount(shopRecruitment.getBonus());
                        bonusRecord.setType(1);
                        bonusRecord.setRemark("推荐奖励金");
                        bonusRecord.setAppId(shopRecruitment.getAppId());
                        bonusRecordDao.save(bonusRecord);

                        WxUserEntity wxUserEntity = wxUserDao.queryObject(jobApp.getRecommenderOpenid());
                        BigDecimal bonus = wxUserEntity.getBonus();
                        if (null == bonus) {
                            bonus = BigDecimal.ZERO;
                        }
                        if (null != wxUserEntity) {
                            WxUserEntity wxUser = new WxUserEntity();
                            wxUser.setId(jobApp.getRecommenderOpenid());
                            wxUser.setBonus(bonus.add(shopRecruitment.getBonus()));
                            wxUserDao.update(wxUser);
                        }
                    }

                }


            }
        }
    }

    /**
     * @Author：
     * @Description：更加输入日期，获取输入日期的前一天
     * @Date：
     * @strData：参数格式：yyyy-MM-dd
     * @return：返回格式：yyyy-MM-dd
     */
    private static String getPreDateByDate(String strData) {
        String preDate = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(strData);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        c.setTime(date);
        int day1 = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day1 - 1);
        preDate = sdf.format(c.getTime());
        return preDate;
    }

    /**
     * @Author：
     * @Description：更加输入日期，获取输入日期的后一天
     * @Date：
     * @strData：参数格式：yyyy-MM-dd
     * @return：返回格式：yyyy-MM-dd
     */
    private static String getNextDateByDate(String strData) {
        String preDate = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(strData);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        c.setTime(date);
        int day1 = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day1 + 1);
        preDate = sdf.format(c.getTime());
        return preDate;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cMemberClockDao.delete(id);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] ids, String tableName) {
        Map<String, Object> map = new HashMap<>();
        map.put("ids", ids);
        map.put("delFlag", 1);
        map.put("tableName", tableName);
        //软删除
        cMemberClockDao.updateAllFlag(map);
        //cMemberClockDao.deleteBatch(ids);
    }

    @Override
    @Transactional(readOnly = false)
    public void createTablesByYearMonth(String date) {
        cMemberClockDao.createTablesByYearMonth(date);
    }

    @Override
    public int isExistTable(String tableSchema, String tableName) {
        return cMemberClockDao.isExistTable(tableSchema, tableName);
    }

    @Override
    public List<CMemberClockEntity> queryMemberClock(Map<String, Object> map) {
        return cMemberClockDao.queryMemberClock(map);
    }

    @Override
    public int queryMemberClockTotal(Map<String, Object> map) {
        return cMemberClockDao.queryMemberClockTotal(map);
    }

    @Override
    public void saveList(CMemberClockEntity cMemberClockEntity) {
        cMemberClockDao.saveList(cMemberClockEntity);
    }

    @Override
    public CMemberClockEntity detail(String time, Long id) {
        return cMemberClockDao.detail(time, id);
    }

    @Override
    public CMemberClockEntity apiDetail(String time, Long userNo, String createDate) {
        return cMemberClockDao.apiDetail(time, userNo, createDate);
    }

    /**
     * api打卡
     *
     * @param cMemberClock
     */
    @Override
    @Transactional
    public void updateApi(CMemberClockEntity cMemberClock) {
        cMemberClock.setClockType(1);
        cMemberClock.setRemark("api打卡");
        cMemberClock.setClockTime(new Date());
        cMemberClock.setUpdateDate(new Date());
        //获取打卡的前一天(实际)
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String beforeDay = getPreDateByDate(formatter.format(cMemberClock.getCreateDate()));
        ParsePosition pos = new ParsePosition(0);
        Calendar now = Calendar.getInstance();
        now.setTime(cMemberClock.getCreateDate());
        //前一天打卡记录
        CMemberClockEntity cMemberClock1 = new CMemberClockEntity();
        if (now.get(Calendar.DAY_OF_MONTH) == 1) {
            String tableName = beforeDay.substring(0, 4) + beforeDay.substring(5, 7);
            cMemberClock1.setTableName(tableName);
        } else {
            cMemberClock1.setTableName(cMemberClock.getTableName());
        }
        cMemberClock1.setCreateDate(formatter.parse(beforeDay, pos));
        cMemberClock1.setUserNo(cMemberClock.getUserNo());
        cMemberClock1 = cMemberClockDao.yesterdayClock(cMemberClock1);
        if (cMemberClock1 != null && cMemberClock1.getNumFlag() != null) {
            cMemberClock.setNumFlag(cMemberClock1.getNumFlag() + 1);//连续打卡+1
        } else {
            cMemberClock.setNumFlag(1);//第一天打卡
        }
        cMemberClockDao.update(cMemberClock);
        //入职用户
        JobApplicationEntity jobApp = jobApplicationDao.queryObject(cMemberClock.getUserNo());
        //返费规则
        List<FeeReturnEntity> feeReturnEntities = feeReturnDao.queryByRecruitmentId(cMemberClock.getShopRecruitmentId());

        JobApplicationEntity jobApplicationEntity = jobApplicationDao.queryObject(cMemberClock.getUserNo());
        if (jobApplicationEntity == null
                || jobApplicationEntity.getShopRecruitmentEntity() == null
                || jobApplicationEntity.getShopRecruitmentEntity().getFeeReturnType() == null
                || (jobApplicationEntity.getShopRecruitmentEntity().getFeeReturnType() != 1 && jobApplicationEntity.getShopRecruitmentEntity().getFeeReturnType() != 2)) {
            throw new RRException("入职员工招聘信息异常");
        }
        Integer type = jobApplicationEntity.getShopRecruitmentEntity().getFeeReturnType();


        if (type == 1) {
            if (feeReturnEntities != null && !feeReturnEntities.isEmpty()) {
                //最大等级返费规则天数
                int maxDay = feeReturnDao.queryMaxDay(cMemberClock.getShopRecruitmentId());
                //记录天数小等于最大等级天数
                if (cMemberClock.getNumFlag() <= maxDay) {
                    for (FeeReturnEntity feeReturn : feeReturnEntities) {
                        Integer day = feeReturn.getDays();//返费满足天数
                        BigDecimal reward = feeReturn.getReward();//奖励金额
                        int count = feeReturnRecordDao.feeIsExist(jobApp.getId(), feeReturn.getId());//已经返费的规则
                        if (count < 1) {//返费记录不存在
                            if (day == cMemberClock.getNumFlag()) {//满足连续打卡
                                if (jobApp.getReward() == null) {
                                    jobApp.setReward(reward);
                                } else {
                                    BigDecimal result1 = jobApp.getReward().add(reward);//余额+奖励
                                    jobApp.setReward(result1);
                                }
                                //修改金额
                                jobApplicationDao.updateBalance(jobApp);
                                //添加返费记录
                                FeeReturnRecordEntity feeReturnRecord = new FeeReturnRecordEntity();
                                feeReturnRecord.setJobId(cMemberClock.getUserNo());
                                feeReturnRecord.setFeeId(feeReturn.getId());//返费规则id
                                feeReturnRecord.setAmount(reward);
                                feeReturnRecord.setRemark("连续打卡" + day + "天,返费" + reward + "元");
                                feeReturnRecord.setType(1);
                                feeReturnRecord.setAppId(jobApp.getAppId());
                                feeReturnRecord.setDelFlag(2);
                                feeReturnRecord.setCreateDate(new Date());
                                feeReturnRecordDao.save(feeReturnRecord);
                            }
                        }
                    }
                }
            }
        } else if (type == 2) {

            if (jobApplicationEntity.getShopRecruitmentEntity().getWorkHours() == null) {
                throw new RRException("入职员工招聘信息异常");
            }

            if (feeReturnEntities != null && !feeReturnEntities.isEmpty()) {
                Integer workHours = jobApplicationEntity.getShopRecruitmentEntity().getWorkHours();
                FeeReturnEntity feeReturnEntity = feeReturnEntities.get(0);
                Integer days = feeReturnEntity.getDays();
                BigDecimal reward = feeReturnEntity.getReward();
                String numString = String.valueOf(workHours / days);
                BigDecimal multiply = reward.multiply(new BigDecimal(numString));


                //服务器当前日期
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String createDate = dateFormat.format(date);

                int i = feeReturnRecordDao.feeIsExistOfToday(jobApp.getId(), createDate);
                if (i < 1) {
                    if (jobApp.getReward() == null) {
                        jobApp.setReward(multiply);
                    } else {
                        BigDecimal result1 = jobApp.getReward().add(multiply);//余额+奖励
                        jobApp.setReward(result1);
                    }


                    //修改金额
                    jobApplicationDao.updateBalance(jobApp);
                    //添加返费记录
                    FeeReturnRecordEntity feeReturnRecord = new FeeReturnRecordEntity();
                    feeReturnRecord.setJobId(cMemberClock.getUserNo());
                    feeReturnRecord.setFeeId(feeReturnEntity.getId());//返费规则id
                    feeReturnRecord.setAmount(multiply);
                    feeReturnRecord.setRemark("打卡1天,返费" + multiply + "元");
                    feeReturnRecord.setType(1);
                    feeReturnRecord.setAppId(jobApp.getAppId());
                    feeReturnRecord.setDelFlag(2);
                    feeReturnRecord.setCreateDate(new Date());
                    feeReturnRecordDao.save(feeReturnRecord);
                }


            }
        }

        //推荐入职 满足奖励条件（推荐入职天数） 推荐人获得奖励金
        //最好在推荐发生时 走个接口 设置 该门店招聘的奖励金不能再修改标识
        ShopRecruitmentEntity shopRecruitment = shopRecruitmentDao.queryObject(cMemberClock.getShopRecruitmentId());
        if (StringUtils.isNotEmpty(jobApp.getRecommenderOpenid()) && null != shopRecruitment && null != shopRecruitment.getBonus() && null != shopRecruitment.getEntryDays()) {

            if (cMemberClock.getNumFlag() == shopRecruitment.getEntryDays()) {//满足入职天数
                BonusRecordEntity bonusRecord = new BonusRecordEntity();
                bonusRecord.setOpenid(jobApp.getRecommenderOpenid());//推荐人openid
                bonusRecord.setJobApplicationId(jobApp.getId());
                bonusRecord.setAmount(shopRecruitment.getBonus());
                bonusRecord.setType(1);
                bonusRecord.setRemark("推荐奖励金");
                bonusRecord.setAppId(shopRecruitment.getAppId());
                bonusRecordDao.save(bonusRecord);

                WxUserEntity wxUserEntity = wxUserDao.queryObject(jobApp.getRecommenderOpenid());
                BigDecimal bonus = wxUserEntity.getBonus();
                if (null == bonus) {
                    bonus = BigDecimal.ZERO;
                }
                if (null != wxUserEntity) {
                    WxUserEntity wxUser = new WxUserEntity();
                    wxUser.setId(jobApp.getRecommenderOpenid());
                    wxUser.setBonus(bonus.add(shopRecruitment.getBonus()));
                    wxUserDao.update(wxUser);
                }
            }


        }


    }

    @Override
    public CMemberClockEntity apiDayClock(Map<String, Object> map) {
        return cMemberClockDao.apiDayClock(map);
    }

}
