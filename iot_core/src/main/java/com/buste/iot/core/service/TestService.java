package com.buste.iot.core.service;

import com.buste.iot.core.model.TestAdmin;
import org.springframework.stereotype.Service;

/**
 * @author buste
 */
@Service
public class TestService {
    public TestAdmin getAdminByUsername(String username) {
        return new TestAdmin("user","123");
    }
//    public UserDetails loadUserByUsername(String username){
//        return new UserDetails();
//    }
}
