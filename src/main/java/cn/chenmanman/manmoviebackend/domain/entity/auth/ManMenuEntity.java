package cn.chenmanman.manmoviebackend.domain.entity.auth;

import cn.chenmanman.manmoviebackend.domain.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.entity.auth
 * @className ManMenuEntity
 * @description 菜单实体类
 * @date 2023/6/3 20:36
 */
@Data
@TableName("man_menu")
@EqualsAndHashCode(callSuper = true)
public class ManMenuEntity extends BaseEntity {
    /**
     * 父菜单id
     * */
    private Long parentId;

    /**
     * 菜单名
     * */
    private String title;

    /**
     * 路由
     * */
    private String path;

    /**
     * 菜单标识
     * */
    private String menuKey;

    /**
     * 重定向地址
     * */
    private String redirect;


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
