package com.lazydsr.platform.controller;

import com.lazydsr.platform.config.cache.redis.RedisService;
import com.lazydsr.platform.entity.ScheduleJob;
import com.lazydsr.platform.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TestController
 * PROJECT_NAME: lazydsr-platform
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

    //@RequestMapping("scheduleJobs")
    //public List<ScheduleJob> getScheduleJobs() {
    //    try {
    //        return scheduleJobConfiguration.getAllJob();
    //    } catch (SchedulerException e) {
    //        e.printStackTrace();
    //    }
    //    return null;
    //}
}
