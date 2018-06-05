package com.lazydsr.platform.service.impl;

import com.lazydsr.platform.entity.Menu;
import com.lazydsr.platform.mapper.MenuMapper;
import com.lazydsr.platform.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * MenuServiceImpl
 * PROJECT_NAME: lazydsr-web-template
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
        return menuMapper.selectByPrimaryKey(menu.getId());

    }

    @Override
    public int delete(String id) {
        return menuMapper.deleteByPrimaryKey(id);
    }


    @Override
    @CachePut(key = "#menu.id")
    public Menu update(Menu menu) {
        int count = menuMapper.updateByPrimaryKey(menu);
        if (count > 0) {
            return menuMapper.selectByPrimaryKey(menu.getId());
        } else {
            return null;
        }
    }

    @Override
    @Cacheable(key = "#id")
    public Menu findById(String id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    @Cacheable
    public List<Menu> findAll() {
        return menuMapper.selectAll();
    }

    @Override
    @CacheEvict
    public int deleteMultipleById(List<String> ids) {
        //ArrayList<String> ids = new ArrayList<>();
        //menus.stream().forEach(menu -> {
        //    ids.add(menu.getId());
        //});
        Example example = new Example(Menu.class);
        example.createCriteria().andIn("id", ids);
        return menuMapper.deleteByExample(example);
    }

    @Override
    public List<Menu> findAllNormal() {
        String key=prefix+"::"+Thread.currentThread().getStackTrace()[1].getMethodName();
        ListOperations opsForList = redisTemplate.opsForList();
        //List<Menu> list = redisService.getList(prefix + "::findAllNormal");
        List<Menu> list = opsForList.range(key,0,-1);
        //log.info("缓存" + list==null?"null":list.toString());

        if (CollectionUtils.isEmpty(list)) {
            log.warn("缓存为空，查询数据库，添加缓存");
            list = menuMapper.selectAllNormal();
            log.info("数据库" + list.toString());
            //if (list != null)
                //redisService.getRedisTemplate().opsForList().leftPushAll(prefix + "::findAllNormal",list);
                //redisService.setListAll(prefix + "::findAllNormal", list);
            if (!CollectionUtils.isEmpty(list)){
                opsForList.leftPushAll(key,list);
            }
        }
        return list;
    }

    //@Override
    //public PageInfo<Menu> findAllNormal(int page, int size) {
    //    PageHelper.startPage(page, size);
    //    List<Menu> menus = menuMapper.selectAllNormal();
    //    PageInfo<Menu> pageInfo = new PageInfo<>(menus);
    //
    //    return pageInfo;
    //}

    @Override
    public List<Menu> findByUserId(String userId) {
        return null;
    }

    //@Override
    //public PageInfo<Menu> findAll(int page, int size) {
    //    PageHelper.startPage(page, size);
    //    List<Menu> menus = menuMapper.selectAll();
    //    PageInfo<Menu> pageInfo = new PageInfo<>(menus);
    //
    //    return pageInfo;
    //}


}
