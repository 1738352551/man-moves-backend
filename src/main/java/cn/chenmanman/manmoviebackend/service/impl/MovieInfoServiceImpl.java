package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.movieinfo.MovieInfoAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.movieinfo.MovieInfoUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.*;
import cn.chenmanman.manmoviebackend.domain.entity.movie.*;
import cn.chenmanman.manmoviebackend.domain.vo.movie.MovieInfoVO;
import cn.chenmanman.manmoviebackend.mapper.*;
import cn.chenmanman.manmoviebackend.service.MovieInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.Optional;
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

    @Resource(type = MovieInfoServiceImpl.class)
    private MovieInfoServiceImpl proxy;


    /**
     * @description 添加一部影视
     *
     * */
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
        // 避免事务控制范围过大对数据库造成大的压力，在非事务方法中使用事务方法不能直接调
        // 解决方法:
        // 1. 使用AopContent 类
        // 2. 在service中注入自己 -> 本方法中使用的, 不会发生循环依赖，因为ioc内部的三级缓存解决的就是这个问题.
        proxy.addMovieInfo_ext(movieInfoAddRequest);
    }

    @Transactional
    public void addMovieInfo_ext(MovieInfoAddRequest movieInfoAddRequest) {
        // 2.3 向影视信息表插入
        MovieInfoEntity movieInfoEntity = new MovieInfoEntity();
        BeanUtils.copyProperties(movieInfoAddRequest, movieInfoEntity);
        movieInfoEntity.setScore(0);
        movieInfoEntity.setViewNum(0L);
        movieInfoEntity.setVideoSource("custom"); // 自己上传
        this.getBaseMapper().insert(movieInfoEntity);
        // 2. 插入movieInfo及一些关联表
        // 2.1 向标签与影视信息的中间表插入
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

    @Override
    public void removeMovieInfo(List<Long> ids) {
        ids.forEach(movieId -> {
            // 检查影视关联的剧集数量, 不能删除还有剧集的影视
            List<EpisodesEntity> episodesEntityList = episodesMapper.selectList(new QueryWrapper<EpisodesEntity>().eq("movie_id", movieId));
            if (episodesEntityList!= null && episodesEntityList.size() > 0) {
                throw new BusinessException(String.format("影视还有剧集不能删除, 影视id: %d", movieId), 500L);
            }
            // 1. 检查影视是否存在
            MovieInfoEntity movieInfoEntity = this.getBaseMapper().selectById(movieId);
            if (movieInfoEntity == null) {
                throw new BusinessException(String.format("影视不存在, 影视id: %d", movieId), 500L);
            }
            // 2. 删除影视剧相关信息
            proxy.removeMovieInfo_ext(movieId);
        });
    }
    @Transactional
    public void removeMovieInfo_ext(Long movieId) {
        // 1. 删除相关联的信息
        movieTagMapper.delete(new QueryWrapper<MovieTagEntity>().eq("movie_id", movieId));
        movieActorMapper.delete(new QueryWrapper<MovieActorEntity>().eq("movie_id", movieId));
        // 2. 删除影视信息
        this.getBaseMapper().deleteById(movieId);
    }

    /**
     * @param movieId 影视id
     * @return 影视信息
     * @description 获取影视信息
     */
    @Override
    public MovieInfoVO getMovieInfo(Long movieId) {
        MovieInfoEntity movieInfoEntity = this.baseMapper.selectById(movieId);
        Optional.ofNullable(movieInfoEntity).orElseThrow(()-> new BusinessException(String.format("电影不存在, 电影id: %d", movieId), 500L));
        MovieInfoVO movieInfoVO = new MovieInfoVO();
        BeanUtils.copyProperties(movieInfoEntity, movieInfoVO);
        movieInfoVO.setMovieName(movieInfoEntity.getName());
        movieInfoVO.setMovieId(movieInfoEntity.getId());
        movieInfoVO.setMovieType(movieInfoEntity.getType());
        // 设置影视所关联标签
        getMovieInfo_ext_set_movieTag(movieInfoVO, movieId);
        // 设置影视所关联的演职员信息
        getMovieInfo_ext_set_movieActor(movieInfoVO, movieId);
        return movieInfoVO;
    }
    /**
     * @description 获取影视信息扩展: 设置标签信息
     * */
    private void getMovieInfo_ext_set_movieTag(MovieInfoVO movieInfoVO, Long movieId) {
        List<TagEntity> movieTagEntities = movieTagMapper.selectList(new QueryWrapper<MovieTagEntity>().eq("movie_id", movieId))
                .stream()
                .map(movieTagEntity ->  tagMapper.selectById(movieTagEntity.getTagId())).collect(Collectors.toList());
        movieInfoVO.setTagList(movieTagEntities);
    }
    /**
     * @description 获取影视信息扩展: 设置演职员信息
     * */
    private void getMovieInfo_ext_set_movieActor(MovieInfoVO movieInfoVO, Long movieId) {
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
    }

    /**
     * @param movieId 影视id
     * @return 影视剧集分页
     * @description 根据id获取影视的剧集
     */
    @Override
    public IPage<EpisodesEntity> getMovieInfoEpisodes(PageRequest pageRequest, Long movieId) {
        IPage<EpisodesEntity> page = new Page<>(pageRequest.getCurrent(), pageRequest.getPageSize());
        return episodesMapper.selectPage(page, new QueryWrapper<EpisodesEntity>().eq("movie_id", movieId));
    }

    /**
     * @param movieInfoUpdateRequest 影视更新信息请求参数
     * @description 修改影视信息
     */
    @Override
    public void updateMovieInfo(MovieInfoUpdateRequest movieInfoUpdateRequest) {
        // 1. 检查影视是否存在
        MovieInfoEntity movieInfo = checkExistMovieInfo(movieInfoUpdateRequest.getId());
        // 2. 进行修改
        proxy.updateMovieInfo_ext(movieInfoUpdateRequest, movieInfo);
    }


    /**
     * 修改影视信息扩展: 将不存在于movieInfoUpdateRequest中list的标签删除
     * */
    private void updateMovieInfo_ext_deleteNotInList(MovieInfoUpdateRequest movieInfoUpdateRequest, MovieInfoEntity movieInfo) {
        // 将不存在于movieInfoUpdateRequest中list的标签删除
        movieTagMapper.delete(new LambdaQueryWrapper<MovieTagEntity>()
                .eq(MovieTagEntity::getMovieId, movieInfo.getId())
                .and(c->c.notIn(MovieTagEntity::getTagId, movieInfoUpdateRequest.getTagList())));
        // 将不存在于movieInfoUpdateRequest中list的演职员删除
        movieActorMapper.delete(new LambdaQueryWrapper<MovieActorEntity>()
                .eq(MovieActorEntity::getMovieId, movieInfo.getId())
                .and(c->c.notIn(MovieActorEntity::getActorId, movieInfoUpdateRequest.getActorList())));
    }

    /**
     * @description 添加movieInfoUpdateRequest中不存在于表中的标签
     * */
    private void updateMovieInfo_ext_AddNotInTable(MovieInfoUpdateRequest movieInfoUpdateRequest, MovieInfoEntity movieInfo) {
        // 2.2 添加movieInfoUpdateRequest中不存在于表中的标签
        movieInfoUpdateRequest.getTagList().forEach(tag -> {
            MovieTagEntity movieTagEntity = movieTagMapper.selectOne(new QueryWrapper<MovieTagEntity>()
                    .eq("movie_id", movieInfo.getId())
                    .and(c->c.eq("tag_id", tag)));
            if (movieTagEntity == null) {
                TagEntity tagEntity = tagMapper.selectById(tag);
                // 如果是空抛出异常
                Optional.ofNullable(tagEntity).orElseThrow(() -> new BusinessException(String.format("标签不存在, 标签id: %d", tag), 500L));
                MovieTagEntity movieTagEntity1 = new MovieTagEntity();
                movieTagEntity1.setMovieId(movieInfo.getId());
                movieTagEntity1.setTagId(tagEntity.getId());
                movieTagMapper.insert(movieTagEntity1);
            }
        });

        // 2.3 添加movieInfoUpdateRequest中不存在于表中的标签
        movieInfoUpdateRequest.getActorList().forEach(actor -> {
            MovieActorEntity movieActorEntity = movieActorMapper.selectOne(new QueryWrapper<MovieActorEntity>()
                    .eq("movie_id", movieInfo.getId())
                    .and(c->c.eq("actor_id", actor.getActorId())));
            if (movieActorEntity == null) {
                ActorEntity actorEntity = actorMapper.selectById(actor.getActorId());
                // 如果是空抛出异常
                Optional.ofNullable(actorEntity).orElseThrow(() -> new BusinessException(String.format("演职员不存在, 演职员id: %d", actor.getActorId()), 500L));
                MovieActorEntity movieActorEntity1 = new MovieActorEntity();
                movieActorEntity1.setMovieId(movieInfo.getId());
                movieActorEntity1.setActorId(actorEntity.getId());
                movieActorMapper.insert(movieActorEntity1);
            }
        });
    }

    /**
     * 修改影视信息
     * */
    @Transactional
    public void updateMovieInfo_ext(MovieInfoUpdateRequest movieInfoUpdateRequest, MovieInfoEntity movieInfo) {

        // 2.删除表中不存在于movieInfoUpdateRequest中的以及添加movieInfoUpdateRequest中存不存在于表中的
        // 2.1 删除表中不存在于movieInfoUpdateRequest中的
        updateMovieInfo_ext_deleteNotInList(movieInfoUpdateRequest, movieInfo);

        // 2.2 添加movieInfoUpdateRequest中不存在于表中的标签
        updateMovieInfo_ext_AddNotInTable(movieInfoUpdateRequest, movieInfo);

        BeanUtils.copyProperties(movieInfoUpdateRequest, movieInfo);
        this.baseMapper.updateById(movieInfo);
    }

    /**
     * @description 检查影视信息是否存在
     * */
    private MovieInfoEntity checkExistMovieInfo(Long movieId) {
        MovieInfoEntity movieInfoEntity = this.getBaseMapper().selectById(movieId);
        Optional.ofNullable(movieInfoEntity).orElseThrow(() -> new BusinessException(String.format("电影不存在, 电影id: %d", movieId), 500L));
        return movieInfoEntity;
    }


}




