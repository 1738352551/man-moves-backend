package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.dto.auth.UserLoginRequest;
import cn.chenmanman.manmoviebackend.service.ManUserService;
import cn.hutool.core.map.MapUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className UserController
 * @description 用户相关
 * @date 2023/6/3 22:45
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private ManUserService manUserService;

    @PostMapping("/login")
    public CommonResult<?> login(@Validated @RequestBody UserLoginRequest userLoginRequest) {
        return CommonResult.success("登录成功!", MapUtil.builder().put("token", manUserService.login(userLoginRequest)).build());
    }


    @GetMapping("/info")
    public  CommonResult<?> info() {
        return CommonResult.success("获取用户信息成功!", manUserService.info());
    }


    @GetMapping("/logout")
    public CommonResult<?> logout() {
        manUserService.logout();
        return CommonResult.success("登出成功!");
    }
}
