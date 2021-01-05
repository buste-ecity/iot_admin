package com.buste.iot.core.controller;

import com.buste.iot.common.api.CommonResult;
import com.buste.iot.core.model.TestAdmin;
import com.buste.iot.core.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author buste
 */
@RestController
@Api(tags = "testController")
@RequestMapping("/admin")
public class TestController {

    @Resource
    private TestService testService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @PreAuthorize("hasAnyRole('USER')")
    @ApiOperation("test")
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String testController(){
        return "Testing!";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation("admin")
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public String adminController(){
        return "ADMIN!";
    }

    @ApiOperation("FakeRegister")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResult<TestAdmin> fakeRegister(@RequestBody TestAdmin testAdminParam){//, BindResult result
        TestAdmin testAdmin = testService.fakeRegister(testAdminParam);
        if (testAdmin == null) {
            CommonResult.failed();
        }
        return CommonResult.success(testAdmin);
    }

    @ApiOperation(value = "FakeLogin With Token")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult fakeLogin(HttpServletResponse response, @RequestBody TestAdmin testAdminParam) {//, BindResult result
        String[] tokens = testService.fakeLogin(testAdminParam.getUsername(),testAdminParam.getPassword());
        String token = tokens[0];
        String rToken = tokens[1];
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead", tokenHead);
        response.addHeader("token",token);
        response.addHeader("refresh_token",rToken);
        return CommonResult.success("登录成功！");
    }

    @ApiOperation(value = "Refresh Token")
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    @ResponseBody
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("token");
        String rToken = request.getHeader("refresh_token");
        String[] tokens = testService.rTokenCheck(token,rToken);
//        if (tokens[0] == null || tokens[1] == null) {
//            return CommonResult.validateFailed("用户名或密码错误");
//        }
        response.addHeader("token",tokens[0]);
        response.addHeader("refresh_token",tokens[1]);
    }
}
