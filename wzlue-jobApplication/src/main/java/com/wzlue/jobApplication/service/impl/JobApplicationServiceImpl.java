package com.wzlue.jobApplication.service.impl;

import cn.hutool.core.util.StrUtil;
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
import com.wzlue.jobApplication.service.JobApplicationService;
import com.wzlue.recruitment.dao.ShopRecruitmentDao;
import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import com.wzlue.wechat.dao.WxUserDao;
import com.wzlue.wechat.entity.WxUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("jobApplicationService")
public class JobApplicationServiceImpl implements JobApplicationService {
    @Autowired
    private JobApplicationDao jobApplicationDao;
    @Autowired
    private FeeReturnRecordDao feeReturnRecordDao;
    @Autowired
    private CMemberClockDao cMemberClockDao;
    @Autowired
    private ShopRecruitmentDao shopRecruitmentDao;


    @Override
    public JobApplicationEntity queryObject(Long id) {
        return jobApplicationDao.queryObject(id);
    }

    @Override
    public List<JobApplicationEntity> queryList(Map<String, Object> map) {
        return jobApplicationDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return jobApplicationDao.queryTotal(map);
    }

    @Override
    public void save(JobApplicationEntity jobApplication) {
        jobApplication.setDistributionStatus(0);
        jobApplicationDao.save(jobApplication);
    }

    @Override
    public void update(JobApplicationEntity jobApplication) {
        jobApplicationDao.update(jobApplication);
    }

    @Override
    public void delete(Long id) {
        jobApplicationDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        jobApplicationDao.deleteBatch(ids);
    }

    @Override
    public List<JobApplicationEntity> queryEntryList(Map<String, Object> map) {
        return jobApplicationDao.queryEntryList(map);
    }

    @Override
    public int queryEntryTotal(Map<String, Object> map) {
        return jobApplicationDao.queryEntryTotal(map);
    }

    @Override
    @Transactional
    public void feedback(JobApplicationEntity jobApplication) {
        //判断修改前的招聘状态
        JobApplicationEntity job = jobApplicationDao.queryObject(jobApplication.getId());
        if (job.getStateFeedback() == 4) {//已在职
            if (null != jobApplication.getStateFeedback() && jobApplication.getStateFeedback() == 4) {
                jobApplication.setHiredate(new Date());
            }
            jobApplicationDao.update(jobApplication);
        } else {//未入职
            if (null != jobApplication.getStateFeedback() && jobApplication.getStateFeedback() == 4) {
                jobApplication.setHiredate(new Date());

                //招聘的入职标识
                ShopRecruitmentEntity shopRecruitment = shopRecruitmentDao.queryObject(job.getShopRecruitmentId());
                if (null != shopRecruitment && null == shopRecruitment.getEntryFlag()) {
                    ShopRecruitmentEntity recruitment = new ShopRecruitmentEntity();
                    recruitment.setId(shopRecruitment.getId());
                    recruitment.setEntryFlag(1);
                    shopRecruitmentDao.update(recruitment);
                }

            }
            jobApplicationDao.update(jobApplication);

            //入职后-----生成当月包含今天之后的打卡记录
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            //当天日期(开始日期)
            String start = format.format(new Date());
            //获得实体类
            Calendar ca = Calendar.getInstance();
            //设置本月最后一天
            ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));

            //判断打卡表是否已经存在该员工打卡记录
            Map<String, Object> map = new HashMap<>();
            map.put("userNo", job.getId());
            map.put("time", start.substring(0, 6));
            int isEx = cMemberClockDao.queryMemberClockTotal(map);
            if (isEx == 0) {
                //获取今日到月底的日期
                List<Date> dateList = getBetweenDates(new Date(), ca.getTime());
                for (Date dateS : dateList) {
                    //查询是否存在当天的打卡记录
                    Map<String, Object> maps = new HashMap<>();
                    maps.put("tableName", start.substring(0, 6));
                    maps.put("createDate", dateS);
                    maps.put("userNo", job.getId());
                    CMemberClockEntity cMemberClock = cMemberClockDao.apiDayClock(maps);
                    if (cMemberClock == null) {
                        //生成打卡记录
                        CMemberClockEntity cMemberClockEntity = new CMemberClockEntity();
                        cMemberClockEntity.setOpenId(job.getOpenid());
                        cMemberClockEntity.setUserNo(job.getId());
                        cMemberClockEntity.setShopRecruitmentId(job.getShopRecruitmentId());
                        cMemberClockEntity.setClockType(2);//缺卡状态
                        cMemberClockEntity.setCreateDate(dateS);//创建时间
                        cMemberClockEntity.setTableName(start.substring(0, 6));
                        cMemberClockEntity.setAppId(job.getAppId());
                        //批量
                        cMemberClockDao.saveList(cMemberClockEntity);
                    }
                }
            }
        }
    }


    @Override
    @Transactional
    public void distribution(JobApplicationEntity jobApplication) {
        Long id = jobApplication.getId();
        String appId = jobApplication.getAppId();
        if (id == null) {
            throw new RRException("ID参数获取异常");
        }
        if (StrUtil.isBlank(appId)) {
            throw new RRException("appId不能为空");
        }
        JobApplicationEntity jobApplicationEntity = jobApplicationDao.queryObject(id);
        if (jobApplicationEntity == null) {
            throw new RRException("待分配招聘用户不能为空");
        }
        Integer status = jobApplicationEntity.getDistributionStatus();

        if (status == 0) {
            //改变门口待分配招聘用户状态为1：已分配
            jobApplicationEntity.setDistributionStatus(1);
            jobApplicationEntity.setLatestAppId(appId);
            jobApplicationDao.update(jobApplicationEntity);

            //新增一条用户招聘信息给相应的门店
            jobApplicationEntity.setDistributionStatus(1);
            jobApplicationEntity.setBelongTo(1);
            jobApplicationEntity.setAppId(appId);
            jobApplicationEntity.setLatestAppId(null);
            jobApplicationEntity.setPlatformJobId(jobApplicationEntity.getId());
            jobApplicationDao.save(jobApplicationEntity);
        }


    }


    @Override
    public List<JobApplicationEntity> queryListByPlatform(Map<String, Object> map) {
        return jobApplicationDao.queryListByPlatform(map);
    }

    @Override
    public int queryTotalByPlatform(Map<String, Object> map) {
        return jobApplicationDao.queryTotalByPlatform(map);
    }

    @Override
    public int fourEntry(Map<String, Object> map) {
        return jobApplicationDao.fourEntry(map);
    }

    /**
     * 获取两个日期间的时间包含开始结束
     *
     * @param start
     * @param end
     * @return
     */
    private static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        result.add(start);
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        result.add(end);
        return result;
    }

    @Override
    public void updateBalance(JobApplicationEntity jobApplicationEntity, BigDecimal amount) {
        BigDecimal result = jobApplicationEntity.getReward().subtract(amount);
        jobApplicationEntity.setReward(result);
        jobApplicationDao.updateBalance(jobApplicationEntity);
        //添加提现记录
        FeeReturnRecordEntity feeReturnRecord = new FeeReturnRecordEntity();
        feeReturnRecord.setJobId(jobApplicationEntity.getId());
        feeReturnRecord.setAmount(amount);//提现金额
        feeReturnRecord.setRemark("返费提现");
        feeReturnRecord.setType(2);
        feeReturnRecord.setAppId(jobApplicationEntity.getAppId());
        feeReturnRecord.setCreateDate(new Date());
        feeReturnRecordDao.save(feeReturnRecord);
    }

    @Override
    public BigDecimal rewardAll(String openid) {
        return jobApplicationDao.rewardAll(openid);
    }

    @Override
    public int isEnroll(Map<String, Object> map) {
        return jobApplicationDao.isEnroll(map);
    }


    @Override
    public List<JobApplicationEntity> queryListByIds(Object[] ids) {
        return jobApplicationDao.queryListByIds(ids);
    }

    @Override
    public List<JobApplicationEntity> queryListAll(Map<String, Object> map) {
        return jobApplicationDao.queryListAll(map);
    }
}
