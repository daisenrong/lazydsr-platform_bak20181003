package com.lazydsr.platform.dao;

import com.lazydsr.platform.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserJdbc
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.jdbc
 * Created by Lazy on 2018/3/7 16:26
 * Version: 0.1
 * Info: @TODO:...
 */

public interface UserDao {
    public List<User> findbyNameJdbc(String name);
}
