package com.buste.iot.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author buste
 */
@RestController
@Api(tags = "testController")
public class TestController {
    @ApiOperation("test")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String testController(){
        return "Testing!";
    }
}
