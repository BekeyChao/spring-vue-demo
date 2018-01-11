package com.casic.demo.controller;

import com.casic.demo.entity.RestResult;
import com.casic.demo.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    ResultGenerator generator;

    @RequestMapping(value = "/userpost", method = RequestMethod.POST)
    public RestResult userpost() {
        return generator.getSuccessResult();
    }

    @RequestMapping("/userget")
    public RestResult userget() {
        return generator.getSuccessResult();
    }
}
