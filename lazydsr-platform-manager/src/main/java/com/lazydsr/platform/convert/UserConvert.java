package com.lazydsr.platform.convert;

import com.lazydsr.platform.entity.User;
import com.lazydsr.platform.vo.UserVO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * UserConvert
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.convert
 * Created by Lazy on 2018/6/10 18:03
 * Version: 0.1
 * Info: user2UserVO
 */
public class UserConvert {
    public static UserVO user2UserVo(User user) {

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    public static List<UserVO> userList2UserVOList(List<User> userList) {
        ArrayList<UserVO> userVOList = new ArrayList<>();
        userList.stream().forEach(user -> {
            userVOList.add(user2UserVo(user));
        });
        return userVOList;
    }
}
