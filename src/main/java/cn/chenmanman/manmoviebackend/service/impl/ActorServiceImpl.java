package cn.chenmanman.manmoviebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.chenmanman.manmoviebackend.domain.entity.ActorEntity;
import cn.chenmanman.manmoviebackend.service.ActorService;
import cn.chenmanman.manmoviebackend.mapper.ActorMapper;
import org.springframework.stereotype.Service;

/**
* @author 17383
* @description 针对表【actor】的数据库操作Service实现
* @createDate 2023-05-05 15:28:18
*/
@Service
public class ActorServiceImpl extends ServiceImpl<ActorMapper, ActorEntity>
    implements ActorService{

}




