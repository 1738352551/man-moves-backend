package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.utils.RedisUtil;
import cn.chenmanman.manmoviebackend.domain.dto.auth.UserLoginRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManUserEntity;
import cn.chenmanman.manmoviebackend.mapper.*;
import cn.chenmanman.manmoviebackend.service.ManUserService;
import cn.chenmanman.manmoviebackend.common.utils.TokenUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service.impl
 * @className ManUserServiceImpl
 * @description TODO
 * @date 2023/6/3 21:28
 *  这里需要实现UserDetailsService接口
 */
@Service
public class ManUserServiceImpl extends ServiceImpl<ManUserMapper, ManUserEntity> implements ManUserService, UserDetailsService {

    @Resource
    private ManUserRoleMapper manUserRoleMapper;


    @Resource
    private ManRoleMenuMapper manRoleMenuMapper;

    @Resource
    private ManMenuMapper manMenuMapper;

    /**
     * 认证需要用到认证管理器
     * */
    @Resource
    private AuthenticationManager authenticationManager;


    @Resource
    private RedisUtil redisUtil;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 根据用户名查询单个用户信息
        ManUserEntity selectedUser = this.baseMapper.selectOne(new LambdaQueryWrapper<ManUserEntity>().eq(ManUserEntity::getUsername, s));
        Optional.ofNullable(selectedUser).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        // 权限
        List<String>  authorities= this.baseMapper.getUserAuthoritiesByUserId(selectedUser.getId());
        selectedUser.setPermissions(authorities);
        return selectedUser;
    }

    /**
     * @param userLoginRequest
     * @description 用户登录
     */
    @Override
    public String login(UserLoginRequest userLoginRequest) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        Optional.ofNullable(authenticate).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        ManUserEntity principal = (ManUserEntity)authenticate.getPrincipal();
        String userId = String.valueOf(principal.getId());
        String redisKey ="loginUser:" + userId;
        redisUtil.set(redisKey, principal);
        return TokenUtil.createJWT(userId);
    }
}
