package cn.chenmanman.manmoviebackend.mapper;

import cn.chenmanman.manmoviebackend.domain.entity.movie.MovieInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 17383
* @description 针对表【movie_info】的数据库操作Mapper
* @createDate 2023-05-05 15:28:18
* @Entity cn.chenmanman.manmoviebackend.domain.entity.movie.MovieInfoEntity
*/
@Mapper
public interface MovieInfoMapper extends BaseMapper<MovieInfoEntity> {

}




