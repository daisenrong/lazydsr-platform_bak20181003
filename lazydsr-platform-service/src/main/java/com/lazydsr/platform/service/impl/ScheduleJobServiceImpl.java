package com.lazydsr.platform.service.impl;

import com.lazydsr.platform.entity.ScheduleJob;
import com.lazydsr.platform.mapper.ScheduleJobMapper;
import com.lazydsr.platform.service.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ScheduleJobServiceImpl
 * PROJECT_NAME: lazydsr-web-template
 * PACKAGE_NAME: com.lazydsr.platform.service.impl
 * Created by Lazy on 2018/5/13 21:23
 * Version: 0.1
 * Info: 调度任务services impl
 */
@Service
@Slf4j
public class ScheduleJobServiceImpl implements ScheduleJobService {
    private static final String prefix = "schedulejob";
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    //@Autowired
    //private ScheduleJobConfiguration scheduleJobConfiguration;

    @Override
    public ScheduleJob add(ScheduleJob scheduleJob) {
        int insert = scheduleJobMapper.insert(scheduleJob);
        ScheduleJob job = scheduleJobMapper.selectByPrimaryKey(scheduleJob.getId());
        //初始化定时任务
        //com.lazydsr.platform.schedulejob.bean.ScheduleJob job1 = new com.lazydsr.platform.schedulejob.bean.ScheduleJob();
        //BeanUtils.copyProperties(job, job1);
        //scheduleJobConfiguration.addJob(job1);

        return job;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public ScheduleJob update(ScheduleJob scheduleJob) {
        return null;
    }

    @Override
    public ScheduleJob findById(String id) {
        return null;
    }

    @Override
    public List<ScheduleJob> findAllNormal() {
        return null;
    }

    @Override
    public List<ScheduleJob> findAll() {
        String key = prefix + "::" + Thread.currentThread().getStackTrace()[1].getMethodName();
        List<ScheduleJob> scheduleJobList = null;
        SetOperations opsForSet = redisTemplate.opsForSet();
        Set<ScheduleJob> scheduleJobSet = opsForSet.members(key);
        if (CollectionUtils.isEmpty(scheduleJobSet)) {
            scheduleJobList = scheduleJobMapper.selectAll();
            scheduleJobSet.addAll(scheduleJobList);
            if (!CollectionUtils.isEmpty(scheduleJobList)) {
                scheduleJobSet.stream().forEach(scheduleJob -> opsForSet.add(key, scheduleJob));
            }
        }
        scheduleJobList = new ArrayList<>();
        scheduleJobList.addAll(scheduleJobSet);
        return scheduleJobList;
    }

    @Override
    public int deleteMultipleById(List<String> ids) {
        return 0;
    }
}
