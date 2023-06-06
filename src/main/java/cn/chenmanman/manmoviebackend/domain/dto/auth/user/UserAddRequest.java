package cn.chenmanman.manmoviebackend.domain.dto.auth.user;

import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.auth
 * @className UserAddRequest
 * @description 添加用户请求体
 * @date 2023/6/6 12:33
 */
@Data
public class UserAddRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String nickname;
    private String email;
    private String avatar;

    private Integer gender;
    private Integer userStatus;
    private List<Long> roles;
}
