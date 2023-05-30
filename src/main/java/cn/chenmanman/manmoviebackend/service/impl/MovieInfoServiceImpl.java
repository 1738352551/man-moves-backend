package cn.chenmanman.manmoviebackend.service.impl;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.movie.MovieInfoAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tencent.TencentMoviePullPostRequest;
import cn.chenmanman.manmoviebackend.domain.entity.*;
import cn.chenmanman.manmoviebackend.mapper.*;
import cn.chenmanman.manmoviebackend.pageprocessor.tencent.TencentVideoPageProcessor;
import cn.chenmanman.manmoviebackend.pageprocessor.tencent.TencentVideoPipeline;
import cn.chenmanman.manmoviebackend.service.MovieInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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

    @Transactional
    @Override
    public void addMovieInfo(MovieInfoAddRequest movieInfoAddRequest) {
        // 1. 检查 tag、actor是否存在
        TagEntity tagEntity = tagMapper.selectById(movieInfoAddRequest.getTagId());
        if (tagEntity == null) {
            throw new BusinessException("标签不存在", 500L);
        }

        ActorEntity actorEntity = actorMapper.selectById(movieInfoAddRequest.getActorId());
        if (actorEntity == null) {
            throw new BusinessException("演员不存在", 500L);
        }

        // 2. 插入movieInfo及一些关联表
        // 2.1 向标签与影视信息的中间表插入
        MovieTagEntity movieTagEntity = new MovieTagEntity();
        movieTagEntity.setTagId(movieInfoAddRequest.getTagId());
        movieTagMapper.insert(movieTagEntity);

        // 2.2 向演员与影视信息的中间表插入
        MovieActorEntity movieActorEntity = new MovieActorEntity();
        movieActorEntity.setActorId(actorEntity.getId());
        movieActorEntity.setPosts(movieInfoAddRequest.getPosts());
        movieActorEntity.setCosplayName(movieInfoAddRequest.getCosplayName());
        movieActorEntity.setCosplayPhoto(movieInfoAddRequest.getCosplayPhoto());
        movieActorMapper.insert(movieActorEntity);

        // 2.3 向影视信息表插入
        MovieInfoEntity movieInfoEntity = new MovieInfoEntity();
        BeanUtils.copyProperties(movieInfoAddRequest, movieInfoEntity);
        movieInfoEntity.setScore(0);
        movieInfoEntity.setViewNum(0L);
        movieInfoEntity.setVideoSource(""); // 自己上传
    }
}




