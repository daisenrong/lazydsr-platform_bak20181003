package com.lazydsr.platform.service;

import com.lazydsr.platform.entity.SystemInfo;

/**
 * SystemInfoRepository
 * PROJECT_NAME: lazydsr-web-template
 * PACKAGE_NAME: com.lazydsr.platform.repository
 * Created by Lazy on 2018/3/15 0:04
 * Version: 0.1
 * Info: @TODO:...
 */
public interface SystemInfoService {
    SystemInfo add(SystemInfo systemInfo);

    SystemInfo findByMaxCreateDate();
}
