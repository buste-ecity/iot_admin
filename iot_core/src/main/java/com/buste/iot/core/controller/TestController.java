package com.buste.iot.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author buste
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public String testController(){
        return "Testing!";
    }
}
