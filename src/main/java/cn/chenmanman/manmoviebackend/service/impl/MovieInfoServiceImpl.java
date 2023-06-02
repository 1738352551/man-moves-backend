package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.MovieInfoAddRequest;
import cn.chenmanman.manmoviebackend.domain.entity.*;
import cn.chenmanman.manmoviebackend.domain.vo.movie.MovieInfoVO;
import cn.chenmanman.manmoviebackend.mapper.*;
import cn.chenmanman.manmoviebackend.service.MovieInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 17383
 * @description 针对表【movie_info】的数据库操作Service实现
 * @createDate 2023-05-05 15:28:18
 */
@Service
public class MovieInfoServiceImpl extends ServiceImpl<MovieInfoMapper, MovieInfoEntity>
        implements MovieInfoService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private MovieTagMapper  movieTagMapper;

    @Resource
    private MovieActorMapper movieActorMapper;

    @Resource
    private ActorMapper actorMapper;

    @Resource
    private EpisodesMapper episodesMapper;


    /**
     * @description 添加一部影视
     *
     * */
    @Transactional
    @Override
    public void addMovieInfo(MovieInfoAddRequest movieInfoAddRequest) {
        // 1. 检查 tag是否有不存在的、actor是否也有不存在
        movieInfoAddRequest.getTagId().forEach(tagId -> {
            TagEntity tagEntity = tagMapper.selectById(tagId);
            if (tagEntity == null) {
                throw new BusinessException(String.format("标签不存在, 标签id: %d", tagId), 500L);
            }
        });

        movieInfoAddRequest.getMovieActor().forEach(actor -> {
            ActorEntity actorEntity = actorMapper.selectById(actor.getActorId());
            if (actorEntity == null) {
                throw new BusinessException(String.format("演员不存在, 演员id: %d", actor.getActorId()), 500L);
            }
        });

        // 2.3 向影视信息表插入
        MovieInfoEntity movieInfoEntity = new MovieInfoEntity();
        BeanUtils.copyProperties(movieInfoAddRequest, movieInfoEntity);
        movieInfoEntity.setScore(0);
        movieInfoEntity.setViewNum(0L);
        movieInfoEntity.setVideoSource("custom"); // 自己上传
        this.getBaseMapper().insert(movieInfoEntity);
        // 2. 插入movieInfo及一些关联表
        // 2.1 向标签与影视信息的中间表插入

//        movieTagMapper.insert()
        movieInfoAddRequest.getTagId().forEach(tagId -> {
            MovieTagEntity movieTagEntity = new MovieTagEntity();
            movieTagEntity.setTagId(tagId);
            movieTagEntity.setMovieId(movieInfoEntity.getId());
            movieTagMapper.insert(movieTagEntity);
        });


        // 2.2 向演员与影视信息的中间表插入
        movieInfoAddRequest.getMovieActor().forEach(actor -> {
            ActorEntity actorEntity = actorMapper.selectById(actor.getActorId());
            MovieActorEntity movieActorEntity = new MovieActorEntity();
            movieActorEntity.setMovieId(movieInfoEntity.getId());
            movieActorEntity.setActorId(actorEntity.getId());
            movieActorEntity.setPosts(actor.getPosts());
            movieActorEntity.setCosplayName(actor.getCosplayName());
            movieActorEntity.setCosplayPhoto(actor.getCosplayPhoto());
            movieActorMapper.insert(movieActorEntity);
        });
    }


    /**
     * @param ids 需要删除影视id
     * @description 根据电影id删除电影信息
     */
    @Transactional
    @Override
    public void removeMovieInfo(List<Long> ids) {
        ids.forEach(movieId -> {
            // 1. 检查影视是否存在
            MovieInfoEntity movieInfoEntity = this.getBaseMapper().selectById(movieId);
            if (movieInfoEntity == null) {
                throw new BusinessException(String.format("电影不存在, 电影id: %d", movieId), 500L);
            }
            // 2. 删除相关联的信息
            movieTagMapper.delete(new QueryWrapper<MovieTagEntity>().eq("movie_id", movieId));
            movieActorMapper.delete(new QueryWrapper<MovieActorEntity>().eq("movie_id", movieId));
            // 3. 删除影视信息
            this.getBaseMapper().deleteById(movieId);
        });
    }

    /**
     * @param movieId 影视id
     * @return 影视信息
     * @description 获取影视信息
     */
    @Override
    public MovieInfoVO getMovieInfo(Long movieId) {
        MovieInfoEntity movieInfoEntity = this.baseMapper.selectById(movieId);
        if (movieInfoEntity == null) {
            throw new BusinessException(String.format("电影不存在, 电影id: %d", movieId), 500L);
        }

        MovieInfoVO movieInfoVO = new MovieInfoVO();
        BeanUtils.copyProperties(movieInfoEntity, movieInfoVO);
        movieInfoVO.setMovieName(movieInfoEntity.getName());
        movieInfoVO.setMovieId(movieInfoEntity.getId());
        movieInfoVO.setMovieType(movieInfoEntity.getType());
        // 设置标签
        List<TagEntity> movieTagEntities = movieTagMapper.selectList(new QueryWrapper<MovieTagEntity>().eq("movie_id", movieId))
                .stream()
                .map(movieTagEntity ->  tagMapper.selectById(movieTagEntity.getTagId())).collect(Collectors.toList());

        movieInfoVO.setTagList(movieTagEntities);

        List<Map<String, Object>> movieActorEntities = movieActorMapper.selectList(new QueryWrapper<MovieActorEntity>().eq("movie_id", movieId))
                .stream()
                .map(movieActorEntity -> {
                    ActorEntity actorEntity = actorMapper.selectById(movieActorEntity.getActorId());
                    Map<String, Object> actorMap = new HashMap<>();
                    actorMap.put("actorId", actorEntity.getId());
                    actorMap.put("actorName", actorEntity.getName());
                    actorMap.put("actorCosplayName", movieActorEntity.getCosplayName());
                    actorMap.put("actorCosplayPhoto", movieActorEntity.getCosplayPhoto());
                    actorMap.put("posts", movieActorEntity.getPosts());
                    actorMap.put("actorPhoto", actorEntity.getPhotoUrl());
                    return actorMap;
                }).collect(Collectors.toList());

        movieInfoVO.setActorList(movieActorEntities);
        return movieInfoVO;
    }

    /**
     * @param movieId 影视id
     * @return 影视剧集分页
     * @description 根据id获取影视的剧集
     */
    @Override
    public IPage<EpisodesEntity> getMovieInfoEpisodes(PageRequest pageRequest, Long movieId) {
        IPage<EpisodesEntity> page = new Page<EpisodesEntity>(pageRequest.getCurrent(), pageRequest.getPageSize());
        return episodesMapper.selectPage(page, new QueryWrapper<EpisodesEntity>().eq("movie_id", movieId));
    }


}




