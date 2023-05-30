package cn.chenmanman.manmoviebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.chenmanman.manmoviebackend.domain.entity.VideoApiEntity;
import cn.chenmanman.manmoviebackend.service.VideoApiService;
import cn.chenmanman.manmoviebackend.mapper.VideoApiMapper;
import org.springframework.stereotype.Service;

/**
* @author 陈慢慢
* @description 针对表【video_api】的数据库操作Service实现
* @createDate 2023-05-29 12:50:59
*/
@Service
public class VideoApiServiceImpl extends ServiceImpl<VideoApiMapper, VideoApiEntity>
    implements VideoApiService{

}




