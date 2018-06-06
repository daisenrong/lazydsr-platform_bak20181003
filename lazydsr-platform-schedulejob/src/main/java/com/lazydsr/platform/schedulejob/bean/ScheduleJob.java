package com.lazydsr.platform.schedulejob.bean;

import com.lazydsr.util.id.UtilUUId;
import com.lazydsr.util.time.UtilDateTime;
import lombok.Data;

/**
 * ScheduleJob
 * PROJECT_NAME: lazydsr-web-template
 * PACKAGE_NAME: com.lazydsr.platform.entity
 * Created by Lazy on 2018/5/13 20:21
 * Version: 0.1
 * Info: 定时任务类
 */

@Data
public class ScheduleJob {

    public static final String STATUS_RUNNING = "0";
    public static final String STATUS_NOT_RUNNING = "1";
    public static final String CONCURRENT_IS = "0";
    public static final String CONCURRENT_NOT = "1";


    private String id = UtilUUId.getId();

    private String name;

    private String jobgroup="default";

    private String classpath;

    private String method;

    private String concurrent;

    private String cron;

    private String description;

    private String jobstatus="0";




    private String creator;

    private String createDate = UtilDateTime.getCurrDateTime();

    private String modifier;

    private String modifyDate = UtilDateTime.getCurrDateTime();

    private Integer status = 0;


}
