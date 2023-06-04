package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.domain.dto.auth.UserLoginRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManUserEntity;
import cn.chenmanman.manmoviebackend.domain.vo.auth.UserInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service
 * @className ManUserService
 * @description TODO
 * @date 2023/6/3 21:27
 */
public interface ManUserService extends IService<ManUserEntity> {

    /**
     * @description 用户登录
     * */
    String login(UserLoginRequest userLoginRequest);

    UserInfoVO info();

    void logout();
}
