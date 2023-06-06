package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.auth.user.UserAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.user.UserLoginRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.user.UserQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.user.UserUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManUserEntity;
import cn.chenmanman.manmoviebackend.domain.vo.auth.UserTableVO;
import cn.chenmanman.manmoviebackend.service.ManUserService;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    @ApiOperation("分页查询用户")
    @PostMapping("/list/page")
    public CommonResult<?> listUserByPage(@Validated @RequestBody UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        // 构造查询条件
        Page<ManUserEntity> manUserPage = manUserService.page(new Page<>(current, size), manUserService.getQueryWrapper(userQueryRequest));
        List<UserTableVO> userTableVOList = manUserPage.getRecords().stream().map(manUserEntity -> {
            UserTableVO userTableVO = new UserTableVO();
            BeanUtils.copyProperties(manUserEntity, userTableVO);
            return userTableVO;
        }).collect(Collectors.toList());
        return CommonResult.success(userTableVOList);
    }


    @PostMapping("/add")
    public CommonResult<?> addUser(@Validated @RequestBody UserAddRequest userAddRequest) {
        Optional.ofNullable(userAddRequest).orElseThrow(() -> new BusinessException("请求参数不能为空!", 500L));
        manUserService.addUser(userAddRequest);
        return CommonResult.success("添加用户成功!");
    }

    @PostMapping("/update")
    public CommonResult<?> updateUser(@Validated @RequestBody UserUpdateRequest userUpdateRequest) {
        Optional.ofNullable(userUpdateRequest).orElseThrow(() -> new BusinessException("请求参数不能为空!", 500L));
        manUserService.updateUser(userUpdateRequest);
        return CommonResult.success("修改用户成功!");
    }

    @GetMapping("/{id}")
    public CommonResult<?> getUserById(@PathVariable Long id) {
        Optional.ofNullable(id).orElseThrow(() -> new BusinessException("id不能为空!", 500L));
        ManUserEntity user = manUserService.getById(id);
        UserTableVO userTableVO = new UserTableVO();
        BeanUtils.copyProperties(user, userTableVO);
        return CommonResult.success(userTableVO);
    }

    @PostMapping("/delete")
    public CommonResult<?> deleteUser(@RequestBody List<Long> ids) {
        Optional.ofNullable(ids).orElseThrow(() -> new BusinessException("请求参数不能为空!", 500L));
        if (ids.isEmpty()) { throw new BusinessException("至少有一个id!", 500L); }
        manUserService.removeUserByIds(ids);
        return CommonResult.success();
    }
}
