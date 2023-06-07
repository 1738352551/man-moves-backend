package cn.chenmanman.manmoviebackend.domain.dto.auth.menu;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.auth.menu
 * @className MenuUpdateRequest
 * @description 菜单修改请求
 * @date 2023/6/7 11:23
 */
@Data
public class MenuUpdateRequest {
    private Long id;
    /**
     * 父菜单id
     * */

    private Long parentId;

    /**
     * 菜单名
     * */
    @NotBlank( message = "菜单标题不能为空!")
    private String title;

    /**
     * 路由
     * */
    @NotBlank( message = "路由地址不能为空!")
    private String path;
    /**
     * 组件地址
     * */
    private String component;

    /**
     * 菜单图标
     * */
    private String icon;

    /**
     * 顺序
     * */
    @NotNull( message = "排序不能为空!")
    private Integer orderBy;

    /**
     * 菜单类型(0:目录, 1:菜单, 2:按钮)
     * */
    private Integer type;

    /**
     * 权限字符串
     * */
    private String permission;
    /**
     * 菜单状态(0:禁止, 1: 正常))
     * */
    private Integer status;
}
