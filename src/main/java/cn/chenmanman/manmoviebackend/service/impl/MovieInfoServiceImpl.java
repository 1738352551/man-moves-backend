package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.service.MovieInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.chenmanman.manmoviebackend.domain.entity.MovieInfoEntity;
import cn.chenmanman.manmoviebackend.mapper.MovieInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 17383
* @description 针对表【movie_info】的数据库操作Service实现
* @createDate 2023-05-05 15:28:18
*/
@Service
public class MovieInfoServiceImpl extends ServiceImpl<MovieInfoMapper, MovieInfoEntity>
    implements MovieInfoService {

}




