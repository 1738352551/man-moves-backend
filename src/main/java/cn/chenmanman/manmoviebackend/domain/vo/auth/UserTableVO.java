package cn.chenmanman.manmoviebackend.domain.vo.auth;

import lombok.Data;

import java.util.Date;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo.auth
 * @className UserTableVO
 * @description 用户表VO
 * @date 2023/6/6 10:49
 */
@Data
public class UserTableVO {
    private Long id;
    private String username;

    private String nickname;

    private Integer gender;

    private String avatar;

    private String email;

    private Date lastLoginDate;

    private String lastLoginIp;

    private Integer loginStatus;

    private Integer status;
}
