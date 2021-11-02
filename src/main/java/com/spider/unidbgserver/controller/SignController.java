package com.spider.unidbgserver.controller;

import com.spider.unidbgserver.dto.QiangdongSignDTO;
import com.spider.unidbgserver.service.DouyinSignService;
import com.spider.unidbgserver.service.QiangdongSignService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
1`
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

    @RequestMapping(value = "doSign", method = {RequestMethod.GET, RequestMethod.POST})
    public String dySignNext(@RequestBody QiangdongSignDTO qiangdongSignDTO) {
        return qiangdongSignService.sign(qiangdongSignDTO);
    }
    @Resource
    private DouyinSignService douyinSignService;
    @Resource
    private QiangdongSignService qiangdongSignService;

}