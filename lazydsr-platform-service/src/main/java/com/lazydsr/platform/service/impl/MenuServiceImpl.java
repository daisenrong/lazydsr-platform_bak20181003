package com.lazydsr.platform.service.impl;

import com.lazydsr.platform.entity.Menu;
import com.lazydsr.platform.mapper.MenuMapper;
import com.lazydsr.platform.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * MenuServiceImpl
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.service.impl
 * Created by Lazy on 2018/3/19 16:02
 * Version: 0.1
 * Info: @TODO:...
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "menu")
public class MenuServiceImpl implements MenuService {
    private static final String prefix = "menu";
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    //@Autowired
    //private RedisService redisService;

    @Override
    public Menu add(Menu menu) {

        int count = menuMapper.insert(menu);
        menu = menuMapper.selectByPrimaryKey(menu.getId());
        //操作缓存
        ValueOperations opsForValue = redisTemplate.opsForValue();
        String key = prefix + "::" + menu.getId();
        if (count > 0) {
            opsForValue.set(key, menu, 60 * 60, TimeUnit.SECONDS);
            redisTemplate.delete(prefix + "::findAllNormal");
            redisTemplate.delete(prefix + "::findAll");
        }
        return menu;
    }

    @Override
    public int delete(String id) {
        int count = menuMapper.deleteByPrimaryKey(id);
        if (count > 0) {
            redisTemplate.delete(prefix + "::" + id);
            redisTemplate.delete(prefix + "::findAllNormal");
            redisTemplate.delete(prefix + "::findAll");
        }
        return count;
    }


    @Override
    public Menu update(Menu menu) {
        String key = prefix + "::" + menu.getId();
        ValueOperations opsForValue = redisTemplate.opsForValue();
        int count = menuMapper.updateByPrimaryKey(menu);
        if (count > 0) {
            menu = menuMapper.selectByPrimaryKey(menu.getId());
            opsForValue.set(key, menu, 60 * 60, TimeUnit.SECONDS);
            redisTemplate.delete(prefix + "::findAllNormal");
            redisTemplate.delete(prefix + "::findAll");
        }
        return menu;
    }

    @Override
    public Menu findById(String id) {
        String key = prefix + "::" + id;
        ValueOperations opsForValue = redisTemplate.opsForValue();
        Menu menu = (Menu) opsForValue.get(key);
        if (menu == null) {
            menu = menuMapper.selectByPrimaryKey(id);
            if (menu != null) {
                opsForValue.set(key, menu, 60 * 60, TimeUnit.SECONDS);
            }
        }
        return menu;
    }

    @Override
    public List<Menu> findAll() {
        String key = prefix + "::" + Thread.currentThread().getStackTrace()[1].getMethodName();
        ListOperations opsForList = redisTemplate.opsForList();
        List<Menu> list = opsForList.range(key, 0, -1);
        if (CollectionUtils.isEmpty(list)) {
            log.warn("缓存为空，查询数据库，添加缓存");
            list = menuMapper.selectAll();

            if (!CollectionUtils.isEmpty(list)) {
                opsForList.rightPushAll(key, list);
                redisTemplate.expire(key, 60 * 60, TimeUnit.SECONDS);
            }
        }
        return list;
    }

    @Override
    public List<Menu> findAllNormal() {
        String key = prefix + "::" + Thread.currentThread().getStackTrace()[1].getMethodName();
        ListOperations opsForList = redisTemplate.opsForList();
        List<Menu> list = opsForList.range(key, 0, -1);

        if (CollectionUtils.isEmpty(list)) {
            log.warn("缓存为空，查询数据库，添加缓存");
            list = menuMapper.selectAllNormal();

            if (!CollectionUtils.isEmpty(list)) {
                opsForList.rightPushAll(key, list);
                redisTemplate.expire(key, 60 * 60, TimeUnit.SECONDS);
            }
        }
        return list;
    }

    @Override
    public int deleteMultipleById(List<String> ids) {
        //ArrayList<String> ids = new ArrayList<>();
        //menus.stream().forEach(menu -> {
        //    ids.add(menu.getId());
        //});
        Example example = new Example(Menu.class);
        example.createCriteria().andIn("id", ids);
        int count = menuMapper.deleteByExample(example);
        if (count > 0) {
            ids.stream().forEach(key -> redisTemplate.delete(key));
            redisTemplate.delete(prefix + "::findAllNormal");
            redisTemplate.delete(prefix + "::findAll");
        }
        return count;
    }


    @Override
    public List<Menu> findByUserId(String userId) {
        return null;
    }


}
