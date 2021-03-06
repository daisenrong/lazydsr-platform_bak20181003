package com.lazydsr.platform.controller.commons;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * BaseController
 * PROJECT_NAME: lazydsr-platform
 * PACKAGE_NAME: com.lazydsr.platform.controller.commons.browse
 * Created by Lazy on 2018/4/1 22:45
 * Version: 0.1
 * Info: @TODO:...
 */
@Controller
@RequestMapping("/page")
public class PageController {
    @GetMapping("/{page}")
    public String toPage(@PathVariable String page) {
        return page;
    }
    @GetMapping("/{page}/{page1}")
    public String toPage(@PathVariable String page,@PathVariable String page1) {
        return page+"/"+page1;
    }
    @GetMapping("/{page}/{page1}/{page2}")
    public String toPage(@PathVariable String page,@PathVariable String page1,@PathVariable String page2) {
        return page+"/"+page1+"/"+page2;
    }
}
