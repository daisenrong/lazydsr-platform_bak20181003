package com.lazydsr.platform.service;

import com.lazydsr.platform.entity.UserLoginRecord;

import java.util.List;

/**
 * UserLoginRecordService
 * PROJECT_NAME: lazydsr-web-template
 * PACKAGE_NAME: com.lazydsr.platform.service
 * Created by Lazy on 2018/3/19 0:51
 * Version: 0.1
 * Info: @TODO:...
 */
public interface UserLoginRecordService {
    UserLoginRecord add(UserLoginRecord userLoginRecord);

    List<UserLoginRecord> findAll();

    List<UserLoginRecord> findByUserId(String userId);

}
