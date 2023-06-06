package cn.chenmanman.manmoviebackend.domain.dto.auth.user;

import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.auth
 * @className UserQueryRequest
 * @description 用户查询请求体
 * @date 2023/6/6 10:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryRequest extends PageRequest {
    /**
     * 用户名
     * */
    private String username;
    /**
     * 邮箱
     * */
    private String email;

    /**
     * 性别
     * */
    private Integer gender;

    /**
     * 用户状态
     * */
    private Integer status;

    /**
     * 登录状态
     * */
    private Integer loginStatus;

    /**
     * 昵称
     * */
    private String nickname;
}
