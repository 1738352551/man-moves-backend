package cn.chenmanman.manmoviebackend.domain.dto.movie.movieinfo;

import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.movie
 * @className MovieInfoQueryRequest
 * @description 影视/电视剧信息查询请求
 * @date 2023/5/30 0:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MovieInfoQueryRequest extends PageRequest implements Serializable {
    /**
     * 影视名
     */
    private String name;

    /**
     * 影视类型
     */
    private Integer type;
}
