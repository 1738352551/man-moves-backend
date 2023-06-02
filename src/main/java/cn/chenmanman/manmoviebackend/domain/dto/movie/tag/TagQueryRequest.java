package cn.chenmanman.manmoviebackend.domain.dto.movie.tag;

import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.movie.tag
 * @className TagQueryRequest
 * @description 标签查询请求
 * @date 2023/6/3 1:50
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TagQueryRequest extends PageRequest {
    private String name;
}
