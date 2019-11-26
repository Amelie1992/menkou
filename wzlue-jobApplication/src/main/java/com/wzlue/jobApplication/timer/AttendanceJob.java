package com.wzlue.jobApplication.timer;

import com.wzlue.jobApplication.entity.CMemberClockEntity;
import com.wzlue.jobApplication.entity.JobApplicationEntity;
import com.wzlue.jobApplication.service.CMemberClockService;
import com.wzlue.jobApplication.service.JobApplicationService;
import com.wzlue.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@EnableScheduling
public class AttendanceJob {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${table_schema}")
    private String tableSchema;//数据库名

    @Autowired
    private CMemberClockService cMemberClockService;
    @Autowired
    private JobApplicationService jobApplicationService;

    //创建打卡表，生成每个入职员工当月每天记录
    @Scheduled(cron = "0 0 0 1 * ?")//每月1日上午零点零分执行
    //@Scheduled(cron="0 0 1 1 * ?")//每月1日1时运行生成本月
    //@Scheduled(cron="0 0/1 * * * ? ")//测试每1分钟执行
    public void startLearnJob() {
        System.out.println("================生成考勤表======================");
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month = cal.get(Calendar.MONTH) + 1;//获取当月月份+1,+2下个月
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, 1);
        cal.roll(Calendar.DATE, -1);
        int maxDate = cal.get(Calendar.DATE);//获取当月天数
        String monthStr = String.valueOf(month);
        if (monthStr.length() <= 1) {
            monthStr = new String("0") + monthStr;
        }
        System.out.println("------------[调度器]当前年月为：" + year + "年" + monthStr + "月" + maxDate + "天----------------------------");
        String tableName = year + monthStr;//eg:201908

        //获取系统当月的最后一天日期
        Date end = cal.getTime();

        // 获取系统当月的第一天日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 0);
        Date start = calendar.getTime();

        //获取一号到月底日期
        List<Date> dateList = getBetweenDates(start, end);
        System.out.println("去重前的长度：" + dateList.size());

        logger.info("日期是否重复",dateList);

        //集合 去重
        HashSet h = new HashSet(dateList);
        dateList.clear();
        dateList.addAll(h);
        System.out.println("去重后的长度：" + dateList.size());

        //重新排序
        Collections.sort(dateList, new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });


        //库+表名查看是否存在
        int count = cMemberClockService.isExistTable(tableSchema, "c_member_clock_" + tableName);
        if (count == 0) {
            //新建表
            cMemberClockService.createTablesByYearMonth(tableName);
            //查询已入职用户
            Map<String, Object> map = new HashMap<>();
            map.put("stateFeedback", 4);
            List<JobApplicationEntity> jobApplicationEntities = jobApplicationService.queryList(map);
            for (JobApplicationEntity jobUser : jobApplicationEntities) {

                //判断打卡表是否已经存在该员工打卡记录
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("userNo", jobUser.getId());
                map2.put("time", tableName);
                int isEx = cMemberClockService.queryMemberClockTotal(map2);
                if (isEx == 0) {

                    for (Date dateS : dateList) {
                        //打卡记录
                        CMemberClockEntity cMemberClockEntity = new CMemberClockEntity();
                        cMemberClockEntity.setOpenId(jobUser.getOpenid());
                        cMemberClockEntity.setUserNo(jobUser.getId());
                        cMemberClockEntity.setShopRecruitmentId(jobUser.getShopRecruitmentId());
                        cMemberClockEntity.setClockType(2);//缺卡状态
                        cMemberClockEntity.setCreateDate(dateS);//创建时间
                        cMemberClockEntity.setTableName(tableName);
                        cMemberClockEntity.setAppId(jobUser.getAppId());
                        //向新建的表插入数据
                        cMemberClockService.saveList(cMemberClockEntity);
                    }
                }
            }
        }

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

}
