package com.wzlue.web.common.config;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {
    Logger logger = Logger.getLogger(this.getClass());

    @Async("asyncPool")
    public void doTask(int i){
        try {
//            Thread.sleep(5*1000);
            logger.info("Task"+i+" started.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
