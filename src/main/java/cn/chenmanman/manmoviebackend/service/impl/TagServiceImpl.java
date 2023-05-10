package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.chenmanman.manmoviebackend.domain.entity.TagEntity;
import cn.chenmanman.manmoviebackend.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author 17383
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2023-05-05 15:28:18
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity>
    implements TagService {

}




