package com.wzlue.schedule.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class StorageWarningTask implements Job {
    static Log logger = LogFactory.getLog(StorageWarningTask.class);
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("Hello!  NewJob is executing."+new Date() );
        //取得job详情
        JobDetail jobDetail = context.getJobDetail();
        // 取得job名称
        String jobName = jobDetail.getClass().getName();
        logger.info("Name: " + jobDetail.getClass().getSimpleName());
        //取得job的类
        logger.info("Job Class: " + jobDetail.getJobClass());
        //取得job开始时间
        logger.info(jobName + " fired at " + context.getFireTime());
        logger.info("Next fire time " + context.getNextFireTime());
    }

}
