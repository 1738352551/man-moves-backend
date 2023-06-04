package cn.chenmanman.manmoviebackend.domain.vo.auth;

import lombok.Data;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo.auth
 * @className PermissionVO
 * @description 权限
 * @date 2023/6/4 21:02
 */
@Data
public class PermissionVO {
//    roleId: 'admin',
//    permissionId: 'dashboard',
//    permissionName: '仪表盘',

    private String roleId;

    private String permissionId;

    private String permissionName;

    private List<ActionEntityVO> actionEntitySet;

}
