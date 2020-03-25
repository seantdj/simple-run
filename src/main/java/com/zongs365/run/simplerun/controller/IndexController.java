package com.zongs365.run.simplerun.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author tengdj
 * @date 2020/3/20 14:32
 **/
@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping({"/", "/index", "websocket"})
    public String wb() {
        return "restart-wb";
    }

}
