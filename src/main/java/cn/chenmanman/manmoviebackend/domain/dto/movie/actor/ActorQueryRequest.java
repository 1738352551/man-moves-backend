package cn.chenmanman.manmoviebackend.domain.dto.movie.actor;

import cn.chenmanman.manmoviebackend.domain.dto.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.movie.actor
 * @className ActorQueryRequest
 * @description 演员信息查询请求
 * @date 2023/6/3 1:31
 */

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ActorQueryRequest extends PageRequest {
    private String name;
}
