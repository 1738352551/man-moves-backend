package cn.chenmanman.manmoviebackend.service;

import cn.chenmanman.manmoviebackend.domain.entity.Files;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.service
 * @className MediaUploadService
 * @description 资源上传服务
 * @date 2023/5/5 15:36
 */
public interface MediaUploadService {

    String uploadVideo(MultipartFile file);

    Files uploadFile(MultipartFile file);
}
