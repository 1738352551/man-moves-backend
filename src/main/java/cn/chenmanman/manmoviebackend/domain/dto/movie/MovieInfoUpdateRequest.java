package cn.chenmanman.manmoviebackend.domain.dto.movie;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.movie
 * @className MovieInfoUpdateRequest
 * @description 影视/电视剧修改请求
 * @date 2023/5/30 0:32
 */
@Data
public class MovieInfoUpdateRequest {

    /**
     * 影视id
     * */
    private Long id;

    /**
     * 影视名
     */
    @NotBlank(message = "请填写影视名!")
    private String name;

    /**
     * 影视介绍
     */
    private String introduction;

    /**
     * 影片封面
     */
    private String bannerUrl;


    /**
     * 影视类型
     */
    private Integer type;
}
