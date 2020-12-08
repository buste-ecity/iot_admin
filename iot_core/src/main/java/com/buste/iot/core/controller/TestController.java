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
    public CommonResult fakeLogin(@RequestBody TestAdmin testAdminParam) {//, BindResult result
        String token = testService.fakeLogin(testAdminParam.getUsername(),testAdminParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }
}
