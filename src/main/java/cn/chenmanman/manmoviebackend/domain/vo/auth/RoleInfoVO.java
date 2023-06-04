package cn.chenmanman.manmoviebackend.domain.vo.auth;

import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo.auth
 * @className RoleInfoVO
 * @description 角色信息
 * @date 2023/6/4 17:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleInfoVO extends ManRoleEntity {

    private List<PermissionVO> permissions;

    private static final long serialVersionUID = 1L;
}
