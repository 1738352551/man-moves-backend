package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.domain.dto.spider.tencent.TencentMoviePullPostRequest;
import cn.chenmanman.manmoviebackend.service.MovieInfoService;
import cn.chenmanman.manmoviebackend.service.MovieSpiderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className MovieSpiderController
 * @description 影视爬虫
 * @date 2023/5/29 12:41
 */

@Api(tags = "视频爬虫管理")
@RestController
@RequestMapping("/movie_info")
@Slf4j
public class MovieSpiderController {
    @Autowired
    private MovieSpiderService movieSpiderService;


    @ApiOperation("腾讯电影抓取")
    @PostMapping("/tencent_pull")
    public CommonResult<?> tencentMoviesPull(@RequestBody TencentMoviePullPostRequest tencentMoviePullPostRequest) {
        movieSpiderService.tencentMoviesPullRun(tencentMoviePullPostRequest);
        return CommonResult.success("腾讯视频爬虫抓取视频启动成功!");
    }


    @ApiOperation("腾讯电影抓取停止")
    @GetMapping("/tencent_pull_stop")
    public CommonResult<?> tencentMoviesPullStop() {
        movieSpiderService.tencentMoviesPullStop();
        return CommonResult.success("腾讯视频爬虫停止成功!");
    }

}
