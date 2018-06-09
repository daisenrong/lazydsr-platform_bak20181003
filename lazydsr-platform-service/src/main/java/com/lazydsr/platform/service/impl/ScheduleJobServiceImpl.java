package com.lazydsr.platform.service.impl;

import com.lazydsr.platform.entity.ScheduleJob;
import com.lazydsr.platform.mapper.ScheduleJobMapper;
import com.lazydsr.platform.schedulejob.config.ScheduleJobConfiguration;
import com.lazydsr.platform.service.ScheduleJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * ScheduleJobServiceImpl
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.service.impl
 * Created by Lazy on 2018/5/13 21:23
 * Version: 0.1
 * Info: 调度任务services impl
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "schedulejob")
public class ScheduleJobServiceImpl implements ScheduleJobService {
    private static final String prefix = "schedulejob";
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ScheduleJobConfiguration scheduleJobConfiguration;

    @Override
    public ScheduleJob add(ScheduleJob scheduleJob) {
        int insert = scheduleJobMapper.insert(scheduleJob);
        ScheduleJob job = scheduleJobMapper.selectByPrimaryKey(scheduleJob.getId());
        //初始化定时任务
        com.lazydsr.platform.schedulejob.bean.ScheduleJob job1 = new com.lazydsr.platform.schedulejob.bean.ScheduleJob();
        BeanUtils.copyProperties(job, job1);
        scheduleJobConfiguration.addJob(job1);

        //操作缓存
        ValueOperations opsForValue = redisTemplate.opsForValue();
        String key = prefix + "::" + scheduleJob.getId();
        opsForValue.set(key, job, 60 * 60, TimeUnit.SECONDS);
        //对已存在的列表进行处理
        redisTemplate.delete(prefix + "::" + "findAllNormal");
        redisTemplate.delete(prefix + "::" + "findAll");
        return job;
    }

    @Override
    public int delete(String id) {
        //操作定时任务
        ScheduleJob scheduleJob = findById(id);
        com.lazydsr.platform.schedulejob.bean.ScheduleJob job = new com.lazydsr.platform.schedulejob.bean.ScheduleJob();
        BeanUtils.copyProperties(scheduleJob, job);
        scheduleJobConfiguration.deleteJob(job);
        int count = scheduleJobMapper.deleteByPrimaryKeyOnCustom(id);
        //处理缓存
        if (count > 0) {
            redisTemplate.delete(prefix + "::" + id);
            redisTemplate.delete(prefix + "::" + "findAllNormal");
            redisTemplate.delete(prefix + "::" + "findAll");
        }

        return count;
    }

    @Override
    public ScheduleJob update(ScheduleJob scheduleJob) {
        int i = scheduleJobMapper.updateByPrimaryKey(scheduleJob);
        if (i > 0) {
            redisTemplate.delete(prefix + "::" + scheduleJob.getId());
            redisTemplate.delete(prefix + "::" + "findAllNormal");
            redisTemplate.delete(prefix + "::" + "findAll");
        }
        //操作定时任务
        ScheduleJob job = scheduleJobMapper.selectByPrimaryKey(scheduleJob.getId());
        com.lazydsr.platform.schedulejob.bean.ScheduleJob job1 = new com.lazydsr.platform.schedulejob.bean.ScheduleJob();
        BeanUtils.copyProperties(job, job1);
        scheduleJobConfiguration.deleteJob(job1);
        scheduleJobConfiguration.addJob(job1);
        return job;
    }

    @Override
    public ScheduleJob findById(String id) {
        ValueOperations opsForValue = redisTemplate.opsForValue();
        String key = prefix + "::" + id;
        ScheduleJob scheduleJob = (ScheduleJob) opsForValue.get(key);
        if (scheduleJob == null) {
            scheduleJob = scheduleJobMapper.selectByPrimaryKey(id);
            opsForValue.set(key, scheduleJob, 60 * 60, TimeUnit.SECONDS);
        }
        return scheduleJob;
    }

    @Override
    public List<ScheduleJob> findAllNormal() {
        String key = prefix + "::" + Thread.currentThread().getStackTrace()[1].getMethodName();
        ListOperations opsForList = redisTemplate.opsForList();
        List<ScheduleJob> list = opsForList.range(key, 0, -1);
        if (CollectionUtils.isEmpty(list)) {
            list = scheduleJobMapper.selectAllNormal();

            if (!CollectionUtils.isEmpty(list)) {
                opsForList.rightPushAll(key, list);
                redisTemplate.expire(key, 60 * 60, TimeUnit.SECONDS);
            }
        }

        return list;
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
        //定时任务
        ids.stream().forEach(key -> {
            com.lazydsr.platform.schedulejob.bean.ScheduleJob job = new com.lazydsr.platform.schedulejob.bean.ScheduleJob();
            BeanUtils.copyProperties(findById(key), job);
            scheduleJobConfiguration.deleteJob(job);
            redisTemplate.delete(prefix + "::" + key);
        });
        //缓存
        redisTemplate.delete(prefix + "::" + "findAllNormal");
        redisTemplate.delete(prefix + "::" + "findAll");
        Example example = new Example(ScheduleJob.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        int count = scheduleJobMapper.deleteByExample(example);
        return count;
    }
}
