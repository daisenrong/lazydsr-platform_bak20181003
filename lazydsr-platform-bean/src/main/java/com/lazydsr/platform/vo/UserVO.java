package com.lazydsr.platform.vo;

import com.lazydsr.util.id.UtilUUId;
import com.lazydsr.util.time.UtilDateTime;
import lombok.Data;

import java.io.Serializable;

/**
 * UserVO
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.vo
 * Created by Lazy on 2018/6/10 17:51
 * Version: 0.1
 * Info: UserVO
 */
@Data
public class UserVO implements Serializable {

    private String id = UtilUUId.getId();

    private String name;

    private String username;

    private String workcode;

    private Integer sumPasswordWrong;

    private Integer passwordLock;

    private String email;

    private String mobile;

    private String telphone;

    private String organization;

    private String company;

    private String department;

    private String idCard;

    private String currentLoginDate;

    private String lastLoginDate;

    private Double orderNum = 1000.0;

    private Integer accountType;

    private String creator;

    private String createDate = UtilDateTime.getCurrDateTime();

    private String modifier;

    private String modifyDate = UtilDateTime.getCurrDateTime();

    private Integer status = 0;
}
