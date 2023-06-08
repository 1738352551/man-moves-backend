package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import cn.chenmanman.manmoviebackend.domain.entity.movie.EpisodesEntity;
import cn.chenmanman.manmoviebackend.domain.vo.PageResult;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RoleTableVO;
import cn.chenmanman.manmoviebackend.service.ManRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
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
 * @className RoleController
 * @description 角色接口
 * @date 2023/6/6 12:29
 */
@Api(tags = "角色接口")
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

    @ApiOperation("分页查询角色")
    @PostMapping("/list/page")
    public CommonResult<?> listRoleByPage(@Validated @RequestBody RoleQueryRequest roleQueryRequest) {
        long current = roleQueryRequest.getCurrent();
        long size = roleQueryRequest.getPageSize();
        // 构造查询条件
        Page<ManRoleEntity> manRolePage = roleService.page(new Page<>(current, size), roleService.getQueryWrapper(roleQueryRequest));
        List<RoleTableVO> roleTableVOList = manRolePage.getRecords().stream().map(manRoleEntity -> {
            RoleTableVO roleTableVO = new RoleTableVO();
            BeanUtils.copyProperties(manRoleEntity, roleTableVO);
            return roleTableVO;
        }).collect(Collectors.toList());

        PageResult<RoleTableVO> pageResult = new PageResult<>();
        pageResult.setList(roleTableVOList);
        pageResult.setTotal(manRolePage.getTotal());
        pageResult.setCurrent(current);
        pageResult.setSize(manRolePage.getSize());
        pageResult.setPages(manRolePage.getPages());
        return CommonResult.success(pageResult);
    }

    @ApiOperation("添加角色")
    @PostMapping("/add")
    public CommonResult<?> addRole(@Validated @RequestBody RoleAddRequest roleAddRequest) {
        roleService.addRole(roleAddRequest);
        return CommonResult.success();
    }

    @ApiOperation("修改角色")
    @PostMapping("/update")
    public CommonResult<?> updateRole(@Validated @RequestBody RoleUpdateRequest roleUpdateRequest) {
        roleService.updateRole(roleUpdateRequest);
        return CommonResult.success();
    }

    @ApiOperation("删除角色")
    @PostMapping("/delete")
    public CommonResult<?> deleteRole(@RequestBody List<Long> ids) {
        roleService.deleteRole(ids);
        return CommonResult.success();
    }

    @ApiOperation("获取指定角色的信息")
    @GetMapping("/{id}")
    public CommonResult<?> getRole(@PathVariable Long id) {
        return CommonResult.success(roleService.getById(id));
    }

    @ApiOperation("获取指定角色可操作的菜单")
    @GetMapping("/menu/{id}")
    public CommonResult<?> getRoleActionableMenus(@PathVariable Long id) {
        Optional.ofNullable(id).orElseThrow(() -> new RuntimeException("角色id不能为空"));
        return CommonResult.success(roleService.getRoleActionableMenus(id));
    }
}
