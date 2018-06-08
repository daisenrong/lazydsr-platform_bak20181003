package com.lazydsr.platform.service.impl;

import com.lazydsr.platform.entity.SystemInfo;
import com.lazydsr.platform.mapper.SystemInfoMapper;
import com.lazydsr.platform.service.SystemInfoService;
import com.lazydsr.util.id.UtilUUId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;



/**
 * SystemInfoServiceImpl
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.service.impl
 * Created by Lazy on 2018/3/15 0:06
 * Version: 0.1
 * Info: @TODO:...
 */
@Service
@CacheConfig(cacheNames = "systemInfo")
public class SystemInfoServiceImpl implements SystemInfoService {
    @Autowired
    private SystemInfoMapper systemInfoMapper;

    @Override
    //@Cacheable(key = "#systemInfo.id")
    @CacheEvict(allEntries = true)
    public SystemInfo add(SystemInfo systemInfo) {
        if (systemInfo.getId() == null || systemInfo.getId().equals(""))
            systemInfo.setId(UtilUUId.getId());
        int count = systemInfoMapper.insert(systemInfo);
        return systemInfoMapper.selectByPrimaryKey(systemInfo.getId());

    }

    @Override
    @Cacheable
    public SystemInfo findByMaxCreateDate() {
        //return systemInfoMapper.findTopByOrderByCreateDateDesc();
        return systemInfoMapper.selectByMaxCreateDate();
    }


}
