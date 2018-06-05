package com.lazydsr.platform.config.schedule;

import com.lazydsr.platform.entity.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * QuartzJobFactory
 * PROJECT_NAME: lazydsr-web-template
 * PACKAGE_NAME: com.lazydsr.platform.config.scheduleJob
 * Created by Lazy on 2018/5/16 14:47
 * Version: 0.1
 * Info: @TODO:...
 */
@Slf4j
public class QuartzJobFactory implements Job {
    //public final Logger log = Logger.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get("scheduleJob");
        ScheduleJobUtils.invokMethod(scheduleJob);
    }
}
