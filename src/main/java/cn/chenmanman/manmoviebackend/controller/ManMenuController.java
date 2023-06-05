package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RouterTreeVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RouterVO;
import cn.chenmanman.manmoviebackend.service.ManMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

}
