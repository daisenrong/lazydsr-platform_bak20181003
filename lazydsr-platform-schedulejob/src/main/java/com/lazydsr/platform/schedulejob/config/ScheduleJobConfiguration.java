package com.lazydsr.platform.schedulejob.config;


import com.lazydsr.platform.schedulejob.bean.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * QuartzConfigration
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.config.scheduleJob
 * Created by Lazy on 2018/5/14 01:44
 * Version: 0.1
 * Info: 定时任务配置类
 */
@Configuration
@EnableScheduling
@Slf4j
public class ScheduleJobConfiguration {

    //@Scheduled(cron = "0/1 * * * * ?")
    //private void configureTasks() {
    //    System.err.println("执行定时任务1: " + UtilDateTime.getCurrDateTime());
    //}
    //private static List<ScheduleJob> scheduleJobList = new ArrayList<>();

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    public void init(List<ScheduleJob> scheduleJobs) {

        //Scheduler scheduler = schedulerFactoryBean.getScheduler();

        // 这里从数据库中获取任务信息数据
        log.info("scheduleList" + scheduleJobs);
        for (ScheduleJob job : scheduleJobs) {
            addJob(job);
        }
    }

    /**
     * 添加任务
     *
     * @param job
     * @throws SchedulerException
     */
    public void addJob(ScheduleJob job) {
        try {
            if (job == null || !ScheduleJob.STATUS_RUNNING.equals(job.getJobstatus())) {
                log.error("scheduleJob定时任务不执行");
                return;
            }

            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getName(), job.getJobgroup());

            CronTrigger trigger = null;

            trigger = (CronTrigger) scheduler.getTrigger(triggerKey);


            // 不存在，创建一个
            if (null == trigger) {
                Class clazz = ScheduleJob.CONCURRENT_IS.equals(job.getConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;
                //Class clazz = QuartzJobFactory.class;

                JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getName(), job.getJobgroup()).build();

                jobDetail.getJobDataMap().put("scheduleJob", job);
                //jobDetail.getJobDataMap().put(job.getJobgroup() + "_" + job.getName(), job);

                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());

                trigger = TriggerBuilder.newTrigger().withIdentity(job.getName(), job.getJobgroup()).withSchedule(scheduleBuilder).build();

                scheduler.scheduleJob(jobDetail, trigger);

                log.info("添加定时任务：" + job.getJobgroup() + "_" + job.getName());
            } else {
                // Trigger已存在，那么更新相应的定时设置
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());

                // 按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

                // 按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            log.error("定时任务加载异常" + e.toString());
            e.printStackTrace();
        }
    }


    /**
     * 获取所有计划中的任务列表
     *
     * @return
     * @throws SchedulerException
     */
    public List<ScheduleJob> getAllJob() {
        List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = null;
            jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    ScheduleJob job = new ScheduleJob();
                    job.setName(jobKey.getName());
                    job.setJobgroup(jobKey.getGroup());
                    job.setDescription("触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setJobstatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCron(cronExpression);
                    }
                    jobList.add(job);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 所有正在运行的job
     *
     * @return
     * @throws SchedulerException
     */
    public List<ScheduleJob> getRunningJob() {
        List<ScheduleJob> jobList = null;
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            List<JobExecutionContext> executingJobs = null;
            executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<ScheduleJob>(executingJobs.size());

            for (JobExecutionContext executingJob : executingJobs) {
                ScheduleJob job = new ScheduleJob();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setName(jobKey.getName());
                job.setJobgroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobstatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCron(cronExpression);
                }
                jobList.add(job);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getJobgroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void resumeJob(ScheduleJob scheduleJob) {

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getJobgroup());
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(ScheduleJob scheduleJob) {

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getJobgroup());
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void runJobNow(ScheduleJob scheduleJob) {

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = JobKey.jobKey(scheduleJob.getName(), scheduleJob.getJobgroup());
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getName(), scheduleJob.getJobgroup());

        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron());

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        scheduler.rescheduleJob(triggerKey, trigger);
    }

    //@Scheduled(cron = "0/1 0 * * * ?")
    //public void cronJob() {
    //    //定时更新计划任务列表
    //    log.error("aaaaaaaaaaaaaa");
    //}

}
