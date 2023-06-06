package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.domain.dto.auth.user.UserAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.user.UserLoginRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.user.UserQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.user.UserUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManUserEntity;
import cn.chenmanman.manmoviebackend.domain.vo.auth.UserInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service
 * @className ManUserService
 * @description 用户服务
 * @date 2023/6/3 21:27
 */
public interface ManUserService extends IService<ManUserEntity> {

    /**
     * @description 用户登录
     * */
    String login(UserLoginRequest userLoginRequest);

    UserInfoVO info();

    void logout();

    /**
     * 查询条件
     * */
    LambdaQueryWrapper<ManUserEntity> getQueryWrapper(UserQueryRequest userQueryRequest);

    void addUser(UserAddRequest userAddRequest);

    void updateUser(UserUpdateRequest userUpdateRequest);

    void removeUserByIds(List<Long> ids);
}
