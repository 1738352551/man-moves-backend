package cn.chenmanman.manmoviebackend.domain.vo.auth;

import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.vo.auth
 * @className UserInfoVO
 * @description TODO
 * @date 2023/6/4 17:43
 */
@Data
public class UserInfoVO {
    /**
     * 用户名
     * */
    private String username;


    /**
     * 邮箱
     * */
    private String email;


    /**
     * 头像
     * */
    private String avatar;
    /**
     *
     * 昵称
     * */
    private String nickname;

    /**
     * 性别
     * */
    private Integer gender;


    /**
     * 上次登录ip
     * */
    private String lastLoginIp;

    /**
     * 上次登录时间
     * */
    private Date lastLoginDate;

    /**
     * 角色
     * */
    private RoleInfoVO role;
}
