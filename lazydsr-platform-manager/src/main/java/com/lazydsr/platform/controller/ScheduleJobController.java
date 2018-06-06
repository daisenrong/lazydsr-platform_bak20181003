package com.lazydsr.platform.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lazydsr.platform.entity.ScheduleJob;
import com.lazydsr.platform.service.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ScheduleJobController
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.controller
 * Created by Lazy on 2018/6/5 22:16
 * Version: 0.1
 * Info: @TODO:...
 */
@Slf4j
@Controller
@RequestMapping("scheduleJob")
public class ScheduleJobController {
    @Autowired
    private ScheduleJobService scheduleJobService;


    @RequestMapping("json/findAll/page")
    @ResponseBody
    public Map findAllJsonPage(int page, int limit) {
        Map map = new HashMap();
        //List<ScheduleJob> all = scheduleJobService.findAll();
        PageHelper.startPage(page, limit);
        List<ScheduleJob> scheduleJobs = scheduleJobService.findAll();
        PageInfo<ScheduleJob> pageInfo = new PageInfo<>(scheduleJobs);

        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", scheduleJobs);
        //map.put("code", 0);
        //map.put("msg", "");
        //map.put("count", menus.getTotalElements());
        //map.put("data", menus.getContent());
        return map;

    }

    @PostMapping
    @ResponseBody
    public ScheduleJob add(ScheduleJob scheduleJob) {
        ScheduleJob job = scheduleJobService.add(scheduleJob);
        return job;
    }
}
