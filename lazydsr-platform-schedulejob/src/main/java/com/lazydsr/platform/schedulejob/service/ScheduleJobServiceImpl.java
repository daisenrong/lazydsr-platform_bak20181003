package com.lazydsr.platform.schedulejob.service;

import com.lazydsr.platform.schedulejob.bean.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ScheduleJobServiceImpl
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.schedulejob.service
 * Created by Lazy on 2018/6/6 23:19
 * Version: 0.1
 * Info: @TODO:...
 */
@Slf4j
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {
    @Override
    public List<ScheduleJob> findAll() throws Exception {
        log.error("ScheduleJobService 未实现，请实现后在使用");
        throw new Exception("ScheduleJobService 未实现，请实现后在使用");
    }
}
