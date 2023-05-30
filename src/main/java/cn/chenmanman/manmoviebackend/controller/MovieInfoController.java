package cn.chenmanman.manmoviebackend.controller;


import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.dto.movie.MovieInfoAddRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.MovieInfoQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.MovieInfoUpdateRequest;
import cn.chenmanman.manmoviebackend.domain.entity.MovieInfoEntity;
import cn.chenmanman.manmoviebackend.domain.entity.VideoApiEntity;
import cn.chenmanman.manmoviebackend.service.MovieInfoService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
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

    @PostMapping("/add")
    public CommonResult<?> addMovieInfo(@Validated @RequestBody MovieInfoAddRequest movieInfoAddRequest) {
        movieInfoService.addMovieInfo(movieInfoAddRequest);
        return CommonResult.success();
    }

    @PostMapping("/update")
    public CommonResult<?> changeMovieInfo(@Validated @RequestBody MovieInfoUpdateRequest movieInfoUpdateRequest) {
        return CommonResult.success();
    }

    @DeleteMapping("/delete")
    public CommonResult<?> deleteMovieInfo(@RequestBody List<Long> ids) {
        return CommonResult.success();
    }

    @GetMapping("/get")
    public CommonResult<?> getMovieInfoById(@RequestParam Long id) {
        return CommonResult.success();
    }

    @GetMapping("/list/page")
    public CommonResult<?> listMovieInfoByPage(MovieInfoQueryRequest movieInfoQueryRequest) {
        long current = movieInfoQueryRequest.getCurrent();
        long size = movieInfoQueryRequest.getPageSize();
        Page<MovieInfoEntity> movieInfoPage = movieInfoService.page(new Page<>(current, size));
        return CommonResult.success();
    }


}
