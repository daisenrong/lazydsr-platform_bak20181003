package com.lazydsr.platform.vo;

import lombok.extern.slf4j.Slf4j;

/**
 * ScheduleTest
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.vo
 * Created by Lazy on 2018/6/2 23:15
 * Version: 0.1
 * Info: @TODO:...
 */
@Slf4j
public class ScheduleTest {
    public void runschedule(){
      log.info("runschedule");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
