package cn.chenmanman.manmoviebackend.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: man-moves-backend
 * @package: cn.chenmanman.manmoviebackend.controller
 * @className: TestController
 * @author: 陈慢慢
 * @description: TODO
 * @date: 2023/5/1 17:26
 * @version: 1.0
 */
@RestController
@Api(value = "用户管理api", tags = "用户管理")
@RequestMapping("/user")
public class TestController {

    @ApiOperation(value = "测试1")
    @GetMapping
    public String test() {
        return "123123zc";
    }
}
