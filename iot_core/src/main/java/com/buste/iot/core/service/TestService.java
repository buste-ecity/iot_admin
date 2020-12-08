package com.buste.iot.core.service;

import com.buste.iot.common.util.JwtTokenUtil;
import com.buste.iot.core.model.TestAdmin;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.Resource;

/**
 * @author buste
 */
@Service
public class TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestService.class);

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private CustomUserDetailsService customUserDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;


    public TestAdmin getAdminByUsername(String username) {
        return new TestAdmin("user","123");
    }
//    public UserDetails loadUserByUsername(String username){
//        return new UserDetails();
//    }
    public TestAdmin fakeRegister(TestAdmin testAdmin){
        TestAdmin newAdmin = new TestAdmin();
        BeanUtils.copyProperties(testAdmin,newAdmin);
        //用户初始化 TODO
        //查询是否有相同用户名的用户 TODO
        newAdmin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
        //ORM增加用户 TODO
        return newAdmin;
    }

    public String fakeLogin(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(password,userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
//            if(!password.equals(userDetails.getPassword())){
//                throw new BadCredentialsException("密码不正确");
//            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            LOGGER.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }
}
