package com.buste.iot.config;

import com.buste.iot.core.service.TestService;
import com.buste.iot.security.config.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import javax.annotation.Resource;


/**
 * @author buste
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AdminSecurityConfig extends SecurityConfig {
    @Resource
    private TestService adminService;

    //    @Bean
    //    public UserDetailsService userDetailsService() {
    //        //获取登录用户信息
    //        return username -> adminService.loadUserByUsername(username);
    //    }
}


