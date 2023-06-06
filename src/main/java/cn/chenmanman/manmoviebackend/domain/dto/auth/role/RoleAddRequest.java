package cn.chenmanman.manmoviebackend.domain.dto.auth.role;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.auth.role
 * @className RoleAddRequest
 * @description 角色添加请求
 * @date 2023/6/7 0:07
 */
@Data
public class RoleAddRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String roleKey;

    private String status;
    private Integer orderBy;
}
