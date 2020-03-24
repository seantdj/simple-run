package com.zongs365.run.simplerun.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @author tengdj
 * @date 2020/3/20 14:32
 **/
@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "restart";
    }

    @GetMapping("websocket")
    public String wb() {
        return "restart-wb";
    }

    @ResponseBody
    @RequestMapping("/restart/{name}")
    public String restart(@PathVariable("name") String name) {
        String cmd = "/deploy/" + name + "/start-api.sh";
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd}, null, null);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null) {
                log.info(line);
                stringBuilder.append(line).append("\r\n");
            }
        } catch (Exception e) {
            for (StackTraceElement ee : e.getStackTrace()){
                stringBuilder.append(ee.toString()).append("\r\n");
            }
        }
        return stringBuilder.toString();
    }

}
