package cn.chenmanman.manmoviebackend.domain.dto.auth.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.auth
 * @className UserLoginRequest
 * @description 用户登录请求
 * @date 2023/6/3 22:44
 */
@Data
public class UserLoginRequest {
    /**
     * 账号
     * */
    @NotBlank(message = "账号不能为空")
    private String username;

    /**
     * 密码
     * */
    @NotBlank(message = "密码不能为空")
    private String password;
}
