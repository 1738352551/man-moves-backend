package cn.chenmanman.manmoviebackend.domain.dto.movie.episodes;

import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.episodes
 * @className EpisodesQueryRequest
 * @description 剧集查询请求
 * @date 2023/6/2 23:10
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class EpisodesQueryRequest extends PageRequest {
    private String title;

    private String movieUrl;

    private Long movieId;
}
