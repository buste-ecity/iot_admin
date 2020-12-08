package com.buste.iot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author buste
 */
@RestController
public class FirstController {
    @GetMapping("/hello")
    public String firstController(){
        return "Hello IOT!";
    }
}
