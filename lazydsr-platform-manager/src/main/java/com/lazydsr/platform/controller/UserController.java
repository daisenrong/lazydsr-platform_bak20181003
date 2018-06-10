package com.lazydsr.platform.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lazydsr.commons.result.HttpStatus;
import com.lazydsr.commons.result.ResultBody;
import com.lazydsr.platform.entity.User;
import com.lazydsr.platform.entity.User;
import com.lazydsr.platform.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UserController
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.controller
 * Created by Lazy on 2018/3/8 11:10
 * Version: 0.1
 * Info: @TODO:...
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
        List<User> Users = userService.findAllNormal();
        PageInfo<User> pageInfo = new PageInfo<>(Users);

        map.put("code", 0);
        map.put("msg", "");
        map.put("count", pageInfo.getTotal());
        map.put("data", Users);
        //map.put("code", 0);
        //map.put("msg", "");
        //map.put("count", menus.getTotalElements());
        //map.put("data", menus.getContent());
        return map;

    }

    @PostMapping
    @ResponseBody
    public User add(User User) {
        User job = userService.add(User);
        return job;
    }

    @GetMapping("/{type}/{id}")
    public String findByTypeAndId(@PathVariable("type") String type, @PathVariable("id") String id, Map map) {
        String url = "";
        if (type != null && type.equalsIgnoreCase("edit")) {
            url = "user/edit";
        } else {
            url = "user/view";
        }
        User User = userService.findById(id);
        map.put("data", User);
        return url;
    }

    @PutMapping
    @ResponseBody
    public Object updateById(User job) {
        User User = userService.update(job);
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
