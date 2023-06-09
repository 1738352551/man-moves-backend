package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.auth.role.RoleUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import cn.chenmanman.manmoviebackend.domain.vo.auth.MenuTreeVO;
import cn.chenmanman.manmoviebackend.mapper.ManRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service
 * @className ManRoleSerivce
 * @description TODO
 * @date 2023/6/3 21:28
 * 接口层 角色
 */
public interface ManRoleService extends IService<ManRoleEntity> {
    /**
     * 分页查询请求条件构造器
     * */
    LambdaQueryWrapper<ManRoleEntity> getQueryWrapper(RoleQueryRequest roleQueryRequest);

    void addRole(RoleAddRequest roleAddRequest);

    void updateRole(RoleUpdateRequest roleUpdateRequest);

    void deleteRole(List<Long> ids);

    List<Long> getRoleActionableMenus(Long id);
}
