package com.lazydsr.platform.service;

import com.lazydsr.platform.entity.ScheduleJob;

import java.util.List;

/**
 * MenuService
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.service
 * Created by Lazy on 2018/3/19 15:58
 * Version: 0.1
 * Info: @TODO:...
 */
public interface ScheduleJobService {

    ScheduleJob add(ScheduleJob scheduleJob);

    int delete(String id);

    ScheduleJob update(ScheduleJob scheduleJob);

    ScheduleJob findById(String id);

    //List<ScheduleJob> findByUserId(String userId);

    List<ScheduleJob> findAllNormal();

    List<ScheduleJob> findAll();

    int deleteMultipleById(List<String> ids);
}
