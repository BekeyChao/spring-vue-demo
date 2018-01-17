package com.casic.demo.controller;

import com.casic.demo.entity.RestResult;
import com.casic.demo.entity.ResultCode;
import com.casic.demo.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公共资源控制器
 */
@RequestMapping("/public")
@RestController
public class PublicController {
    private final ResultGenerator generator;

    @Autowired
    public PublicController(ResultGenerator generator) {
        this.generator = generator;
    }

    @RequestMapping("/unauthorized")
    public RestResult unauthorized() {
        return generator.getFreeResult(ResultCode.UNAUTHORIZED, "用户未登录", null);
    }
}
