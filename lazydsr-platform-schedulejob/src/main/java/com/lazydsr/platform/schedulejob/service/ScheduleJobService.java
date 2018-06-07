package com.lazydsr.platform.schedulejob.service;

import com.lazydsr.platform.schedulejob.bean.ScheduleJob;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ScheduleJobService
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.schedulejob.service
 * Created by Lazy on 2018/6/6 23:10
 * Version: 0.1
 * Info: @TODO:...
 */

public interface ScheduleJobService {
    public List<ScheduleJob> findAll() throws Exception;
}
