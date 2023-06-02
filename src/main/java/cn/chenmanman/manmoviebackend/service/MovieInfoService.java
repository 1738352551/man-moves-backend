package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.MovieInfoAddRequest;
import cn.chenmanman.manmoviebackend.domain.entity.EpisodesEntity;
import cn.chenmanman.manmoviebackend.domain.entity.MovieInfoEntity;
import cn.chenmanman.manmoviebackend.domain.vo.movie.MovieInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 17383
* @description 针对表【movie_info】的数据库操作Service
* @createDate 2023-05-05 15:28:18
*/
public interface MovieInfoService extends IService<MovieInfoEntity> {


    void addMovieInfo(MovieInfoAddRequest movieInfoAddRequest);

    /**
     * @description 根据电影id删除电影信息
     * */
    void removeMovieInfo(List<Long> ids);

    /**
     * @description 获取影视信息
     * @param movieId 影视id
     * @return 影视信息
     * */
    MovieInfoVO getMovieInfo(Long movieId);


    /**
     * @description 根据id获取影视的剧集
     * @param movieId 影视id
     * @return 影视剧集分页
     * */
    IPage<EpisodesEntity> getMovieInfoEpisodes(PageRequest pageRequest, Long movieId);
}
