package cn.chenmanman.manmoviebackend.domain.vo.auth;

import lombok.Data;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo.auth
 * @className RoleTableVO
 * @description TODO
 * @date 2023/6/7 0:03
 */
@Data
public class RoleTableVO {
    private Long id;
    /**
     * 角色名
     * */
    private String name;
    /**
     * 角色标识
     * */
    private String roleKey;
    /**
     * 角色状态
     * */
    private Integer status;

    private Integer orderBy;
}
