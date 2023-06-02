package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.episodes.EpisodesAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.episodes.EpisodesQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.episodes.EpisodesUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.movieinfo.MovieInfoAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.movieinfo.MovieInfoUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.movie.EpisodesEntity;
import cn.chenmanman.manmoviebackend.domain.entity.movie.MovieInfoEntity;
import cn.chenmanman.manmoviebackend.service.EpisodesService;
import cn.chenmanman.manmoviebackend.service.impl.EpisodesServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className MovieEpisodesController
 * @description 影视分集
 * @date 2023/5/30 0:36
 */
@Api(tags="影视剧集相关接口")
@RestController
@RequestMapping("/movie/episodes")
@Slf4j
public class MovieEpisodesController {

    @Resource(type = EpisodesServiceImpl.class)
    private EpisodesService episodesService;

    @ApiOperation(value = "添加影视剧集")
    @PostMapping("/add")
    public CommonResult<?> addMovieEpisodes(@Validated @RequestBody EpisodesAddRequest episodesAddRequest) {
        episodesService.addMovieEpisodes(episodesAddRequest);
        return CommonResult.success();
    }

    @ApiOperation(value = "修改影视信息")
    @PostMapping("/update")
    public CommonResult<?> changeMovieEpisodes(@Validated @RequestBody EpisodesUpdateRequest episodesUpdateRequest) {
        episodesService.updateMovieEpisodes(episodesUpdateRequest);
        return CommonResult.success();
    }

    @ApiOperation(value = "删除影视信息")
    @DeleteMapping("/delete")
    public CommonResult<?> deleteMovieEpisodes(@RequestBody List<Long> ids) {
        if (ids == null && ids.size() == 0) {
            return CommonResult.fail("ids不能为空");
        }
        episodesService.removeMovieEpisodes(ids);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取影视信息")
    @GetMapping("/getMovieEpisodes")
    public CommonResult<?> getMovieEpisodesById(Long id) {
        if (id == null) {
            return CommonResult.fail("id不能为空");
        }

        return CommonResult.success(episodesService.getMovieEpisodesById(id));
    }


    @ApiOperation("分页查询影视剧集")
    @PostMapping("/list/page")
    public CommonResult<?> listMovieEpisodesByPage(@Validated @RequestBody EpisodesQueryRequest episodesQueryRequest) {
        long current = episodesQueryRequest.getCurrent();
        long size = episodesQueryRequest.getPageSize();
        // 构造查询条件
        Page<EpisodesEntity> movieInfoPage = episodesService.page(new Page<>(current, size), episodesService.getQueryWrapper(episodesQueryRequest));
        return CommonResult.success(movieInfoPage);
    }

    @ApiOperation("分配影视剧集给影视")
    @PostMapping("/assign")
    public CommonResult<?> AssignEpisodesToMovie(Long movieId, Long episodesId) {
        if (movieId == null) {
            return CommonResult.fail("movieId不能为空");
        }
        if (episodesId == null) {
            return CommonResult.fail("episodesId不能为空");
        }
        episodesService.assignEpisodesToMovie(movieId, episodesId);
        return CommonResult.success();
    }
}
