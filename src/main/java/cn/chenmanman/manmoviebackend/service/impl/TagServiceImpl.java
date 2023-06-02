package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tag.TagAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tag.TagQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tag.TagUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.movie.ActorEntity;
import cn.chenmanman.manmoviebackend.service.TagService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.chenmanman.manmoviebackend.domain.entity.TagEntity;
import cn.chenmanman.manmoviebackend.mapper.TagMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
* @author 17383
* @description 针对表【tag】的数据库操作Service实现
* @createDate 2023-05-05 15:28:18
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, TagEntity>
    implements TagService {

    @Override
    public void addTag(TagAddRequest tagAddRequest) {
        TagEntity tagEntity = new TagEntity();
        BeanUtils.copyProperties(tagAddRequest, tagEntity);
        this.baseMapper.insert(tagEntity);
    }

    @Override
    public void updateTag(TagUpdateRequest tagUpdateRequest) {
        TagEntity tagEntity = this.baseMapper.selectById(tagUpdateRequest.getId());
        Optional.ofNullable(tagEntity)
                .orElseThrow(() -> new BusinessException(String.format("需要编辑的标签信息不存在!, id: %d", tagUpdateRequest.getId()), 500L));
        BeanUtils.copyProperties(tagUpdateRequest, tagEntity);
        this.baseMapper.updateById(tagEntity);
    }

    @Override
    public void removeTag(List<Long> ids) {
        ids.forEach(id -> {
            TagEntity tagEntity = this.baseMapper.selectById(id);
            Optional.ofNullable(tagEntity)
                    .orElseThrow(() -> new BusinessException(String.format("标签不存在, 演员id: %d", id), 500L));
            this.baseMapper.deleteById(id);
        });
    }

    @Override
    public TagEntity getTagById(Long id) {
        TagEntity tagEntity = this.baseMapper.selectById(id);
        Optional.ofNullable(tagEntity)
                .orElseThrow(() -> new BusinessException(String.format("标签不存在!, id: %d", id), 500L));
        return tagEntity;
    }

    @Override
    public LambdaQueryWrapper<TagEntity> getQueryWrapper(TagQueryRequest tagQueryRequest) {
        // 检查查询条件是否为null
        Optional.ofNullable(tagQueryRequest).orElseThrow(() -> new BusinessException("请求参数不能为空", 500L));
        return new LambdaQueryWrapper<TagEntity>()
                .like(Strings.isNotBlank(tagQueryRequest.getName()),TagEntity::getName, tagQueryRequest.getName());
    }
}




