package cn.chenmanman.manmoviebackend.domain.dto.auth.role;

import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.auth.role
 * @className RoleQueryRequest
 * @description 角色查询请求
 * @date 2023/6/7 0:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleQueryRequest extends PageRequest {
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
