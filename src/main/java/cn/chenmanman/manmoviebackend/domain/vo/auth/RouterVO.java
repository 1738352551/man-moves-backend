package cn.chenmanman.manmoviebackend.domain.vo.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo.auth
 * @className RouterVO
 * @description TODO
 * @date 2023/6/5 12:51
 */
@Data
public class RouterVO {

    private Long id;

    private Long parentId;

    /**
     * 菜单标题
     * */
    private String name;

    /**
     * 菜单标识
     * */
    private String key;

    private String path;
    private String redirect;
    private boolean hideChildrenInMenu;
    private boolean hidden;

    /**
     * 组件地址
     * */
    private String component;

    private MetaVO meta;
}
