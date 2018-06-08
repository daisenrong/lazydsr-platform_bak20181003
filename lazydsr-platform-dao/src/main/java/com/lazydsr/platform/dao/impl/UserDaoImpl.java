package com.lazydsr.platform.dao.impl;

import com.lazydsr.platform.entity.User;
import com.lazydsr.platform.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserJdbcImpl
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.jdbc.impl
 * Created by Lazy on 2018/3/7 16:27
 * Version: 0.1
 * Info: @TODO:...
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findbyNameJdbc(String name) {
        List<User> list = jdbcTemplate.query("select * from user", new BeanPropertyRowMapper<>(User.class));
        return list;
    }
}
