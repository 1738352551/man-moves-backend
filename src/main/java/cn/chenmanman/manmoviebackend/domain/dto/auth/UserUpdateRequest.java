package cn.chenmanman.manmoviebackend.domain.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.auth
 * @className UserUpdateRequest
 * @description 用户修改请求体
 * @date 2023/6/6 12:33
 */
@Data
public class UserUpdateRequest {
    @NotNull(message = "修改用户必须传递id")
    private Long id;
    private String username;
    private String password;

    private String nickname;
    private String email;
    private String avatar;

    private Integer gender;
    private Integer userStatus;
    private List<Long> roles;
}
