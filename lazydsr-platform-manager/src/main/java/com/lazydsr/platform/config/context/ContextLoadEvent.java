package com.lazydsr.platform.config.context;

import com.lazydsr.platform.config.datasource.DynamicDataSourceConfiguration;
import com.lazydsr.platform.config.system.SystemInfoConfiguration;
import com.lazydsr.platform.entity.ScheduleJob;
import com.lazydsr.platform.schedulejob.config.ScheduleJobConfiguration;
import com.lazydsr.platform.service.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ContextLoadEvent
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.config.content
 * Created by Lazy on 2018/3/8 20:36
 * Version: 0.1
 * Info: 容器加载事件
 */
@Component
@Slf4j
public class ContextLoadEvent implements ApplicationRunner {

    @Autowired
    private ScheduleJobConfiguration scheduleJobConfiguration;
    @Autowired
    private ScheduleJobService  scheduleJobService;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on info
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("多数据源加载开始");
        try {
            DynamicDataSourceConfiguration.getInstance().init();
        } catch (Exception e) {
            log.error("多数据源加载异常，请检查," + e);
            e.printStackTrace();
        }
        log.info("多数据源加载结束");


        log.info("加载系统基本信息开始");
        try {
            //该行代码报错的话，请检查一下系统中的库是不是存在
            SystemInfoConfiguration.getInstance().init();
        } catch (Exception e) {
            log.error("加载系统基本信息异常，" + e);
            e.printStackTrace();
        }
        log.info("加载系统基本信息结束");


        log.info("加载定时任务开始");
        try {
            List<ScheduleJob> scheduleJobs = scheduleJobService.findAllNormal();
            if (!CollectionUtils.isEmpty(scheduleJobs)){
                List<com.lazydsr.platform.schedulejob.bean.ScheduleJob> jobs=new ArrayList<>();
                for (ScheduleJob scheduleJob:scheduleJobs){
                    com.lazydsr.platform.schedulejob.bean.ScheduleJob job = new com.lazydsr.platform.schedulejob.bean.ScheduleJob();
                    BeanUtils.copyProperties(scheduleJob,job);
                    jobs.add(job);
                }
                scheduleJobConfiguration.init(jobs);
            }
        } catch (Exception e) {
            log.error("加载定时任务异常，" + e);
            e.printStackTrace();
        }
        log.info("加载定时任务结束");


        log.info("初始化常用缓存信息开始");
        try {
        } catch (Exception e) {
            log.error("初始化常用缓存信息异常，" + e);
            e.printStackTrace();
        }
        log.info("初始化常用缓存信息结束");
    }
}
