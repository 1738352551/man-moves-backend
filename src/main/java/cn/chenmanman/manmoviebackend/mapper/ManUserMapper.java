package cn.chenmanman.manmoviebackend.mapper;

import cn.chenmanman.manmoviebackend.domain.entity.auth.ManUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.mapper
 * @className ManUserMapper
 * @description 操作用户表
 * @date 2023/6/3 21:20
 */
public interface ManUserMapper extends BaseMapper<ManUserEntity> {

    List<String> getUserAuthoritiesByUserId(Long userId);
}
