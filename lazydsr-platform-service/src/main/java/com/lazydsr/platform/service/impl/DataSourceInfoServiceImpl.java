package com.lazydsr.platform.service.impl;

//import com.lazydsr.platform.config.cache.redis.RedisService;

import com.lazydsr.platform.entity.DataSourceInfo;
import com.lazydsr.platform.mapper.DataSourceInfoMapper;
import com.lazydsr.platform.service.DataSourceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * DatasourceInfoServiceImpl
 * PROJECT_NAME: lazydsr-web-template
 * PACKAGE_NAME: com.lazydsr.platform.service.impl
 * Created by Lazy on 2018/3/9 21:55
 * Version: 0.1
 * Info: @TODO:...
 */
@Service
@Slf4j
public class DataSourceInfoServiceImpl implements DataSourceInfoService {
    private static final String prefix = "dataSourceInfo::";
    @Autowired
    private DataSourceInfoMapper dataSourceInfoMapper;
    //@Autowired
    //private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public DataSourceInfo add(DataSourceInfo dataSourceInfo) {
        int count = dataSourceInfoMapper.insert(dataSourceInfo);
        return dataSourceInfoMapper.selectByPrimaryKey(dataSourceInfo.getId());
    }

    @Override
    @CacheEvict
    public int delete(String id) {
        return dataSourceInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public DataSourceInfo update(DataSourceInfo dataSourceInfo) {
        int count = dataSourceInfoMapper.updateByPrimaryKey(dataSourceInfo);
        redisTemplate.delete(prefix+dataSourceInfo.getId());
        return findById(dataSourceInfo.getId());
    }

    @Override
    @Cacheable(cacheNames = prefix, key = "#id")
    public DataSourceInfo findById(String id) {
        return dataSourceInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DataSourceInfo> findAll() {
        String key = prefix + Thread.currentThread().getStackTrace()[1].getMethodName();
        ListOperations opsForList = redisTemplate.opsForList();
        List<DataSourceInfo> list = opsForList.range(key, 0, -1);
        //List<DataSourceInfo> list = redisService.getList(prefix + "::findAll", 0, -1);

        if (CollectionUtils.isEmpty(list)) {
            log.info("缓存数据获取失败，加载到缓存");
            list = dataSourceInfoMapper.selectAllNormal();
            //redisService.setList(prefix + "::findAll", list);
            if (!CollectionUtils.isEmpty(list)){
                opsForList.leftPushAll(key, list);
                redisTemplate.expire(key,10,TimeUnit.MINUTES);
            }

        }
        return list;
    }

    @Override
    public List<DataSourceInfo> findAllNormal() {

        String key = prefix + Thread.currentThread().getStackTrace()[1].getMethodName();
        ListOperations opsForList = redisTemplate.opsForList();
        List<DataSourceInfo> list = opsForList.range(key, 0, -1);

        if (CollectionUtils.isEmpty(list)) {
            log.error("缓存数据获取失败，加载到缓存");
            list = dataSourceInfoMapper.selectAllNormal();
            if (!CollectionUtils.isEmpty(list)){
                opsForList.leftPushAll(key, list);
                redisTemplate.expire(key,10,TimeUnit.MINUTES);
            }

        }

        return list;
    }


}
