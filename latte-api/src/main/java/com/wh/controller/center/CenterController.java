package com.wh.controller.center;

import com.wh.pojo.Users;
import com.wh.service.center.CenterUserService;
import com.wh.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-13 14:08
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("center")
@Api(value = "用户中心", tags = {"用户中心的接口"})
public class CenterController {


    private CenterUserService centerUserService;

    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    @GetMapping("userInfo")
    public IMOOCJSONResult userInfo(
            @ApiParam(name = "userId", value = "用户id")
            @RequestParam String userId
    ) {
        Users users = centerUserService.queryUserInfo(userId);
        return IMOOCJSONResult.ok(users);
    }



}
