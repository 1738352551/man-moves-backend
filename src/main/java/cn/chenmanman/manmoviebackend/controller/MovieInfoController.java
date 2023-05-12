package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.movie.tencent.TencentMoviePullPostRequest;
import cn.chenmanman.manmoviebackend.pageprocessor.tencent.TencentVideoPageProcessor;
import cn.chenmanman.manmoviebackend.pageprocessor.tencent.TencentVideoPipeline;
import cn.chenmanman.manmoviebackend.service.MovieInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.model.HttpRequestBody;
import us.codecraft.webmagic.utils.HttpConstant;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className MovieInfoController
 * @description TODO
 * @date 2023/5/5 15:33
 */
@RestController
@RequestMapping("/movie_info")
@Slf4j
public class MovieInfoController {


    @Autowired
    private MovieInfoService movieInfoService;


    @ApiOperation("腾讯电影抓取")
    @PostMapping("/tencent_pull")
    public CommonResult<?> tencentMoviesPull(@RequestBody TencentMoviePullPostRequest tencentMoviePullPostRequest) {
        movieInfoService.tencentMoviesPullRun(tencentMoviePullPostRequest);
        return CommonResult.success("腾讯视频爬虫抓取视频启动成功!");
    }


    @ApiOperation("腾讯电影抓取停止")
    @GetMapping("/tencent_pull_stop")
    public CommonResult<?> tencentMoviesPullStop() {
        movieInfoService.tencentMoviesPullStop();
        return CommonResult.success("腾讯视频爬虫停止成功!");
    }

}
