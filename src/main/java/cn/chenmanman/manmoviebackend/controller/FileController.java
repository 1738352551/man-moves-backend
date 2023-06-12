package cn.chenmanman.manmoviebackend.controller;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import cn.chenmanman.manmoviebackend.common.exception.BusinessException;
import cn.chenmanman.manmoviebackend.service.MediaUploadService;
import com.aliyun.oss.model.PutObjectRequest;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.controller
 * @className FileController
 * @description 文件管理
 * @date 2023/6/7 12:22
 */
@Api(tags = "文件管理")
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private MediaUploadService mediaUploadService;

    @PostMapping("/video/upload")
    public CommonResult<?> uploadVideo(@RequestPart("file") MultipartFile file) {
        if (Objects.isNull(file)) {
            throw new BusinessException("上传文件为空!", 500L);
        }
        return CommonResult.success("视频上传完成!", mediaUploadService.uploadVideo(file));
    }

    @PostMapping("/upload")
    public CommonResult<?> upload(@RequestPart("file") MultipartFile file) {


        return CommonResult.success(mediaUploadService.uploadFile(file));
    }
}
