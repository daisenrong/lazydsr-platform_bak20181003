package com.lazydsr.platform.entity;

import com.lazydsr.util.id.UtilUUId;
import com.lazydsr.util.time.UtilDateTime;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ScheduleJob
 * PROJECT_NAME: lazydsr-web-template
 * PACKAGE_NAME: com.lazydsr.platform.entity
 * Created by Lazy on 2018/5/13 20:21
 * Version: 0.1
 * Info: 定时任务类
 */
@Table(name = "sys_scheduleJob")
@Data
public class ScheduleJob {

    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";
    public static final String CONCURRENT_IS = "1";
    public static final String CONCURRENT_NOT = "0";

    @Id
    private String id = UtilUUId.getId();

    private String name;

    private String jobgroup;

    private String classpath;

    private String method;

    private String concurrent;

    private String cron;

    private String description;

    private String jobstatus;




    private String creator;

    private String createDate = UtilDateTime.getCurrDateTime();

    private String modifier;

    private String modifyDate = UtilDateTime.getCurrDateTime();

    private Integer status = 0;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleJob that = (ScheduleJob) o;

        if (jobgroup != null ? !jobgroup.equals(that.jobgroup) : that.jobgroup != null) return false;
        if (classpath != null ? !classpath.equals(that.classpath) : that.classpath != null) return false;
        if (method != null ? !method.equals(that.method) : that.method != null) return false;
        if (concurrent != null ? !concurrent.equals(that.concurrent) : that.concurrent != null) return false;
        if (cron != null ? !cron.equals(that.cron) : that.cron != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (jobstatus != null ? !jobstatus.equals(that.jobstatus) : that.jobstatus != null) return false;
        if (creator != null ? !creator.equals(that.creator) : that.creator != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (modifier != null ? !modifier.equals(that.modifier) : that.modifier != null) return false;
        if (modifyDate != null ? !modifyDate.equals(that.modifyDate) : that.modifyDate != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        int result = jobgroup != null ? jobgroup.hashCode() : 0;
        result = 31 * result + (classpath != null ? classpath.hashCode() : 0);
        result = 31 * result + (method != null ? method.hashCode() : 0);
        result = 31 * result + (concurrent != null ? concurrent.hashCode() : 0);
        result = 31 * result + (cron != null ? cron.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (jobstatus != null ? jobstatus.hashCode() : 0);
        result = 31 * result + (creator != null ? creator.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (modifier != null ? modifier.hashCode() : 0);
        result = 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
