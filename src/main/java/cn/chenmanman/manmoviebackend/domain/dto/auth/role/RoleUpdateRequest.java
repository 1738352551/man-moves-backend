package cn.chenmanman.manmoviebackend.domain.dto.auth.role;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.auth.role
 * @className RoleUpdateRequest
 * @description 角色修改请求
 * @date 2023/6/7 0:07
 */
@Data
public class RoleUpdateRequest {

    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String roleKey;

    private String status;
    private Integer orderBy;
    private List<Long> menus;
}
