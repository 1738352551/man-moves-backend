package cn.chenmanman.manmoviebackend.mapper;

import cn.chenmanman.manmoviebackend.domain.entity.movie.MovieTagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.mapper
 * @className MovieTagMapper
 * @description 影视与标签的中间表
 * @date 2023/5/31 0:06
 */
@Mapper
public interface MovieTagMapper extends BaseMapper<MovieTagEntity> {
}
