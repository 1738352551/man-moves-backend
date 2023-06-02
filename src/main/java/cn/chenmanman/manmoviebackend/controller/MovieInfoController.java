package cn.chenmanman.manmoviebackend.controller;


import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.movieinfo.MovieInfoAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.movieinfo.MovieInfoQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.movieinfo.MovieInfoUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.movie.MovieInfoEntity;
import cn.chenmanman.manmoviebackend.service.MovieInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className MovieInfoController
 * @description 影视信息接口
 * @date 2023/5/5 15:33
 */

@Api(tags = "影视信息接口")
@RestController
@RequestMapping("/movie_info")
@Slf4j
public class MovieInfoController {

    @Autowired
    private MovieInfoService movieInfoService;

    @ApiOperation(value = "添加影视信息")
    @PostMapping("/add")
    public CommonResult<?> addMovieInfo(@Validated @RequestBody MovieInfoAddRequest movieInfoAddRequest) {
        movieInfoService.addMovieInfo(movieInfoAddRequest);
        return CommonResult.success();
    }

    @ApiOperation(value = "修改影视信息")
    @PostMapping("/update")
    public CommonResult<?> changeMovieInfo(@Validated @RequestBody MovieInfoUpdateRequest movieInfoUpdateRequest) {

        movieInfoService.updateMovieInfo(movieInfoUpdateRequest);


        return CommonResult.success();
    }

    @ApiOperation(value = "删除影视信息")
    @DeleteMapping("/delete")
    public CommonResult<?> deleteMovieInfo(@RequestBody List<Long> ids) {
        movieInfoService.removeMovieInfo(ids);
        return CommonResult.success();
    }

    @ApiOperation(value = "获取影视信息")
    @GetMapping("/getMovieInfo")
    public CommonResult<?> getMovieInfoByMovieId(Long id) {
        if (id == null) {
            return CommonResult.fail("id不能为空");
        }

        return CommonResult.success(movieInfoService.getMovieInfo(id));
    }

    @ApiOperation(value = "获取影视剧集")
    @GetMapping("/getMovieInfoEpisodes")
    public CommonResult<?> getMovieInfoEpisodesById(PageRequest pageRequest, Long id) {
        if (id == null) {
            return CommonResult.fail("id不能为空");
        }

        return CommonResult.success(movieInfoService.getMovieInfoEpisodes(pageRequest, id));
    }

    @ApiOperation(value = "分页查询影视信息")
    @GetMapping("/list/page")
    public CommonResult<?> listMovieInfoByPage(MovieInfoQueryRequest movieInfoQueryRequest) {
        long current = movieInfoQueryRequest.getCurrent();
        long size = movieInfoQueryRequest.getPageSize();
        Page<MovieInfoEntity> movieInfoPage = movieInfoService.page(new Page<>(current, size));
        return CommonResult.success(movieInfoPage);
    }


}
