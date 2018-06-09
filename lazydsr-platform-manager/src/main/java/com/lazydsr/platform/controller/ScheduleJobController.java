package com.lazydsr.platform.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lazydsr.commons.result.HttpStatus;
import com.lazydsr.commons.result.ResultBody;
import com.lazydsr.platform.entity.Menu;
import com.lazydsr.platform.entity.ScheduleJob;
import com.lazydsr.platform.service.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<ScheduleJob> scheduleJobs = scheduleJobService.findAllNormal();
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

    @GetMapping("/{type}/{id}")
    public String findByTypeAndId(@PathVariable("type") String type, @PathVariable("id") String id, Map map) {
        String url = "";
        if (type != null && type.equalsIgnoreCase("edit")) {
            url = "scheduleJob/edit";
        } else {
            url = "scheduleJob/view";
        }
        ScheduleJob scheduleJob = scheduleJobService.findById(id);
        map.put("data", scheduleJob);
        return url;
    }

    @PutMapping
    @ResponseBody
    public Object updateById(ScheduleJob job) {
        ScheduleJob scheduleJob = scheduleJobService.update(job);
        if (scheduleJob != null)
            return ResultBody.success();
        else
            return ResultBody.error(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Object deleteById(@PathVariable("id") String id) {
        int count = scheduleJobService.delete(id);

        if (count > 0)
            return ResultBody.success();
        else
            return ResultBody.error(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("menu2")
    @ResponseBody
    public Object deleteMultipleById( Object[] ids) {

        log.error(ids.toString());

        return null;
        //int count = scheduleJobService.deleteMultipleById(ids);
        //if (count>0){
        //    return ResultBody.success();
        //}else {
        //    return ResultBody.error();
        //}
    }
}
