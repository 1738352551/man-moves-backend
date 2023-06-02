package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.service.MovieActorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.chenmanman.manmoviebackend.domain.entity.movie.MovieActorEntity;
import cn.chenmanman.manmoviebackend.mapper.MovieActorMapper;
import org.springframework.stereotype.Service;

/**
* @author 17383
* @description 针对表【movie_actor】的数据库操作Service实现
* @createDate 2023-05-05 15:28:18
*/
@Service
public class MovieActorServiceImpl extends ServiceImpl<MovieActorMapper, MovieActorEntity>
    implements MovieActorService {

}




