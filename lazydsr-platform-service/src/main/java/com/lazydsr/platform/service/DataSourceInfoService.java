package com.lazydsr.platform.service;

import com.lazydsr.platform.entity.DataSourceInfo;

import java.util.List;

/**
 * DatasourceService
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.service
 * Created by Lazy on 2018/3/9 21:54
 * Version: 0.1
 * Info: @TODO:...
 */
public interface DataSourceInfoService {
    DataSourceInfo add(DataSourceInfo dataSourceInfo);

    int delete(String id);

    DataSourceInfo update(DataSourceInfo dataSourceInfo);

    DataSourceInfo findById(String id);

    public List<DataSourceInfo> findAll();

    public List<DataSourceInfo> findAllNormal();


}
