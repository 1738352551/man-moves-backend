package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.movie.episodes.EpisodesAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.episodes.EpisodesQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.episodes.EpisodesUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.vo.movie.EpisodesVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.chenmanman.manmoviebackend.domain.entity.movie.EpisodesEntity;
import cn.chenmanman.manmoviebackend.service.EpisodesService;
import cn.chenmanman.manmoviebackend.mapper.EpisodesMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
* @author 17383
* @description 针对表【episodes】的数据库操作Service实现
* @createDate 2023-05-05 15:28:18
*/
@Service
public class EpisodesServiceImpl extends ServiceImpl<EpisodesMapper, EpisodesEntity>
    implements EpisodesService{



    /**
     * @param episodesQueryRequest 剧集的查询请求
     * @return Wrapper<EpisodesEntity> mybatisplus的条件构造器
     * @description 分页查询的条件
     */
    @Override
    public LambdaQueryWrapper<EpisodesEntity> getQueryWrapper(EpisodesQueryRequest episodesQueryRequest) {
        // 检查查询条件是否为null
        Optional.ofNullable(episodesQueryRequest).orElseThrow(() -> new BusinessException("请求参数不能为空", 500L));
        return new LambdaQueryWrapper<EpisodesEntity>()
                .eq(Objects.nonNull(episodesQueryRequest.getMovieId()), EpisodesEntity::getMovieId, episodesQueryRequest.getMovieId())
                .like(Strings.isNotBlank(episodesQueryRequest.getTitle()), EpisodesEntity::getTitle, episodesQueryRequest.getTitle())
                .like(Strings.isNotBlank(episodesQueryRequest.getMovieUrl()), EpisodesEntity::getMovieUrl, episodesQueryRequest.getMovieUrl());
    }

    @Override
    public void addMovieEpisodes(EpisodesAddRequest episodesAddRequest) {
        EpisodesEntity episodesEntity = new EpisodesEntity();
        BeanUtils.copyProperties(episodesAddRequest, episodesEntity);
        this.baseMapper.insert(episodesEntity);
    }

    @Override
    public void updateMovieEpisodes(EpisodesUpdateRequest episodesUpdateRequest) {
        EpisodesEntity episodesEntity = this.baseMapper.selectById(episodesUpdateRequest.getId());
        Optional.ofNullable(episodesEntity)
                .orElseThrow(() -> new BusinessException(String.format("需要编辑的影视剧集不存在!, id: %d", episodesUpdateRequest.getId()), 500L));
        BeanUtils.copyProperties(episodesUpdateRequest, episodesEntity);
        this.baseMapper.updateById(episodesEntity);
    }

    @Override
    public EpisodesVO getMovieEpisodesById(Long id) {
        EpisodesEntity episodesEntity = this.baseMapper.selectById(id);
        Optional.ofNullable(episodesEntity)
                .orElseThrow(() -> new BusinessException(String.format("影视剧集不存在!, id: %d", id), 500L));
        EpisodesVO episodesVO = new EpisodesVO();
        BeanUtils.copyProperties(episodesEntity, episodesVO);
        return episodesVO;
    }

    /**
     * @description
     * */
    @Transactional
    @Override
    public void removeMovieEpisodes(List<Long> ids) {
        ids.forEach(id -> {
            EpisodesEntity episodesEntity = this.baseMapper.selectById(id);
            Optional.ofNullable(episodesEntity)
                    .orElseThrow(() -> new BusinessException(String.format("剧集不存在, 剧集: %d", id), 500L));
            this.baseMapper.deleteById(id);
        });
    }

    @Override
    public void assignEpisodesToMovie(Long movieId, Long episodesId) {
        EpisodesEntity episodesEntity = this.baseMapper.selectById(episodesId);
        Optional.ofNullable(episodesEntity)
                .orElseThrow(() -> new BusinessException(String.format("剧集不存在, 剧集: %d", episodesId), 500L));
        episodesEntity.setMovieId(movieId);
        this.baseMapper.updateById(episodesEntity);
    }
}




