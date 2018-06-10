package com.lazydsr.platform.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lazydsr.commons.result.HttpStatus;
import com.lazydsr.commons.result.ResultBody;
import com.lazydsr.platform.config.security.CustomPasswordEncoder;
import com.lazydsr.platform.convert.UserConvert;
import com.lazydsr.platform.entity.User;
import com.lazydsr.platform.entity.User;
import com.lazydsr.platform.service.UserService;
import com.lazydsr.platform.vo.UserVO;
import com.lazydsr.util.time.UtilDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.controller
 * Created by Lazy on 2018/3/8 11:10
 * Version: 0.1
 * Info: userController
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("json/findAll/page")
    @ResponseBody
    public Map findAllJsonPage(int page, int limit) {
        Map map = new HashMap();
        //List<User> all = userService.findAll();
        PageHelper.startPage(page, limit);
        List<User> users = userService.findAllNormal();
        //使用VO对象返回
        List<UserVO> result=new ArrayList<>();
        users.stream().forEach(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user,userVO);
            result.add(userVO);
        });
        PageInfo<UserVO> pageInfo = new PageInfo<>(result);

        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", result);
        //map.put("code", 0);
        //map.put("msg", "");
        //map.put("count", menus.getTotalElements());
        //map.put("data", menus.getContent());
        return map;

    }

    @PostMapping
    @ResponseBody
    public UserVO add(User user) {
        CustomPasswordEncoder customPasswordEncoder = new CustomPasswordEncoder();
        user.setPassword(customPasswordEncoder.encode(user.getPassword()));
        user = userService.add(user);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    @GetMapping("/{type}/{id}")
    public String findByTypeAndId(@PathVariable("type") String type, @PathVariable("id") String id, Map map) {
        String url = "";
        if (type != null && type.equalsIgnoreCase("edit")) {
            url = "user/edit";
        } else {
            url = "user/view";
        }
        User user = userService.findById(id);
        map.put("data", UserConvert.user2UserVo(user));
        return url;
    }

    @PutMapping
    @ResponseBody
    public Object updateById(User user) {
        user.setModifyDate(UtilDateTime.getCurrDateTime());
        User User = userService.update(user);
        if (User != null)
            return ResultBody.success();
        else
            return ResultBody.error(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Object deleteById(@PathVariable("id") String id) {
        int count = userService.delete(id);

        if (count > 0)
            return ResultBody.success();
        else
            return ResultBody.error(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    @ResponseBody
    public Object deleteMultipleById( Object[] ids) {

        log.error(ids.toString());

        return null;
        //int count = userService.deleteMultipleById(ids);
        //if (count>0){
        //    return ResultBody.success();
        //}else {
        //    return ResultBody.error();
        //}
    }
    
}
