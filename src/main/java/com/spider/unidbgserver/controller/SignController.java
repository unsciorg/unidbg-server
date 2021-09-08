package com.spider.unidbgserver.controller;

import com.alibaba.fastjson.JSON;
import com.spider.unidbgserver.service.DouyinSignService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/unidbg")
public class SignController {

    @PostMapping(value = "dySignNext")
    public Map<String, String> dySignNext(@RequestParam("url") String url) {
        synchronized (this) {
            Map<String, String> result = douyinSignService.crack(url);
            return result;
        }

    }
    @Resource
    private DouyinSignService douyinSignService;

}