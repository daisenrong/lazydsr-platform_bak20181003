package com.lazydsr.platform.schedulejob.config;

import com.lazydsr.platform.schedulejob.bean.ScheduleJob;
import com.lazydsr.util.time.UtilDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * ScheduleJobUtils
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.config.scheduleJob
 * Created by Lazy on 2018/5/16 14:40
 * Version: 0.1
 * Info: 定时任务执行工具类
 */
@Slf4j
public class ScheduleJobUtils {
    //public final static Logger log = Logger.getLogger(ScheduleJobUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static String invokMethod(ScheduleJob scheduleJob) {
        Object object = null;
        Class clazz = null;
        //springId不为空先按springId查找bean
        //if (!Strings.isNotBlank(scheduleJob.getClasspath())) {
        //if (false) {
        //    //TODO:暂时不需
        //    object = SpringContextUtil.getBean(scheduleJob.getClasspath());
        //} else
        if (Strings.isNotBlank(scheduleJob.getClasspath())) {
            try {
                clazz = Class.forName(scheduleJob.getClasspath());
                object = clazz.newInstance();
            } catch (Exception e) {
                log.error("任务名称 = [" + scheduleJob.getName() + "]---------------未启动成功，请检查类路径是否正确！！！" + e.toString());
                e.printStackTrace();
                return "任务名称 = [" + scheduleJob.getName() + "]---------------未启动成功，请检查类路径是否正确！！！" + e.toString();
            }

        }

        //clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(scheduleJob.getMethod());
        } catch (NoSuchMethodException e) {
            log.error("任务名称 = [" + scheduleJob.getName() + "]---------------未启动成功，方法名设置错误！！！");

            return "任务名称 = [" + scheduleJob.getName() + "]---------------未启动成功，方法名设置错误！！！" + e.toString();
        } catch (SecurityException e) {
            return "任务名称 = [" + scheduleJob.getName() + "]---------------" + e.toString();
        }
        if (method != null) {
            try {
                log.info("定时任务：{}执行开始，执行方法：{}，开始时间：{}", scheduleJob.getClasspath(), scheduleJob.getMethod(), UtilDateTime.getCurrDateTime());
                method.invoke(object);
                log.info("定时任务：{}执行开始，执行方法：{}，结束时间：{}", scheduleJob.getClasspath(), scheduleJob.getMethod(), UtilDateTime.getCurrDateTime());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return "任务名称 = [" + scheduleJob.getName() + "]---------------" + e.toString();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return "任务名称 = [" + scheduleJob.getName() + "]---------------" + e.toString();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return "任务名称 = [" + scheduleJob.getName() + "]---------------" + e.toString();
            }
        }
        return "Success";
    }

}
