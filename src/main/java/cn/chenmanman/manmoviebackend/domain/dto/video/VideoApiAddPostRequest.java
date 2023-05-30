package cn.chenmanman.manmoviebackend.domain.dto.video;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.video
 * @className VideoApiAddReqParam
 * @description 视频解析接口添加请求类
 * @date 2023/5/29 12:59
 */
@Data
public class VideoApiAddPostRequest {
    @NotBlank(message = "视频解析接口名不能为空!")
    @Size(message = "视频解析接口名长度不符合0~10!", min = 0, max = 10)
    private String name;

    @NotBlank(message = "视频解析接口url不能为空!")
    private String url;
}
