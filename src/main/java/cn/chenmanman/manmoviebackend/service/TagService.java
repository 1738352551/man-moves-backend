package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.domain.dto.movie.tag.TagAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tag.TagQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tag.TagUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.TagEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 17383
* @description 针对表【tag】的数据库操作Service
* @createDate 2023-05-05 15:28:18
*/
public interface TagService extends IService<TagEntity> {

    void addTag(TagAddRequest tagAddRequest);

    void updateTag(TagUpdateRequest tagUpdateRequest);

    void removeTag(List<Long> ids);

    Object getTagById(Long id);

    LambdaQueryWrapper<TagEntity> getQueryWrapper(TagQueryRequest tagQueryRequest);
}
