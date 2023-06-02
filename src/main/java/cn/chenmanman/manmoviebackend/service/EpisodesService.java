package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.domain.dto.movie.episodes.EpisodesAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.episodes.EpisodesQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.episodes.EpisodesUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.movie.EpisodesEntity;
import cn.chenmanman.manmoviebackend.domain.vo.movie.EpisodesVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 17383
* @description 针对表【episodes】的数据库操作Service
* @createDate 2023-05-05 15:28:18
*/
public interface EpisodesService extends IService<EpisodesEntity> {

    /**
     * @description 分页查询的条件
     * @return Wrapper<EpisodesEntity> mybatisplus的条件构造器
     * */
    LambdaQueryWrapper<EpisodesEntity> getQueryWrapper(EpisodesQueryRequest episodesQueryRequest);

    void addMovieEpisodes(EpisodesAddRequest episodesAddRequest);

    void updateMovieEpisodes(EpisodesUpdateRequest episodesUpdateRequest);

    EpisodesVO getMovieEpisodesById(Long id);

    void removeMovieEpisodes(List<Long> ids);

    void assignEpisodesToMovie(Long movieId, Long episodesId);
}
