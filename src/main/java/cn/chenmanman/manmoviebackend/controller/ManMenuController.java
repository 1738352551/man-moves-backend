package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.auth.menu.MenuAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.menu.MenuQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.menu.MenuUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleQueryRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManMenuEntity;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import cn.chenmanman.manmoviebackend.domain.vo.auth.*;
import cn.chenmanman.manmoviebackend.service.ManMenuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className ManMenuController
 * @description TODO
 * @date 2023/6/5 1:53
 */

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/menu")
public class ManMenuController {

    @Resource
    private ManMenuService manMenuService;

    @ApiOperation("获得antdesignpro的路由")
    @GetMapping("/ant_tree")
    public CommonResult<?> getAntDesignRoutesTree() {
        List<RouterTreeVO> antDesignRoutes = manMenuService.getAntDesignRoutesTree();
        return CommonResult.success(antDesignRoutes);
    }

    @ApiOperation("ant需要的扁平数组")
    @GetMapping("/ant")
    public CommonResult<?> getAntDesignRoutesArr() {
        List<RouterVO> antDesignRoutes = manMenuService.getAntDesignRoutesArr();
        return CommonResult.success(antDesignRoutes);
    }

    @GetMapping("/treeList")
    public CommonResult<?> getMenuTreeList() {
        return CommonResult.success(manMenuService.getMenuTreeList());
    }


    @ApiOperation("分页查询菜单")
    @PostMapping("/list")
    public CommonResult<?> listMenuByPage(@Validated @RequestBody MenuQueryRequest menuQueryRequest) {
        // 所有菜单
        List<ManMenuEntity> manMenuEntities = manMenuService.getBaseMapper().selectList(this.manMenuService.getQueryWrapper(menuQueryRequest));

        // 所有父级菜单
        List<MenuTreeVO> rootMenus = new ArrayList<>();
        for (ManMenuEntity manMenuEntity : manMenuEntities) {
            if (manMenuEntity.getParentId().equals(0L)) {
                MenuTreeVO menuTreeVO = new MenuTreeVO();
                BeanUtils.copyProperties(manMenuEntity, menuTreeVO);
                rootMenus.add(menuTreeVO);
            }
        }

        // 寻找父级菜单的子菜单
        for (MenuTreeVO rootMenu : rootMenus) {
            rootMenu.setChildren(manMenuService.getMenuTreeChildren(rootMenu.getId(), manMenuEntities));
        }

        return CommonResult.success(rootMenus);
    }

    @PostMapping("/add")
    private CommonResult<?> addMenu(@Validated @RequestBody MenuAddRequest menuAddRequest) {
        ManMenuEntity manMenuEntity = new ManMenuEntity();
        BeanUtils.copyProperties(menuAddRequest, manMenuEntity);
        manMenuService.save(manMenuEntity);
        return CommonResult.success();
    }

    @PostMapping("/update")
    private CommonResult<?> updateMenu(@Validated @RequestBody MenuUpdateRequest menuUpdateRequest) {
        ManMenuEntity manMenuEntity = manMenuService.getById(menuUpdateRequest.getId());
        Optional.ofNullable(manMenuEntity).orElseThrow(() -> new BusinessException("菜单不存在", 500L));
        BeanUtils.copyProperties(menuUpdateRequest, manMenuEntity);
        manMenuService.updateById(manMenuEntity);
        return CommonResult.success();
    }

    @PostMapping("/delete")
    private CommonResult<?> deleteMenu(@RequestBody List<Long> ids) {
        manMenuService.deleteMenu(ids);
        return CommonResult.success();
    }

    @GetMapping("/{id}")
    private CommonResult<?> getMenuById(@PathVariable Long id) {
        MenuTreeVO menuTreeVO = manMenuService.getMenu(id);
        return CommonResult.success(menuTreeVO);
    }
}
