package com.lazydsr.platform.mapper;

import com.lazydsr.platform.entity.ScheduleJob;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob>{
    @Delete({
        "update sys_schedule_job set status=1",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKeyOnCustom(String id);

    @Select({
            "select",
            "id, name, jobgroup, classpath, method, concurrent, cron, description, jobstatus, ",
            "creator, create_date, modifier, modify_date, status",
            "from sys_schedule_job",
            "where status=0"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="jobgroup", property="jobgroup", jdbcType=JdbcType.VARCHAR),
            @Result(column="classpath", property="classpath", jdbcType=JdbcType.VARCHAR),
            @Result(column="method", property="method", jdbcType=JdbcType.VARCHAR),
            @Result(column="concurrent", property="concurrent", jdbcType=JdbcType.VARCHAR),
            @Result(column="cron", property="cron", jdbcType=JdbcType.VARCHAR),
            @Result(column="description", property="description", jdbcType=JdbcType.VARCHAR),
            @Result(column="jobstatus", property="jobstatus", jdbcType=JdbcType.VARCHAR),
            @Result(column="creator", property="creator", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_date", property="createDate", jdbcType=JdbcType.VARCHAR),
            @Result(column="modifier", property="modifier", jdbcType=JdbcType.VARCHAR),
            @Result(column="modify_date", property="modifyDate", jdbcType=JdbcType.VARCHAR),
            @Result(column="status", property="status", jdbcType=JdbcType.INTEGER)
    })
    List<ScheduleJob> selectAllNormal();

}