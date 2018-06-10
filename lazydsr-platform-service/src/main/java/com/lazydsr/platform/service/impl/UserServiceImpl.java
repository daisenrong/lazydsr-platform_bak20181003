package com.lazydsr.platform.service.impl;

import com.lazydsr.platform.dao.UserDao;
import com.lazydsr.platform.entity.User;
import com.lazydsr.platform.mapper.UserMapper;
import com.lazydsr.platform.service.UserService;
import com.lazydsr.util.MD5.UtilMD5;
import com.lazydsr.util.id.UtilUUId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * UserServiceImpl
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.service.impl
 * Created by Lazy on 2018/3/7 23:30
 * Version: 0.1
 * Info: @TODO:...
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {
    private static final String prefix = "user";
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDao userDao;
    //@Autowired
    //private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public User add(User user) {
        int count = userMapper.insert(user);
        if (count > 0) {
            user = userMapper.selectByPrimaryKey(user.getId());
            if (user != null) {
                ValueOperations opsForValue = redisTemplate.opsForValue();
                opsForValue.set(prefix + "::" + user.getUsername(), user, 60 * 60, TimeUnit.SECONDS);
                opsForValue.set(prefix + "::" + user.getId(), user, 60 * 60, TimeUnit.SECONDS);
                redisTemplate.opsForList().rightPush(prefix+"::findAll",user.getId());
                redisTemplate.expire(prefix+"::findAll",60*60,TimeUnit.SECONDS);
                if (user.getStatus()==0){
                    redisTemplate.opsForList().rightPush(prefix+"::findAllNormal",user.getId());
                    redisTemplate.expire(prefix+"::findAllNormal",60*60,TimeUnit.SECONDS);
                }
            }
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        String key = prefix + "::" + username;
        ValueOperations opsForValue = redisTemplate.opsForValue();
        User user = (User) opsForValue.get(key);
        if (user == null) {
            log.warn("缓存获取失败，查询数据库");
            user = userMapper.selectByUsername(username);
            if (user != null) {
                opsForValue.set(key, user, 60 * 60, TimeUnit.SECONDS);
                opsForValue.set(prefix + "::" + user.getId(), user, 60 * 60, TimeUnit.SECONDS);
            }
        }
        return user;
    }

    @Override
    public int delete(String id) {
        String key = prefix + "::" + id;
        User user = findById(id);
        //int count = userMapper.deleteByPrimaryKey(id);
        user.setStatus(1);
        int count = userMapper.updateByPrimaryKey(user);
        if (count > 0) {
            redisTemplate.delete(key);
            redisTemplate.delete(prefix + "::" + user.getUsername());
            redisTemplate.opsForList().remove(prefix+"::findAllNormal", 1, id);
        }
        return count;
    }

    @Override
    public User update(User user) {
        String key = prefix + "::" + user.getId();
        ValueOperations opsForValue = redisTemplate.opsForValue();

        int count = userMapper.updateByPrimaryKeySelective(user);
        if (count > 0) {
            user = userMapper.selectByPrimaryKey(user.getId());
            opsForValue.set(key, user, 60 * 60, TimeUnit.SECONDS);
            opsForValue.set(prefix + "::" + user.getUsername(), user, 60 * 60, TimeUnit.SECONDS);
        }
        return user;
    }

    @Override
    public User findById(String id) {
        String key = prefix + "::" + id;
        ValueOperations opsForValue = redisTemplate.opsForValue();
        User user = (User) opsForValue.get(key);
        if (user == null) {
            user = userMapper.selectByPrimaryKey(id);
            if (user != null) {
                opsForValue.set(key, user, 60 * 60, TimeUnit.SECONDS);
                opsForValue.set(prefix + "::" + user.getUsername(), user, 60 * 60, TimeUnit.SECONDS);
            }
        }
        return user;
    }

    @Override
    public List<User> findAllNormal() {
        String key = prefix + "::" + Thread.currentThread().getStackTrace()[1].getMethodName();
        ListOperations opsForList = redisTemplate.opsForList();
        List<String> list = opsForList.range(key, 0, -1);
        if (CollectionUtils.isEmpty(list)) {
            list = userMapper.selectAllIdNormal();
            if (!CollectionUtils.isEmpty(list)) {
                opsForList.rightPushAll(key, list);
                redisTemplate.expire(key, 60 * 60, TimeUnit.SECONDS);
            }
        }
        //组合数据
        ArrayList<User> users = new ArrayList<>();
        list.stream().forEach(id -> users.add(findById(id)));
        return users;
    }

    @Override
    public List<User> findAll() {
        String key = prefix + "::" + Thread.currentThread().getStackTrace()[1].getMethodName();
        ListOperations opsForList = redisTemplate.opsForList();
        List<String> list = opsForList.range(key, 0, -1);
        if (CollectionUtils.isEmpty(list)) {
            list = userMapper.selectAllId();
            if (!CollectionUtils.isEmpty(list)) {
                opsForList.rightPushAll(key, list);
                redisTemplate.expire(key, 60 * 60, TimeUnit.SECONDS);
            }
        }
        //组合数据
        ArrayList<User> users = new ArrayList<>();
        list.stream().forEach(id -> users.add(findById(id)));
        return users;

    }


}
