package com.buste.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author buste
 */
//@ComponentScan({"com.buste.iot.common","com.buste.iot.security"})
@SpringBootApplication
public class IotAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(IotAdminApplication.class,args);
    }
}
