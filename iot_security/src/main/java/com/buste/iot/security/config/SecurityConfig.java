package com.buste.iot.security.config;


import com.buste.iot.security.component.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;


/**
 * @author buste
 */

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Resource
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(HttpSecurity http) throws Exception {
            http.headers().cacheControl().and().frameOptions().disable().and().cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    //测试时全部运行访问
//                    .antMatchers("/**").permitAll()
                    // 允许对于网站静态资源的无授权访问
                    .antMatchers(HttpMethod.GET,
                            "/",
                            "/*.html",
                            "/favicon.ico",
                            "/**/*.html",
                            "/**/*.css",
                            "/**/*.js",
                            "/swagger-ui/**",
                            "/swagger-resources/**",
                            "/v3/api-docs/**"
                    )
                    .permitAll()
                    // 对登录注册要允许匿名访问
                    .antMatchers("/admin/login", "/admin/register")
                    .permitAll()
                    //跨域请求会先进行一次options请求
                    .antMatchers(HttpMethod.OPTIONS)
                    .permitAll()
                    // 除上面外的所有请求全部需要鉴权认证
                    .anyRequest()
                    .authenticated()
               .and()
               .exceptionHandling()
                   .accessDeniedHandler(restfulAccessDeniedHandler)
                   .authenticationEntryPoint(restAuthenticationEntryPoint)
               .and()
                   .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
//        return new JwtAuthenticationTokenFilter();
//    }
//
//    @Bean
//    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
//        return new RestfulAccessDeniedHandler();
//    }
//
//    @Bean
//    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
//        return new RestAuthenticationEntryPoint();
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
