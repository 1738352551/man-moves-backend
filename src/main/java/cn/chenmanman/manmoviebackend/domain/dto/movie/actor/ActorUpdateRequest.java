package cn.chenmanman.manmoviebackend.domain.dto.movie.actor;

import lombok.Data;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.domain.dto.movie.actor
 * @className ActorUpdateRequest
 * @description  演员信息更新请求
 * @date 2023/6/3 1:31
 */
@Data
public class ActorUpdateRequest {
    private Long id;

    private String name;


    private String photoUrl;
}
