package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.common.utils.RedisUtil;
import cn.chenmanman.manmoviebackend.domain.dto.auth.UserLoginRequest;
import cn.chenmanman.manmoviebackend.domain.entity.BaseEntity;
import cn.chenmanman.manmoviebackend.domain.entity.auth.*;
import cn.chenmanman.manmoviebackend.domain.vo.auth.ActionEntityVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.PermissionVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.RoleInfoVO;
import cn.chenmanman.manmoviebackend.domain.vo.auth.UserInfoVO;
import cn.chenmanman.manmoviebackend.mapper.*;
import cn.chenmanman.manmoviebackend.service.ManUserService;
import cn.chenmanman.manmoviebackend.common.utils.TokenUtil;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Resource
    private ManRoleMapper manRoleMapper;

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
        // 前端md5加密了密码
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

    /**
     * 获取当前登录用户的信息
     * */
    @Override
    public UserInfoVO info() {
        // 当前登录的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional.ofNullable(authentication).orElseThrow(() -> new BusinessException("请先登录!", 401L));
        ManUserEntity principal = (ManUserEntity) authentication.getPrincipal();
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(principal, userInfoVO);
//        // 1. 整理为UserInfoVO
//        // 1.1 根据authentication查到对应角色
        // 用户所拥有的所有角色身份
        List<ManRoleEntity> userRoleAllList = this.baseMapper.getUserHaveRoleByUserId(principal.getId());
        // 给每个角色都加上所拥有的权限
        List<RoleInfoVO> roleList = userRoleAllList.stream().map(manRoleEntity -> {
            RoleInfoVO roleInfoVO = new RoleInfoVO();
            BeanUtils.copyProperties(manRoleEntity, roleInfoVO);
            // 当前角色的所有可操作的菜单
            List<ManMenuEntity> menusByRoleId = manRoleMapper.getMenusByRoleId(manRoleEntity.getId());
            List<PermissionVO> permission =  menusByRoleId.stream().map(manMenu -> {
                PermissionVO permissionVO = new PermissionVO();
                permissionVO.setRoleId(manRoleEntity.getRoleKey());
                permissionVO.setPermissionId(manMenu.getPath());
                permissionVO.setPermissionName(manMenu.getTitle());
                permissionVO.setActionEntitySet(manRoleMapper.getButtonsByRoleId(manRoleEntity.getId()).stream().map(manMenu1 -> {
                    ActionEntityVO actionEntityVO = new ActionEntityVO();
                    actionEntityVO.setAction(manMenu1.getPermission());
                    actionEntityVO.setDescribe(manMenu1.getTitle());
                    return actionEntityVO;
                }).collect(Collectors.toList()));
                return permissionVO;
            }).collect(Collectors.toList());
            roleInfoVO.setPermissions(permission);
            return roleInfoVO;
        }).collect(Collectors.toList());
        userInfoVO.setRole(roleList.get(0));
        return userInfoVO;
    }

    @Override
    public void logout() {
        ManUserEntity  principal = (ManUserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional.ofNullable(principal).orElseThrow(()-> new BusinessException("用户未登录!", 500L));
        Long userId = principal.getId();
        String redisKey = "loginUser:" + userId;
        // 删除redis中存储的用户信息
        redisUtil.del(redisKey);
        // 清除SecurityContextHolder
        SecurityContextHolder.clearContext();
    }

    /**
     * 获取子节点
     *
     * @param id      父节点id
     * @param menu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<Map<String, Object>> getChild(String id, List<ManMenuEntity> menus) {
        List<Map<String, Object>> childList = new ArrayList<>();
        for (ManMenuEntity entity : menus) {
            if (entity.getParentId().toString().equals(id)) {
                childList.add(MapUtil.builder(new HashMap<String, Object>()).put("id", entity.getId()).put("action", entity.getPermission()).put("describe", entity.getTitle()).build());
            }
        }
        //递归
        for (Map<String, Object> entity : childList) {
            entity.put("actionEntitySet", getChild(entity.get("id").toString(), menus));
        }
        //Collections.sort(childList,order()); //排序
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }
}
