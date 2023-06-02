package cn.chenmanman.manmoviebackend.domain.dto.movie.tag;

import lombok.Data;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.movie.tag
 * @className TagUpdateRequest
 * @description 标签修改请求
 * @date 2023/6/3 1:49
 */
@Data
public class TagUpdateRequest {
    private Long id;
    private String name;
}
