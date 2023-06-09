package cn.chenmanman.manmoviebackend.mapper;

import cn.chenmanman.manmoviebackend.domain.entity.auth.ManMenuEntity;
import cn.chenmanman.manmoviebackend.domain.entity.auth.ManRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.mapper
 * @className ManRoleMapper
 * @description TODO
 * @date 2023/6/3 21:21
 */
public interface ManRoleMapper extends BaseMapper<ManRoleEntity> {
    List<ManMenuEntity> getMenusByRoleId(Long roleId);
    List<ManMenuEntity> getButtonsByRoleId(Long roleId);
}
