package cn.chenmanman.manmoviebackend.domain.dto.movie.episodes;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.episodes
 * @className EpisodesAddRequest
 * @description 剧集添加请求
 * @date 2023/6/2 23:10
 */
@Data
public class EpisodesAddRequest {

    @NotBlank(message = "剧集标题不能为空")
    private String title;


    private String movieUrl;

    @NotNull(message = "剧集必须有对应的movieId")
    private Long movieId;

}
