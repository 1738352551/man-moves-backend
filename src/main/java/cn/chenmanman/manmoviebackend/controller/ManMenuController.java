package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.auth.menu.MenuAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.menu.MenuQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.menu.MenuUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleQueryRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManMenuEntity;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import cn.chenmanman.manmoviebackend.domain.vo.auth.MenuTableVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RoleTableVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RouterTreeVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RouterVO;
import cn.chenmanman.manmoviebackend.service.ManMenuService;
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
    @PostMapping("/list/page")
    public CommonResult<?> listMenuByPage(@Validated @RequestBody MenuQueryRequest menuQueryRequest) {
        long current = menuQueryRequest.getCurrent();
        long size = menuQueryRequest.getPageSize();
        // 构造查询条件
        Page<ManMenuEntity> manMenuPage = manMenuService.page(new Page<>(current, size), manMenuService.getQueryWrapper(menuQueryRequest));
        List<MenuTableVO> menuTableVOList = manMenuPage.getRecords().stream().map(manMenuEntity -> {
            MenuTableVO menuTableVO = new MenuTableVO();
            BeanUtils.copyProperties(manMenuEntity, menuTableVO);
            return menuTableVO;
        }).collect(Collectors.toList());
        return CommonResult.success(menuTableVOList);
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
}
