package com.lazydsr.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * LoginController
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.controller
 * Created by Lazy on 2018/3/18 14:46
 * Version: 0.1
 * Info: @TODO:...
 */
@Controller
@RequestMapping("/")
public class LoginController {
    @RequestMapping(value = {"/login"})
    public String login() {

        return "login/login";
    }

    @RequestMapping(value = {"/logou"})
    public String logout() {

        return "login/login";
    }
}
