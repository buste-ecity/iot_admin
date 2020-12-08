package com.buste.iot.security.component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author buste
 */
@Component
public class MyPasswordEncoder extends BCryptPasswordEncoder{

}
