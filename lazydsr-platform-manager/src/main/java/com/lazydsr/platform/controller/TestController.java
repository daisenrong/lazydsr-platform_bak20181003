package com.lazydsr.platform.controller;

import com.lazydsr.platform.config.cache.redis.RedisService;
import com.lazydsr.platform.config.schedule.ScheduleConfiguration;
import com.lazydsr.platform.entity.ScheduleJob;
import com.lazydsr.platform.entity.User;
import com.lazydsr.platform.service.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TestController
 * PROJECT_NAME: lazydsr-web-template
 * PACKAGE_NAME: com.lazydsr.platform.controller
 * Created by Lazy on 2018/5/19 15:38
 * Version: 0.1
 * Info: @TODO:...
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private ScheduleConfiguration scheduleConfiguration;

    @RequestMapping("/{string}")
    public String test(@PathVariable String string) {
        int a = 3;
        User user = new User();
        //user.set;
        redisService.set(a + "", user);
        String s = (String) redisService.get(string);
        log.error(s + "-----" + s);
        return s;
    }

    @RequestMapping("scheduleJobs")
    public List<ScheduleJob> getScheduleJobs() {
        try {
            return scheduleConfiguration.getAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
