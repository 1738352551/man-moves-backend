package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.dto.auth.UserLoginRequest;
import cn.chenmanman.manmoviebackend.service.ManUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return CommonResult.success(manUserService.login(userLoginRequest));
    }
}
