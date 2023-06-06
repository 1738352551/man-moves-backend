package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import cn.chenmanman.manmoviebackend.service.ManRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className RoleController
 * @description TODO
 * @date 2023/6/6 12:29
 */
@RequestMapping("/role")
@RestController
public class RoleController {
    @Resource
    private ManRoleService roleService;
    @RequestMapping("/list")
    public CommonResult<?> getRoleList() {
        List<ManRoleEntity> list = roleService.list(new LambdaQueryWrapper<ManRoleEntity>().eq(ManRoleEntity::getStatus, 1));
        return CommonResult.success(list);
    }
}
