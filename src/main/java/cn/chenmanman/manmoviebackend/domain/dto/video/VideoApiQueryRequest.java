package cn.chenmanman.manmoviebackend.domain.dto.video;

import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.video
 * @className VideoApiQueryRequest
 * @description 视频解析接口查询请求类
 * @date 2023/5/29 13:20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class VideoApiQueryRequest extends PageRequest implements Serializable {
    private String name;

    private String url;
}
