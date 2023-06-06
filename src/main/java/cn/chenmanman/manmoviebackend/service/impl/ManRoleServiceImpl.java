package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleQueryRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import cn.chenmanman.manmoviebackend.mapper.ManRoleMapper;
import cn.chenmanman.manmoviebackend.service.ManRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service.impl
 * @className ManRoleServiceImpl
 * @description TODO
 * @date 2023/6/3 21:28
 * 实现层
 */
@Service
public class ManRoleServiceImpl extends ServiceImpl<ManRoleMapper, ManRoleEntity> implements ManRoleService {


    /**
     * 分页查询请求条件构造器
     *
     * @param roleQueryRequest 角色查询请求
     */
    @Override
    public LambdaQueryWrapper<ManRoleEntity> getQueryWrapper(RoleQueryRequest roleQueryRequest) {
        Optional.ofNullable(roleQueryRequest).orElseThrow(()-> new BusinessException("角色查询请求不能为空", 500L));
        return new LambdaQueryWrapper<ManRoleEntity>()
                .like(Strings.isNotBlank(roleQueryRequest.getName()),ManRoleEntity::getName, roleQueryRequest.getName())
                .like(Strings.isNotBlank(roleQueryRequest.getRoleKey()),ManRoleEntity::getRoleKey, roleQueryRequest.getRoleKey())
                .eq(Objects.nonNull(roleQueryRequest.getStatus()), ManRoleEntity::getStatus, roleQueryRequest.getStatus())
                .orderByDesc(ManRoleEntity::getOrderBy);
    }
}
