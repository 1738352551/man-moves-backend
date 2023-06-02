package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.domain.dto.movie.video.VideoApiAddPostRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.video.VideoApiQueryRequest;
import cn.chenmanman.manmoviebackend.domain.dto.movie.video.VideoApiUpdatePutRequest;
import cn.chenmanman.manmoviebackend.domain.entity.movie.VideoApiEntity;
import cn.chenmanman.manmoviebackend.service.VideoApiService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className VideoApiController
 * @description 视频解析接口
 * @date 2023/5/29 12:53
 */

@Api(tags = "视频解析接口管理")
@RestController
@RequestMapping("/video_api")
@Slf4j
public class VideoApiController {

    @Autowired
    private VideoApiService videoApiService;

    @ApiOperation("添加视频解析接口")
    @PostMapping("/add")
    public CommonResult<?> addVideo(@Validated @RequestBody VideoApiAddPostRequest videoApiAddReqParam) {
        VideoApiEntity videoApiEntity = new VideoApiEntity();
        BeanUtils.copyProperties(videoApiAddReqParam, videoApiEntity);
        videoApiService.save(videoApiEntity);
        return CommonResult.success();
    }



    @ApiOperation("修改视频解析接口")
    @PostMapping("/update")
    public CommonResult<?> changeVideo(@RequestBody VideoApiUpdatePutRequest videoApiUpdatePutRequest) {
        VideoApiEntity videoApiEntity = new VideoApiEntity();
        BeanUtils.copyProperties(videoApiUpdatePutRequest, videoApiEntity);
        videoApiService.updateById(videoApiEntity);
        return CommonResult.success();
    }


    @ApiOperation("删除视频解析接口")
    @DeleteMapping("/delete")
    public CommonResult<?> deleteVideo(@RequestBody List<Long> ids) {
        if (ids.size() == 0) {  throw new BusinessException("删除视频解析接口无任何id!", 500L); }
        videoApiService.removeBatchByIds(ids);
        return CommonResult.success();
    }

    @ApiOperation("分页查询")
    @PostMapping("/list/page")
    public CommonResult<Page<VideoApiEntity>> listVideoByPage(VideoApiQueryRequest videoApiQueryRequest){
        long current = videoApiQueryRequest.getCurrent();
        long size = videoApiQueryRequest.getPageSize();
        Page<VideoApiEntity> videoApiPage = videoApiService.page(new Page<>(current, size));
        return CommonResult.success(videoApiPage);
    }
}
