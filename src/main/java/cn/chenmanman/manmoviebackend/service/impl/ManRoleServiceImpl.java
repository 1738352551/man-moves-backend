package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManMenuEntity;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleMenuEntity;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManUserRoleEntity;
import cn.chenmanman.manmoviebackend.domain.vo.auth.MenuTreeVO;
import cn.chenmanman.manmoviebackend.mapper.ManMenuMapper;
import cn.chenmanman.manmoviebackend.mapper.ManRoleMapper;
import cn.chenmanman.manmoviebackend.mapper.ManRoleMenuMapper;
import cn.chenmanman.manmoviebackend.mapper.ManUserRoleMapper;
import cn.chenmanman.manmoviebackend.service.ManRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Resource
    private ManRoleMenuMapper manRoleMenuMapper;
    @Resource
    private ManUserRoleMapper manUserRoleMapper;

    @Resource
    private ManMenuMapper manMenuMapper;

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

    @Transactional
    @Override
    public void addRole(RoleAddRequest roleAddRequest) {
        // 1. 添加角色信息
        ManRoleEntity manRoleEntity = new ManRoleEntity();
        BeanUtils.copyProperties(roleAddRequest,manRoleEntity);
        manRoleEntity.setOrderBy(Math.toIntExact(this.baseMapper.selectCount(null)));
        this.baseMapper.insert(manRoleEntity);
        // 2. 添加角色与菜单的关联
        List<Long> menus = roleAddRequest.getMenus();
        if (menus!= null && menus.size() > 0) {
            menus.forEach(menuId -> {
                // 判断菜单是否存在, 如果不存在抛出异常
                ManMenuEntity manMenuEntity = manMenuMapper.selectById(menuId);
                Optional.ofNullable(manMenuEntity).orElseThrow(()-> new BusinessException("菜单不存在", 500L));
                ManRoleMenuEntity manRoleMenuEntity = new ManRoleMenuEntity();
                manRoleMenuEntity.setMenuId(menuId);
                manRoleMenuEntity.setRoleId(manRoleEntity.getId());
                manRoleMenuMapper.insert(manRoleMenuEntity);
            });
        }
    }

    @Transactional
    @Override
    public void updateRole(RoleUpdateRequest roleUpdateRequest) {
        //  1. 检查角色是否存在
        ManRoleEntity manRoleEntity = this.baseMapper.selectById(roleUpdateRequest.getId());
        Optional.ofNullable(manRoleEntity).orElseThrow(()-> new BusinessException("要修改的角色不存在", 500L));
        // 2. 修改角色与菜单之间的关联
        List<Long> menus = roleUpdateRequest.getMenus();
        // 2.1 删除表中有, 但是menus中没有的关联信息
        manRoleMenuMapper.delete(new LambdaQueryWrapper<ManRoleMenuEntity>().eq(ManRoleMenuEntity::getRoleId, manRoleEntity.getId()).in(ManRoleMenuEntity::getMenuId, menus));
        // 2.2 添加menus有, 但是表中没有的关联信息
        menus.forEach(menuId -> {
            // 检查menuId是否存在
            ManMenuEntity manMenuEntity = manMenuMapper.selectById(menuId);
            Optional.ofNullable(manMenuEntity).orElseThrow(()-> new BusinessException("菜单不存在", 500L));
            // 检查表中是否有当前遍历到的菜单, 如果没有就加入
            ManRoleMenuEntity manRoleMenuEntity = manRoleMenuMapper.selectOne(new LambdaQueryWrapper<ManRoleMenuEntity>()
                    .eq(ManRoleMenuEntity::getRoleId, manRoleEntity.getId())
                    .eq(ManRoleMenuEntity::getMenuId, menuId));
            if (Objects.isNull(manRoleMenuEntity)) {
                // 表中没有
                manRoleMenuEntity = new ManRoleMenuEntity();
                manRoleMenuEntity.setMenuId(menuId);
                manRoleMenuEntity.setRoleId(manRoleEntity.getId());
                manRoleMenuMapper.insert(manRoleMenuEntity);
            }
        });
    }

    @Transactional
    @Override
    public void deleteRole(List<Long> ids) {
        if (ids.size() == 0) {
            throw new BusinessException("批量删除必须要有至少一个id", 500L);
        }
        for (Long id : ids) {
            ManRoleEntity manRoleEntity = this.baseMapper.selectById(id);
            if (Objects.isNull(manRoleEntity)) {
                continue;
            }
            // 删除角色与菜单间的关联
            manRoleMenuMapper.delete(new LambdaQueryWrapper<ManRoleMenuEntity>()
                    .eq(ManRoleMenuEntity::getRoleId, manRoleEntity.getId()));
            // 删除角色与用户间的关联
            manUserRoleMapper.delete(new LambdaQueryWrapper<ManUserRoleEntity>()
                   .eq(ManUserRoleEntity::getRoleId, manRoleEntity.getId()));
            this.baseMapper.deleteById(id);
        }
    }

    @Override
    public List<Long> getRoleActionableMenus(Long id) {
        return manRoleMenuMapper.selectList(new LambdaQueryWrapper<ManRoleMenuEntity>()
                        .eq(ManRoleMenuEntity::getRoleId, id)).stream().map(ManRoleMenuEntity::getMenuId)
                .collect(Collectors.toList());
    }
}
